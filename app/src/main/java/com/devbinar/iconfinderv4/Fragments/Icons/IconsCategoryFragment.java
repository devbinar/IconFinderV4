package com.devbinar.iconfinderv4.Fragments.Icons;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.devbinar.iconfinderv4.Activitys.CoreActivity;
import com.devbinar.iconfinderv4.Activitys.Icons.DetailsIconActivity;
import com.devbinar.iconfinderv4.Configuration.Server;
import com.devbinar.iconfinderv4.Custom.Classes.CustomEasyReqFilter;
import com.devbinar.iconfinderv4.Custom.Classes.CustomLog;
import com.devbinar.iconfinderv4.Custom.UI.ListViews.Icons.Icon;
import com.devbinar.iconfinderv4.Custom.UI.ListViews.Icons.IconsAdapter;
import com.devbinar.iconfinderv4.Custom.UI.ProgressBar.ProgressBarGeneral;
import com.devbinar.iconfinderv4.Models.BackPressedObject;
import com.devbinar.iconfinderv4.R;
import com.devbinar.peticiones.EasyReq;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IconsCategoryFragment extends Fragment {

    String category_name;
    String category_identifier;

    public IconsCategoryFragment(String category_name, String category_identifier){
        this.category_name = category_name;
        this.category_identifier = category_identifier;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_icons_category, container, false);

        final RecyclerView rv_icons = view.findViewById(R.id.fic_rv_icons);

        CoreActivity.modify_action_bar(getActivity(), "Category "+category_name, new CoreActivity.onCore() {
            @Override
            public void action1Touch() {
                getActivity().onBackPressed();
            }
            @Override
            public void action2Touch() {
            }
        }, true, R.drawable.round_arrow_back_white_48dp, false, R.drawable.dw_icon);
        CoreActivity.addListOnBackPressed(new BackPressedObject(this, new CoreActivity.OnBackPressed() {
            @Override
            public void press(FragmentManager fragmentManager) {
            }
            @Override
            public void pressed() {
            }
        }));

        Map<String, String> headers_map = new HashMap<>();
        headers_map.put("Authorization", "Bearer X0vjEUN6KRlxbp2DoUkyHeM0VOmxY91rA6BbU5j3Xu6wDodwS0McmilLPBWDUcJ1");
        EasyReq.GET(getContext(), Server.url + "icons/search?count=100&category="+category_identifier, new CustomEasyReqFilter(), 1, headers_map, new EasyReq.Event() {
            @Override
            public void Response(String response, int code_request) {
                try {
                    JSONArray ja_icons = new JSONObject(response).getJSONArray("icons");
                    ArrayList<Icon> iconArrayList = new ArrayList<>();
                    for (int i = 0; i < ja_icons.length(); i++){
                        JSONObject jo_icon = ja_icons.getJSONObject(i);
                        boolean is_premium = jo_icon.getBoolean("is_premium");
                        String currency = "";
                        String price = "";
                        if (is_premium) {
                            JSONObject jo_prices = jo_icon.getJSONArray("prices").getJSONObject(0);
                            currency = jo_prices.getString("currency");
                            price = jo_prices.getString("price");
                        }
                        JSONArray ja_all_images = jo_icon.getJSONArray("raster_sizes");
                        String url_image_1 = ja_all_images.getJSONObject(jo_icon.getJSONArray("raster_sizes").length()-2).getJSONArray("formats").getJSONObject(0).getString("preview_url");
                        String url_image_2 = ja_all_images.getJSONObject(jo_icon.getJSONArray("raster_sizes").length()-3).getJSONArray("formats").getJSONObject(0).getString("preview_url");
                        String url_image_3 = ja_all_images.getJSONObject(jo_icon.getJSONArray("raster_sizes").length()-4).getJSONArray("formats").getJSONObject(0).getString("preview_url");
                        String url_image_4 = ja_all_images.getJSONObject(jo_icon.getJSONArray("raster_sizes").length()-5).getJSONArray("formats").getJSONObject(0).getString("preview_url");
                        iconArrayList.add(new Icon(jo_icon.getInt("icon_id"), is_premium, currency, price, url_image_1, url_image_2, url_image_3, url_image_4));
                    }
                    IconsAdapter iconsAdapter = new IconsAdapter(getActivity(), iconArrayList);
                    iconsAdapter.setOnClickListener(new IconsAdapter.OnClickListener() {
                        @Override
                        public void touch(Icon icon) {
                            Intent intent = new Intent(getActivity(), DetailsIconActivity.class);
                            intent.putExtra("bm_image", icon.getBm_image());
                            intent.putExtra("icon_id", icon.getIcon_id());
                            intent.putExtra("url_image_2", icon.getUrl_image_2());
                            intent.putExtra("url_image_3", icon.getUrl_image_3());
                            intent.putExtra("url_image_4", icon.getUrl_image_4());
                            startActivity(intent);
                        }
                    });
                    rv_icons.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    rv_icons.setAdapter(iconsAdapter);

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
                ProgressBarGeneral.ShowProgressBarGeneral(getContext(), "Loading");
            }

            @Override
            public void End() {

            }
        }, 15000);

        return view;
    }
}