package com.example.pristinepastries.models;

public class Order {

    public String id;
    public String ordered_at;
    public String customer_id;
    public String product_id;
    public String size_id;
    public String no_of_items;
    public String payment_method;
    public String status;
    public String delivery_location;
    public String delivered_at;
    public String created_at;
    public String updated_at;
    public String note;
    public String product_name;
    public String size_label;
    public String size_price;
    public String image;
    public String cart;

    public Order(String id, String ordered_at, String customer_id, String product_id, String size_id, String no_of_items, String payment_method, String status, String delivery_location, String delivered_at, String created_at, String updated_at, String note, String product_name, String size_label, String size_price, String image, String cart) {
        this.id = id;
        this.ordered_at = ordered_at;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.size_id = size_id;
        this.no_of_items = no_of_items;
        this.payment_method = payment_method;
        this.status = status;
        this.delivery_location = delivery_location;
        this.delivered_at = delivered_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.note = note;
        this.product_name = product_name;
        this.size_label = size_label;
        this.size_price = size_price;
        this.image = image;
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", ordered_at='" + ordered_at + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", size_id='" + size_id + '\'' +
                ", no_of_items='" + no_of_items + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", status='" + status + '\'' +
                ", delivery_location='" + delivery_location + '\'' +
                ", delivered_at='" + delivered_at + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", note='" + note + '\'' +
                ", product_name='" + product_name + '\'' +
                ", size_label='" + size_label + '\'' +
                ", size_price='" + size_price + '\'' +
                ", image='" + image + '\'' +
                ", cart='" + cart + '\'' +
                '}';
    }
}
