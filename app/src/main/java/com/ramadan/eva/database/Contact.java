package com.ramadan.eva.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CONTACTS")
public class Contact {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "CONTACT_ID")
    public String contactID;

    @ColumnInfo(name = "NAME")
    public String name;

    @ColumnInfo(name = "EMAIL")
    public String email;

    @ColumnInfo(name = "ADDRESS")
    public String address;

    @ColumnInfo(name = "GENDER")
    public String gender;

    @ColumnInfo(name = "MOBILE")
    public String mobile;

    @ColumnInfo(name = "HOME")
    public String home;

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + contactID + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", mobile=" + mobile +
                '}';
    }
}
