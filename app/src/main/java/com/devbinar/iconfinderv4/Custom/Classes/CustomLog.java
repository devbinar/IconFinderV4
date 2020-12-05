package com.devbinar.iconfinderv4.Custom.Classes;

import android.util.Log;

public class CustomLog {

    public static void i(String from, String message){
        Log.i(from,message);
    }

    public static void stacktrace(Exception e){
        e.printStackTrace();
    }
}
