package com.devbinar.iconfinderv4.Custom.UI.ListViews.Icons;

public class Raster {

    int size;
    String preview_url;
    String download_url;
    String format;

    public Raster(int size, String preview_url, String download_url, String format) {
        this.size = size;
        this.preview_url = preview_url;
        this.download_url = download_url;
        this.format = format;
    }

    public int getSize() {
        return size;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public String getFormat() {
        return format;
    }
}
