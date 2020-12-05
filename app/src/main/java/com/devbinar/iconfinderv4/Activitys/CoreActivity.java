package com.devbinar.iconfinderv4.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.devbinar.iconfinderv4.Custom.UI.ProgressBar.ProgressBarGeneral;
import com.devbinar.iconfinderv4.R;

public class CoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        ProgressBarGeneral.ShowProgressBarGeneral(this, "Buscando");
    }
}