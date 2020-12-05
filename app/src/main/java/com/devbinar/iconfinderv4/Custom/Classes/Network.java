package com.devbinar.iconfinderv4.Custom.Classes;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class Network {

    public static boolean enabled(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        if (actNetInfo != null && actNetInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public interface OnInternet{
        void IsOk();
        void IsNotOk();
    }

    public static void internet_access(final Context context, final OnInternet onInternet) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    Process p = Runtime.getRuntime().exec("ping -n 2 8.8.8.8");
                    int val = p.waitFor();
                    if (val == 0) {
                        CustomLog.i("internet_access", "---------------------------------------------------------------------------------------------"+ true);
                        return true;
                    } else {
                        CustomLog.i("internet_access", "---------------------------------------------------------------------------------------------"+ false);
                        return false;
                    }
                } catch (Exception e) {
                    CustomLog.stacktrace(e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean){
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onInternet.IsOk();
                        }
                    });
                }else{
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onInternet.IsNotOk();
                        }
                    });
                }
            }
        }.execute();
    }
}
