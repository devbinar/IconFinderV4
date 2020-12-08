package com.devbinar.iconfinderv4.Custom.UI.ListViews.Icons;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbinar.iconfinderv4.R;
import com.devbinar.peticiones.EasyReq;

import java.util.ArrayList;

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<Icon> iconArrayList;

    public IconsAdapter(Activity activity, ArrayList<Icon> iconArrayList) {
        this.activity = activity;
        this.iconArrayList = iconArrayList;
    }

    @NonNull
    @Override
    public IconsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View iconView = inflater.inflate(R.layout.item_view_icon, parent, false);
        IconsAdapter.ViewHolder viewHolder = new IconsAdapter.ViewHolder(iconView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IconsAdapter.ViewHolder holder, final int position) {
        EasyReq.READ_IMAGE(iconArrayList.get(position).getUrl_image_1(), new EasyReq.EventReadImage() {
            @Override
            public void Start() {
            }

            @Override
            public void Downloaded(Bitmap bitmap) {
                iconArrayList.get(position).setBm_image(bitmap);
                holder.iv_icon.setImageBitmap(bitmap);
                holder.pb_carga.setVisibility(View.GONE);
            }

            @Override
            public void Error(String error) {
                holder.iv_icon.setImageResource(R.drawable.round_warning_red_a700_48dp);
                holder.pb_carga.setVisibility(View.GONE);
            }
        });
        if (iconArrayList.get(position).is_premium){
            holder.tv_price.setText(iconArrayList.get(position).getPrice()+" "+iconArrayList.get(position).getCurrency());
        }else{
            holder.tv_price.setText("FREE");
            holder.iv_premium.setVisibility(View.GONE);
        }
        holder.v_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null){
                    onClickListener.touch(iconArrayList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return iconArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View v_view;
        ProgressBar pb_carga;
        ImageView iv_icon;
        ImageView iv_premium;
        TextView tv_price;

        public ViewHolder(View itemView) {
            super(itemView);
            v_view = itemView;
            pb_carga = itemView.findViewById(R.id.ivi_pb_carga);
            iv_icon = itemView.findViewById(R.id.ivi_iv_icon);
            iv_premium = itemView.findViewById(R.id.ivi_iv_premium);
            tv_price = itemView.findViewById(R.id.ivi_tv_precio);
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void touch(Icon icon);
    }
}