package com.devbinar.iconfinderv4.Custom.UI.ListViews.Categories;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devbinar.iconfinderv4.R;

import java.util.ArrayList;

public class CategoriesAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Category> customObjectArticulos;

    public CategoriesAdapter(Activity activity, ArrayList<Category> customObjectArticulos) {
        this.context = activity;
        this.customObjectArticulos = customObjectArticulos;
    }

    @Override
    public int getCount() {
        return customObjectArticulos.size();
    }

    @Override
    public Category getItem(int position) {
        return customObjectArticulos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ( convertView == null ) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_listview_category, null);
        }

        TextView name = convertView.findViewById(R.id.ilc_tv_name);
        name.setText(customObjectArticulos.get(position).getName());

        return convertView;
    }
}
