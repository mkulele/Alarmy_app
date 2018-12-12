package com.alarmy.konyang.alarmy;

public class MyItem {

    private String idx;
    private String title;
    private String name;
    private String time;
    private String category;

    private String ownerId;

   public MyItem(String idx, String title, String name, String time, String category, String ownerId){
        this.idx=idx;
        this.title=title;
        this.name=name;
        this.time=time;
        this.category=category;
        this.ownerId=ownerId;
    }

    public String getIdx(){
        return idx;
    }

    public void setIdx(String idx){
        this.idx=idx;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

   public String getName(){
        return name;
   }

   public void setName(String name){
        this.name = name;
   }

    public String getTime(){
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
       return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getOwnerid(){
       return ownerId;
    }
    public void setOwnerid(String ownerId){
       this.ownerId=ownerId;
    }
}
