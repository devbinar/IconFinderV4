package com.devbinar.iconfinderv4.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.devbinar.iconfinderv4.Configuration.Server;
import com.devbinar.iconfinderv4.Custom.Classes.CustomEasyReqFilter;
import com.devbinar.iconfinderv4.Custom.Classes.CustomLog;
import com.devbinar.iconfinderv4.R;
import com.devbinar.peticiones.EasyReq;

import java.util.HashMap;
import java.util.Map;

public class CategoriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        Map<String, String> headers_map = new HashMap<>();
        headers_map.put("Authorization", "Bearer X0vjEUN6KRlxbp2DoUkyHeM0VOmxY91rA6BbU5j3Xu6wDodwS0McmilLPBWDUcJ1");
        CustomLog.i("CoreActivity", CustomLog.getLine_debug()+ Server.url+"categories?count=100");
        EasyReq.GET(getContext(), Server.url + "categories?count=100", new CustomEasyReqFilter(), 1, headers_map, new EasyReq.Event() {
            @Override
            public void Response(String response, int code_request) {

            }

            @Override
            public void Error(VolleyError error, int code_request) {

            }
        }, null, 15000);
        return view;
    }
}