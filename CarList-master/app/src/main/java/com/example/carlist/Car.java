package com.example.carlist;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.Serializable;

public class Car implements Serializable {
    private String brand;
    private String color;
    private String date;
    private int resource;//jak nie ma photo to wyswietla obrazek
    private String photo;//sciezka do pliku


    public Car(String brand, String color, String date, int resource) {
        this.brand = brand;
        this.color = color;
        this.date = date;
        this.resource = resource;
        this.photo = null;
    }
//funcje do zarzadzania obiektem typu car 
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public Uri getPhoto() {
        if (photo == null) {
            return null;
        } else {
            return Uri.parse(photo); //ze strina na URI
        }
    }

    public void setPhoto(Uri photo) {
        this.photo = photo.getPath();
    }//z obiektu URI sciagamy sciezke
}
