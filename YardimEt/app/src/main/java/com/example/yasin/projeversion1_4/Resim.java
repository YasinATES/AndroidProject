package com.example.yasin.projeversion1_4;

public class Resim {
    private String ihbarid;
    private String imgUrl;

    public Resim() {

    }

    public Resim(String ihbarid, String imgUrl) {
        this.ihbarid = ihbarid;
        this.imgUrl = imgUrl;
    }

    public String getIhbarid() {
        return ihbarid;
    }

    public void setIhbarid(String ihbarid) {
        this.ihbarid = ihbarid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
