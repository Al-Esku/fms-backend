package com.fencing.midsouth.fmswebsite.model.dto;

public class HomeClubResponse {
    private final String shortName;

    private final String longName;

    private final String logoImage;

    public HomeClubResponse(String shortName, String longName, String logoImage) {
        this.shortName = shortName;
        this.longName = longName;
        this.logoImage = logoImage;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public String getLogoImage() {
        return logoImage;
    }
}
