package com.heroku.Modal;

public class User {
    private String name;
    private String user_id;
    private String phone_number;
    private String email;
    private String password;

    public User(String name, String user_id, String phone_number, String email, String password) {
        this.name = name;
        this.user_id = user_id;
        this.phone_number= phone_number;
        this.email = email;
        this.password = password;
    }

    // public User(String name, String user_id, String phone_number,  String password) {
    //     this.name = name;
    //     this.user_id = user_id;
    //     this.phone_number= phone_number;
    //     this.password = password;
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return user_id;
    }

    public void setId(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone_number;
    }

    public void setPhone(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}