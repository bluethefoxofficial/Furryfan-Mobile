package com.enclica.furryfan_mobile.internal.objects;

import org.json.JSONObject;

public class ImageObject {
    private int id;
    private String imageUrl;

    public ImageObject(){

    }

    public  ImageObject(JSONObject jsonObject){
        if(jsonObject == null) return;
        this.id = jsonObject.optInt("id");
        this.imageUrl = jsonObject.optString("url");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}