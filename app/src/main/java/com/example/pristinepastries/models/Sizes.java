package com.example.pristinepastries.models;

public class Sizes {
    public String id;
    public String label;
    public String price;
    public String product_id;
    public String created_at;
    public String updated_at;

    public Sizes(String id, String label, String price, String product_id, String created_at, String updated_at) {
        this.id = id;
        this.label = label;
        this.price = price;
        this.product_id = product_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
