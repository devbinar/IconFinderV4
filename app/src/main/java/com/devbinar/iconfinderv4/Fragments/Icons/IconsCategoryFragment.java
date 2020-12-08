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

        /*
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                        (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() - listView.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


         */
        Map<String, String> headers_map = new HashMap<>();
        headers_map.put("Authorization", "Bearer X0vjEUN6KRlxbp2DoUkyHeM0VOmxY91rA6BbU5j3Xu6wDodwS0McmilLPBWDUcJ1");
        EasyReq.GET(getContext(), Server.url + "icons/search?count=100&&category="+category_identifier, new CustomEasyReqFilter(), 1, headers_map, new EasyReq.Event() {
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
                        String url_image = jo_icon.getJSONArray("raster_sizes").
                                getJSONObject(jo_icon.getJSONArray("raster_sizes").length()-2).
                                getJSONArray("formats").getJSONObject(0).getString("preview_url");
                        iconArrayList.add(new Icon(jo_icon.getInt("icon_id"), is_premium, currency, price, url_image));
                    }
                    IconsAdapter iconsAdapter = new IconsAdapter(getActivity(), iconArrayList);
                    iconsAdapter.setOnClickListener(new IconsAdapter.OnClickListener() {
                        @Override
                        public void touch(Icon icon) {
                            Intent intent = new Intent(getActivity(), DetailsIconActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("icon_id", icon.getIcon_id());
                            bundle.putParcelable("bm_image", icon.getBm_image());
                            startActivity(intent, bundle);
                        }
                    });
                    rv_icons.setLayoutManager(new GridLayoutManager(getContext(), 2));
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