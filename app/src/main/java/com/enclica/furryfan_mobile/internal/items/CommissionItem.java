package com.enclica.furryfan_mobile.internal.items;

public class CommissionItem{
    private String refsheeturl;
    private String desc;
    private String sender;
    private int ID;
    private int approved;
    private int completed;
    private  int reported;


    private String subtitle;
    public CommissionItem(String refsheeturl, String desc,  String sender, int ID, int approved, int completed,int reported){
        this.refsheeturl =refsheeturl;
        this.desc=desc;
        this.sender = sender;
        this.refsheeturl = refsheeturl;
        this.ID = ID;
        this.approved = approved;
        this.completed = completed;
        this.reported = reported;
    }
    public  String getrefsheetURL(){return refsheeturl;}
    public void setrefsheeturl(String refsheeturl){
        this.refsheeturl=refsheeturl;
    }
    public String getdescription(){return desc;}
    public void setdescription(String desc){
        this.desc=desc;
    }
    public String getsender(){return sender;}
    public void setsender(String sender){
        this.sender=sender;
    }
    public int getID(){return ID;}
    public void setID(int ID){this.ID=ID;}
    public int getApproved(){return approved;}
    public void setApproved(int approved){this.approved=approved;}
    public int getCompleted(){return completed;}
    public void setCompleted(int completed){this.completed=completed;}
    public  int getReported(){return reported;}
    public void setReported(int reported){this.reported = reported;}

}