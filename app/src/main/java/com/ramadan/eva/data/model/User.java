package com.ramadan.eva.data.model;

import java.io.Serializable;

public class User implements Serializable {
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    String password;
    String picture;

    public User(String firstName, String lastName, String email, String phone, String password, String picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.picture = picture;
    }
}
