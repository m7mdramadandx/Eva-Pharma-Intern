package com.ramadan.eva.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phone {

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("office")
    @Expose
    private String office;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "mobile='" + mobile + '\'' +
                ", home='" + home + '\'' +
                ", office='" + office + '\'' +
                '}';
    }
}
