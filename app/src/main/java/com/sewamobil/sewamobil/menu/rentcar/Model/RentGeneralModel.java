package com.sewamobil.sewamobil.menu.rentcar.Model;

public class RentGeneralModel {
    String title = "", konten = "";
    int image;

    String type = "";

    public RentGeneralModel(String title, String konten, int image) {
        this.title = title;
        this.konten = konten;
        this.image = image;
    }

    public RentGeneralModel(String title, String konten, String type) {
        this.title = title;
        this.konten = konten;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
