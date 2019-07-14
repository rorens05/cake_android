package com.example.pristinepastries.models;

public class User {
    public String id;
    public String name;
    public String address;
    public String contact_no;
    public String email;
    public String gender;
    public String status;
    public String created_at;
    public String updated_at;
    public String password_digest;
    public String image;

    public User(String id, String name, String address, String contact_no, String email, String gender, String status, String created_at, String updated_at, String password_digest, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact_no = contact_no;
        this.email = email;
        this.gender = gender;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.password_digest = password_digest;
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact_no='" + contact_no + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", password_digest='" + password_digest + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}


