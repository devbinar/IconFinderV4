package com.devbinar.iconfinderv4.Custom.UI.ListViews.Icons;

import android.graphics.Bitmap;

public class Icon {

    int icon_id;
    boolean is_premium;
    String currency;
    String price;
    String url_image_1;
    String url_image_2;
    String url_image_3;
    String url_image_4;
    Bitmap bm_image;

    public Icon(int icon_id, boolean is_premium, String currency, String price, String url_image_1, String url_image_2, String url_image_3, String url_image_4) {
        this.icon_id = icon_id;
        this.is_premium = is_premium;
        this.currency = currency;
        this.price = price;
        this.url_image_1 = url_image_1;
        this.url_image_2 = url_image_2;
        this.url_image_3 = url_image_3;
        this.url_image_4 = url_image_4;
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

    public String getUrl_image_1() {
        return url_image_1;
    }

    public Bitmap getBm_image() {
        return bm_image;
    }

    public void setBm_image(Bitmap bm_image) {
        this.bm_image = bm_image;
    }

    public String getUrl_image_2() {
        return url_image_2;
    }

    public String getUrl_image_3() {
        return url_image_3;
    }

    public String getUrl_image_4() {
        return url_image_4;
    }
}
