package com.devbinar.iconfinderv4.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.VolleyError;
import com.devbinar.iconfinderv4.Activitys.CoreActivity;
import com.devbinar.iconfinderv4.Configuration.Server;
import com.devbinar.iconfinderv4.Custom.Classes.CustomEasyReqFilter;
import com.devbinar.iconfinderv4.Custom.UI.ListViews.Categories.CategoriesAdapter;
import com.devbinar.iconfinderv4.Custom.UI.ListViews.Categories.Category;
import com.devbinar.iconfinderv4.Custom.UI.ProgressBar.ProgressBarGeneral;
import com.devbinar.iconfinderv4.Fragments.Icons.IconsCategoryFragment;
import com.devbinar.iconfinderv4.Models.BackPressedObject;
import com.devbinar.iconfinderv4.R;
import com.devbinar.peticiones.EasyReq;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoriesFragment extends Fragment {

    ArrayList<Category> categoryArrayList = new ArrayList<>();
    ArrayList<Category> categoryArrayListNew = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        final EditText et_search_category = view.findViewById(R.id.fc_et_search_category);
        final ListView lv_categories = view.findViewById(R.id.fc_lv_categories);

        CoreActivity.modify_action_bar(getActivity(), "Select category", new CoreActivity.onCore() {
            @Override
            public void action1Touch() {

            }

            @Override
            public void action2Touch() {
            }
        }, false, R.drawable.dw_icon, false, R.drawable.dw_icon);
        CoreActivity.addListOnBackPressed(new BackPressedObject(this, new CoreActivity.OnBackPressed() {
            @Override
            public void press(FragmentManager fragmentManager) {
            }
            @Override
            public void pressed() {
            }
        }));

        et_search_category.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textlength = et_search_category.getText().length();

                categoryArrayListNew = new ArrayList<>();

                for (int i = 0; i < categoryArrayList.size(); i++) {
                    if (textlength <= categoryArrayList.get(i).getName().length()) {
                        if(categoryArrayList.get(i).getName().toLowerCase().contains(et_search_category.getText().toString().toLowerCase().trim())) {
                            categoryArrayListNew.add(categoryArrayList.get(i));
                        }
                    }
                }
                CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity(), categoryArrayListNew);
                lv_categories.setAdapter(categoriesAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lv_categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ac_cl_view, new IconsCategoryFragment(categoryArrayListNew.get(position).getName(), categoryArrayListNew.get(position).getIdentifier())).commit();
            }
        });

        Map<String, String> headers_map = new HashMap<>();
        headers_map.put("Authorization", "Bearer X0vjEUN6KRlxbp2DoUkyHeM0VOmxY91rA6BbU5j3Xu6wDodwS0McmilLPBWDUcJ1");
        EasyReq.GET(getContext(), Server.url + "categories?count=100", new CustomEasyReqFilter(), 1, headers_map, new EasyReq.Event() {
            @Override
            public void Response(String response, int code_request) {
                try {
                    JSONArray categories = new JSONObject(response).getJSONArray("categories");
                    categoryArrayList.clear();
                    for (int i = 0; i < categories.length(); i++){
                        categoryArrayList.add(new Category(categories.getJSONObject(i).getString("name"), categories.getJSONObject(i).getString("identifier")));
                    }
                    categoryArrayListNew = categoryArrayList;
                    CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity(), categoryArrayList);
                    lv_categories.setAdapter(categoriesAdapter);
                    et_search_category.setText(et_search_category.getText().toString());
                }catch (Exception exception){
                    //
                }
                ProgressBarGeneral.HideProgressBarGeneral();
            }

            @Override
            public void Error(VolleyError error, int code_request) {

            }
        }, new EasyReq.State() {
            @Override
            public void Start() {
                ProgressBarGeneral.ShowProgressBarGeneral(getContext(), "Buscando");
            }

            @Override
            public void End() {

            }
        }, 15000);

        return view;
    }
}