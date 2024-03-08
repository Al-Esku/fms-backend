package com.fencing.midsouth.fmswebsite.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String addressLineOne;

    private String addressLineTwo;

    private String suburb;

    private String city;

    private String country;

    private String postcode;

    public Location() {}

    public Location(String name,
                    String addressLineOne,
                    String addressLineTwo,
                    String suburb,
                    String city,
                    String country,
                    String postcode) {
        this.name = name;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.suburb = suburb;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
    }
}
