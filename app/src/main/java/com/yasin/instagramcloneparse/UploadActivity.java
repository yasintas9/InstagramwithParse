package com.yasin.instagramcloneparse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    EditText descrp;
    ImageView photo;
    Button save;
    Bitmap chosenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        descrp = findViewById(R.id.description);
        photo = findViewById(R.id.imageView);
        save = findViewById(R.id.savebtn);
    }

    public void upload(View view){ // DB'ye RESIM VE bilgileri ekleme
        String comment = descrp.getText().toString();


        //resim işleri
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        chosenImage.compress(Bitmap.CompressFormat.PNG,60,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        ParseFile parseFile = new ParseFile("image.png",bytes);


        ParseObject object = new ParseObject("Posts");

        object.put("image",parseFile);

        object.put("comment",comment);
        object.put("username", ParseUser.getCurrentUser().getUsername());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                } else{
                    Toast.makeText(getApplicationContext(),"POST UPLOADED!!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    public void chooseImage(View view){
        // api 23 sonrası için galeri izni isteme 23 öncesi manifestte
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }else{ // izin varsa
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==2) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // İZİN VERİLİRSE GALERİYE GİT
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

            if(requestCode==1 && resultCode == RESULT_OK && data !=null){
                Uri imageData = data.getData();

                try {
                    if(Build.VERSION.SDK_INT>=28){
                        ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                         chosenImage = ImageDecoder.decodeBitmap(source);
                         photo.setImageBitmap(chosenImage);
                    }
                    else{
                        chosenImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                        photo.setImageBitmap(chosenImage);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        super.onActivityResult(requestCode, resultCode, data);
    }
}