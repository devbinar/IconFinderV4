package com.devbinar.iconfinderv4.Activitys.Icons;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.devbinar.iconfinderv4.R;
import com.devbinar.peticiones.EasyReq;

public class DetailsIconActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_details_icon);

        ImageView iv_1 = findViewById(R.id.adi_iv_1);
        final ImageView iv_2 = findViewById(R.id.adi_iv_2);
        final ImageView iv_3 = findViewById(R.id.adi_iv_3);
        final ImageView iv_4 = findViewById(R.id.adi_iv_4);
        final ProgressBar pb_2 = findViewById(R.id.adi_pb_2);
        final ProgressBar pb_3 = findViewById(R.id.adi_pb_3);
        final ProgressBar pb_4 = findViewById(R.id.adi_pb_4);

        EasyReq.enabledHistoryRequests(true);

        iv_1.setImageBitmap((Bitmap) getIntent().getParcelableExtra("bm_image"));
        EasyReq.READ_IMAGE(getIntent().getStringExtra("url_image_2"), new EasyReq.EventReadImage() {
            @Override
            public void Start() {
            }

            @Override
            public void Downloaded(Bitmap bitmap) {
                iv_2.setImageBitmap(bitmap);
                pb_2.setVisibility(View.GONE);
            }

            @Override
            public void Error(String error) {
                iv_2.setImageResource(R.drawable.round_warning_red_a700_48dp);
                pb_2.setVisibility(View.GONE);
            }
        });
        EasyReq.READ_IMAGE(getIntent().getStringExtra("url_image_3"), new EasyReq.EventReadImage() {
            @Override
            public void Start() {
            }

            @Override
            public void Downloaded(Bitmap bitmap) {
                iv_3.setImageBitmap(bitmap);
                pb_3.setVisibility(View.GONE);
            }

            @Override
            public void Error(String error) {
                iv_3.setImageResource(R.drawable.round_warning_red_a700_48dp);
                pb_3.setVisibility(View.GONE);
            }
        });
        EasyReq.READ_IMAGE(getIntent().getStringExtra("url_image_4"), new EasyReq.EventReadImage() {
            @Override
            public void Start() {
            }

            @Override
            public void Downloaded(Bitmap bitmap) {
                iv_4.setImageBitmap(bitmap);
                pb_4.setVisibility(View.GONE);
            }

            @Override
            public void Error(String error) {
                iv_4.setImageResource(R.drawable.round_warning_red_a700_48dp);
                pb_4.setVisibility(View.GONE);
            }
        });

        modify_action_bar(this, "Details icon", new DetailsIconActivity.onCore() {
            @Override
            public void action1Touch() {
                DetailsIconActivity.this.onBackPressed();
            }

            @Override
            public void action2Touch() {
            }
        }, true, R.drawable.round_arrow_back_white_48dp, false, R.drawable.dw_icon);
    }

    public interface onCore{
        void action1Touch();
        void action2Touch();
    }

    public static void modify_action_bar(Activity activity, String text, final DetailsIconActivity.onCore onCore, boolean show_action_1, int action_1_icon, boolean show_action_2, int action_2_icon){
        ImageView iv_action_1 = activity.findViewById(R.id.ag_iv_action_1);
        TextView text_action_bar = activity.findViewById(R.id.ag_tv_section);
        ImageView iv_action_2 = activity.findViewById(R.id.ag_iv_action_2);

        if (show_action_1){
            iv_action_1.setVisibility(View.VISIBLE);
        }else{
            iv_action_1.setVisibility(View.GONE);
        }
        iv_action_1.setImageResource(action_1_icon);
        iv_action_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCore.action1Touch();
            }
        });
        text_action_bar.setText(text);
        if (show_action_2){
            iv_action_2.setVisibility(View.VISIBLE);
        }else{
            iv_action_2.setVisibility(View.GONE);
        }
        iv_action_2.setImageResource(action_2_icon);
        iv_action_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCore.action2Touch();
            }
        });
    }
}