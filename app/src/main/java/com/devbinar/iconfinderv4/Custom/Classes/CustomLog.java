package com.devbinar.iconfinderv4.Custom.Classes;

import android.util.Log;

public class CustomLog {

    public static void i(String tag, String message){
        Log.i(tag, message);
    }

    public static void stacktrace(Exception e){
        e.printStackTrace();
    }

    public static String getLine_debug() {
        return "-----------------------------------------------";
    }
}
