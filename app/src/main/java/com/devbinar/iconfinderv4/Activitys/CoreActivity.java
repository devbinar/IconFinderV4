package com.devbinar.iconfinderv4.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.devbinar.iconfinderv4.Activitys.Intros.CustomIntro;
import com.devbinar.iconfinderv4.Custom.Classes.Cache;
import com.devbinar.iconfinderv4.Custom.Classes.CustomLog;
import com.devbinar.iconfinderv4.Custom.UI.Modals.ModalGeneral;
import com.devbinar.iconfinderv4.Models.BackPressedObject;
import com.devbinar.iconfinderv4.R;

import java.util.ArrayList;
import java.util.List;

public class CoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_core);

        Cache cache = new Cache(getSharedPreferences("data", MODE_PRIVATE));
        if (!cache.read().getBoolean("intro", false)){
            Intent intent = new Intent(this, CustomIntro.class);
            startActivity(intent);
        }


        //ProgressBarGeneral.ShowProgressBarGeneral(this, "Buscando");
    }







    public interface onCore{
        void helpTouch();
    }

    public static void modify_action_bar(Activity activity, String text, final onCore onCore, boolean mostrar_menu, boolean mostrar_help, int action_icon){
        ImageView menu = activity.findViewById(R.id.ag_iv_menu);
        TextView text_action_bar = activity.findViewById(R.id.ag_tv_section);
        ImageView action_action_bar = activity.findViewById(R.id.ag_iv_action);
        if (mostrar_menu){
            menu.setVisibility(View.VISIBLE);
        }else{
            menu.setVisibility(View.GONE);
        }
        text_action_bar.setText(text);
        if (mostrar_help){
            action_action_bar.setVisibility(View.VISIBLE);
        }
        action_action_bar.setImageResource(action_icon);
        action_action_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCore.helpTouch();
            }
        });
    }









    public interface OnBackPressed{
        void press(FragmentManager fragmentManager);
        void pressed();
    }

    private static final List<BackPressedObject> listBackPressedObject = new ArrayList<>();

    public static List<BackPressedObject> getListBackPressedObject() {
        return listBackPressedObject;
    }

    public static void addListOnBackPressed(BackPressedObject backPressedObject) {
        CustomLog.i("addListOnBackPressed", CustomLog.getLine_debug());
        for (BackPressedObject backPressedObject1 : getListBackPressedObject()){
            if (backPressedObject1.equals(backPressedObject)){
                getListBackPressedObject().remove(backPressedObject1);
            }

        }
        listBackPressedObject.add(backPressedObject);
    }

    public final OnBackPressed onBackPressedController = new OnBackPressed() {
        @Override
        public void press(FragmentManager fragmentManager) {
            if (listBackPressedObject.size() > 0) {
                BackPressedObject backPressedObject_remove = listBackPressedObject.get(listBackPressedObject.size()-1);
                backPressedObject_remove.getOnBackPressed().press(fragmentManager);
                listBackPressedObject.remove(backPressedObject_remove);
                CustomLog.i("onBackPressedController.pressed", CustomLog.getLine_debug());

                if (listBackPressedObject.size() > 0) {
                    BackPressedObject backPressedObject_last = listBackPressedObject.get(listBackPressedObject.size() - 1);
                    fragmentManager.beginTransaction().replace(R.id.ac_cl_view, backPressedObject_last.getFragment()).commitNow();
                    backPressedObject_remove.getOnBackPressed().pressed();
                    return;
                }else{
                    backPressedObject_remove.getOnBackPressed().pressed();
                }
            }

            ModalGeneral.ShowModalGeneral(CoreActivity.this, "Close application", "Do you want exit of the application?", "Exit", "Cancel", new ModalGeneral.OnModalGeneral() {
                @Override
                public void onPressTitle() {
                }

                @Override
                public void onPressMessage() {
                }

                @Override
                public void onPressBtn1() {
                    startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    finishAffinity();
                    System.exit(0);
                }

                @Override
                public void onPressBtn2() {
                    ModalGeneral.HideModalGeneral();
                }
            });
        }

        @Override
        public void pressed() {

        }
    };

    @Override
    public void onBackPressed() {
        onBackPressedController.press(getSupportFragmentManager());
    }
}