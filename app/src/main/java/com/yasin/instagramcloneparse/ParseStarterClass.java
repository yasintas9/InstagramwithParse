package com.yasin.instagramcloneparse;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("uGwnJQLnIAkq4Yevh3OozHdzdNyp1Bs3FfZCaZ7w")
                .clientKey("xEdOHSZRPALjdKGzqoLneXce0kSXB8mkSPDegwsS")
                .server("https://parseapi.back4app.com/")
                .build()

        );
    }
}
