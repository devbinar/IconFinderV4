package com.devbinar.iconfinderv4.Activitys.Icons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.devbinar.iconfinderv4.Configuration.Server;
import com.devbinar.iconfinderv4.Custom.Classes.CustomEasyReqFilter;
import com.devbinar.iconfinderv4.Custom.Classes.CustomLog;
import com.devbinar.iconfinderv4.Custom.UI.ProgressBar.ProgressBarGeneral;
import com.devbinar.iconfinderv4.R;
import com.devbinar.peticiones.EasyReq;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        final TextView tv_name_icon = findViewById(R.id.adi_tv_name_icon);
        final TextView tv_name_company = findViewById(R.id.adi_tv_name_company);
        final TextView tv_name_user = findViewById(R.id.adi_tv_name_user);
        final TextView tv_name_author = findViewById(R.id.adi_tv_name_author);
        final TextView tv_publisher = findViewById(R.id.adi_tv_publisher);
        final TextView tv_license = findViewById(R.id.adi_tv_license);
        final Button btn_see_web_icon = findViewById(R.id.adi_btn_see_web_icon);
        final Button btn_see_web_author = findViewById(R.id.adi_btn_see_web_author);


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
        Map<String, String> headers_map = new HashMap<>();
        headers_map.put("Authorization", "Bearer X0vjEUN6KRlxbp2DoUkyHeM0VOmxY91rA6BbU5j3Xu6wDodwS0McmilLPBWDUcJ1");
        EasyReq.GET(this, Server.url + "icons/"+getIntent().getIntExtra("icon_id", 1), new CustomEasyReqFilter(), 2, headers_map, new EasyReq.Event() {
            @Override
            public void Response(String response, int code_request) {
                try {
                    JSONObject jo_all = new JSONObject(response);
                    CustomLog.i("Response", CustomLog.getLine_debug()+jo_all.toString());
                    JSONObject jo_iconset = jo_all.getJSONObject("iconset");
                    final JSONObject jo_author = jo_iconset.getJSONObject("author");
                    JSONObject jo_license = new JSONObject("{\"license_id\": 12,\"name\": \"Free for commercial use (Do not redistribute)\",\"scope\": \"free\"}");
                    if (jo_iconset.has("license")){
                        jo_license = jo_iconset.getJSONObject("license");
                    }
                    tv_name_icon.setText(jo_iconset.getString("name"));
                    tv_name_company.setText((jo_author.has("company") ? jo_author.getString("company") : "Not company"));
                    tv_name_user.setText(jo_author.getString("username"));
                    tv_name_author.setText(jo_author.getString("name"));
                    tv_publisher.setText(jo_iconset.getString("published_at").split("T")[0]);
                    tv_license.setText(jo_license.getString("name"));

                    btn_see_web_icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("https://www.iconfinder.com/icons/"+getIntent().getIntExtra("icon_id", 1));
                            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                            startActivity(intent);
                        }
                    });
                    final String internal_jo_author = jo_author.getString("username");
                    btn_see_web_author.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("https://www.iconfinder.com/"+internal_jo_author);
                            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                            startActivity(intent);
                        }
                    });
                }catch (Exception exception){
                    CustomLog.stacktrace(exception);
                }
                ProgressBarGeneral.HideProgressBarGeneral();
            }

            @Override
            public void Error(VolleyError error, int code_request) {

            }
        }, new EasyReq.State() {
            @Override
            public void Start() {
                ProgressBarGeneral.ShowProgressBarGeneral(DetailsIconActivity.this, "Loading");
            }

            @Override
            public void End() {

            }
        }, 15000);

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