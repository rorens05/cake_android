package com.example.pristinepastries.models;

public class Categories {

    public String id;
    public String name;
    public String description;
    public String image;

    public Categories(String id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Categories() {
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
