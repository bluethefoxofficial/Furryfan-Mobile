package com.enclica.furryfan_mobile;

import android.text.Spanned;

public class Item {
    private int imageId;
    private String imageURL;
    private String title;
    private String filetype;
    private int postID;
    private String description;
    private String author;


    private String subtitle;
    public Item(String title, String subtitle, String imageURL, String filetype, int postID, Spanned description, String author){
        this.imageId=imageId;
        this.imageURL=imageURL;
        this.title =title;
        this.subtitle=subtitle;
        this.filetype = filetype;
        this.postID = postID;
        this.author = author;
    }
    public int getImageId(){return imageId;}
    public void setImageId(int imageId){
        this.imageId=imageId;
    }

    public  String getTitle(){return title;}
    public void setTitle(String title){
        this.title=title;
    }
    public  String getImageURL(){return imageURL;}
    public void setImageURL(String url){
        this.imageURL=url;
    }
    public String getSubtitle(){return subtitle;}
    public void setSubtitle(String subtitle){
        this.subtitle=subtitle;
    }
    public String getFiletype(){return filetype;}
    public void setFiletype(String filetype){
        this.filetype=filetype;
    }
    public int getPostID(){return postID;}
    public void setPostID(int id){
        this.postID=id;
    }
    public String getDescription(){return description;}
    public void setDescription(String description){
        this.subtitle=description;
    }
    public String getAuthor(){return author;}
    public void setAuthor(String author){
        this.subtitle=author;
    }

}