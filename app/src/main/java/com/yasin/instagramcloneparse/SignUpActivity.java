package com.yasin.instagramcloneparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
        EditText name,password;
        Button login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        name = findViewById(R.id.namesignup);
        password = findViewById(R.id.passwordsignup);
        login = findViewById(R.id.loginbtn);
        signup = findViewById(R.id.signupbtn);

        ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser !=null){
            Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
            startActivity(intent);

        }




    }

    public void signIn(View view){

        ParseUser.logInInBackground(name.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Welcome Back "+user.getUsername(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    public void signUp(View view){
        ParseUser user = new ParseUser();
        user.setUsername(name.getText().toString());
        user.setPassword(password.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(),"user created",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}