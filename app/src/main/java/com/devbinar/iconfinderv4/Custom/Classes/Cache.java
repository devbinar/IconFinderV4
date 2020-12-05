package com.devbinar.iconfinderv4.Custom.Classes;

import android.content.SharedPreferences;

import java.util.Map;

public class Cache {

    private SharedPreferences sharedPreferences;

    public Cache(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public void write(Map<String, ?> variables){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String keys_variables : variables.keySet()){
            Object object_variables = (Object) variables.get(keys_variables);
            if (object_variables instanceof Boolean)
                editor.putBoolean(keys_variables, (Boolean) object_variables);
            if (object_variables instanceof Float)
                editor.putFloat(keys_variables, (Float) object_variables);
            if (object_variables instanceof Integer)
                editor.putInt(keys_variables, (Integer) object_variables);
            if (object_variables instanceof Long)
                editor.putLong(keys_variables, (Long) object_variables);
            if (object_variables instanceof String)
                editor.putString(keys_variables, (String) object_variables);
        }
        editor.apply();
    }

    public SharedPreferences read(){
        return sharedPreferences;
    }
}
