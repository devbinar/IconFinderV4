package com.devbinar.iconfinderv4.Activitys.Intros;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.devbinar.iconfinderv4.Custom.Classes.Cache;
import com.devbinar.iconfinderv4.Custom.UI.Modals.ModalGeneral;
import com.devbinar.iconfinderv4.R;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

import java.util.HashMap;
import java.util.Map;

public class CustomIntro extends AppIntro {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance(
                "Welcome",
                "This is an aplication of test",
                R.drawable.dw_icon,
                getResources().getColor(R.color.gray_1),
                getResources().getColor(R.color.black_1),
                getResources().getColor(R.color.black_2)
        ));
        setDoneText("Done");

        setTitleColor(getResources().getColor(R.color.black_1));
        setColorDoneText(getResources().getColor(R.color.black_1));
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        Cache cache = new Cache(getSharedPreferences("data", MODE_PRIVATE));
        SharedPreferences.Editor sharedEditor = cache.read().edit();
        Map<String, Boolean> introMap = new HashMap<>();
        introMap.put("intro", true);
        cache.write(introMap);
        sharedEditor.apply();
        setResult(RESULT_OK);
        finish();
        super.onDonePressed(currentFragment);
    }

    @Override
    public void onBackPressed() {
        ModalGeneral.ShowModalGeneral(this, "Close application", "Do you want exit of the application?", "Cancel", "Exit", new ModalGeneral.OnModalGeneral() {
            @Override
            public void onPressTitle() {
            }

            @Override
            public void onPressMessage() {
            }

            @Override
            public void onPressBtn1() {
                ModalGeneral.HideModalGeneral();
            }

            @Override
            public void onPressBtn2() {
                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finishAffinity();
                System.exit(0);
            }
        });
    }
}
