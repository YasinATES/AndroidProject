package com.example.yasin.projeversion1_4;

public class Ihbar {
    public String sucTuru;
    public String aciklama;
    public String latitude;
    public String longitude;
    public String kulmail;
    public String img;

    public Ihbar() {
        // Default Constructor
    }

    public Ihbar(String sucTuru, String aciklama, String latitude, String longitude, String kulmail, String img) {
        this.sucTuru = sucTuru;
        this.aciklama = aciklama;
        this.latitude = latitude;
        this.longitude = longitude;
        this.kulmail = kulmail;
        this.img = img;
    }

    public String getSucTuru() {
        return sucTuru;
    }

    public void setSucTuru(String sucTuru) {
        this.sucTuru = sucTuru;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getKulmail() {
        return kulmail;
    }

    public void setKulmail(String kulmail) {
        this.kulmail = kulmail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
