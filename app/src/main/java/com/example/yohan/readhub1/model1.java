package com.example.yohan.readhub1;

public class model1 {

  //  public static final int IMAGE_TYPE = 1;
    public String title,date,image;
    public int type;
    public String render;
    public static String profile;

    public model1(){

    }

    public model1(int type,String title, String date, String image,String render,String profile ) {
        this.type = type;
        this.title = title;
        this.date = date;
        this.image = image;
        this.render = render;
        this.profile = profile;


    }



//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
////    public int getType() {
////        return type;
////    }
////
////    public void setType(int type) {
////        this.type = type;
////    }
//
//    public String getRender() {
//        return render;
//    }
//
//    public void setRender(String render) {
//        this.render = render;
//    }
//
//    public static String getProfile() {
//        return profile;
//    }
//
//    public static void setProfile(String profile) {
//        model1.profile = profile;
//    }
}
