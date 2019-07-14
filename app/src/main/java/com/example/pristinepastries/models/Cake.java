package com.example.pristinepastries.models;

public class Cake {
    public String id;
    public String name;
    public String description;
    public String category_id;
    public String stock;
    public String image;

    public Cake(String id, String name, String description, String category_id, String stock, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category_id = category_id;
        this.stock = stock;
        this.image = image;
    }

    public Cake() {
    }

    @Override
    public String toString() {
        return "Cake{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category_id='" + category_id + '\'' +
                ", stock='" + stock + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
