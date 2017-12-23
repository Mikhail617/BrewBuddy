package com.example.mikhailefroimson.brewbuddy;

/**
 * Created by mikhail.efroimson on 12/22/2017.
 */

public class Brewery {
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getType() {
        return type;
    }

    private String name;
    private String type;
    private String address;
    private String phone;
    private String website;

    public Brewery(String name,
                   String type,
                   String address,
                   String phone,
                   String website) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.website = website;
    }

}
