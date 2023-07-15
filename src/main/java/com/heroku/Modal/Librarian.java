package com.heroku.Modal;

public class Librarian {
    private String name;
    private String lib_id;
    private String phone_number;
    private String email;
    private String password;

    public Librarian(String name, String lib_id, String phone_number, String email, String password) {
        this.name = name;
        this.lib_id = lib_id;
        this.phone_number= phone_number;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return lib_id;
    }

    public void setId(String lib_id) {
        this.lib_id = lib_id;
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