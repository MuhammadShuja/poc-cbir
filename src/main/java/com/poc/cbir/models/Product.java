package com.poc.cbir.models;

public class Product {
    private int id;
    private String thumbnail;

    public Product(int id, String thumbnail) {
        this.id = id;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
