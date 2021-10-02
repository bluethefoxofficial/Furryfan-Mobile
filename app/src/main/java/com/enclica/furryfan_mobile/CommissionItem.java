package com.enclica.furryfan_mobile;

public class CommissionItem {
    private String title;
    private String filetype;
    private int postID;
    private String description;
    private String author;


    private String subtitle;
    public CommissionItem(String title, String subtitle,  String filetype, int postID, String description, String author){
        this.title =title;
        this.subtitle=subtitle;
        this.filetype = filetype;
        this.postID = postID;
        this.description = description;
        this.author = author;
    }
    public  String getTitle(){return title;}
    public void setTitle(String title){
        this.title=title;
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
        this.description=description;
    }
    public String getAuthor(){return author;}
    public void setAuthor(String author){
        this.subtitle=author;
    }

}