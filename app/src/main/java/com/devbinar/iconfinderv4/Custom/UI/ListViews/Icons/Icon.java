package com.devbinar.iconfinderv4.Custom.UI.ListViews.Icons;

import android.graphics.Bitmap;

public class Icon {

    int icon_id;
    boolean is_premium;
    String currency;
    String price;
    String url_image;
    Bitmap bm_image;

    public Icon(int icon_id, boolean is_premium, String currency, String price, String url_image) {
        this.icon_id = icon_id;
        this.is_premium = is_premium;
        this.currency = currency;
        this.price = price;
        this.url_image = url_image;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public boolean isIs_premium() {
        return is_premium;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl_image() {
        return url_image;
    }

    public Bitmap getBm_image() {
        return bm_image;
    }

    public void setBm_image(Bitmap bm_image) {
        this.bm_image = bm_image;
    }
}
