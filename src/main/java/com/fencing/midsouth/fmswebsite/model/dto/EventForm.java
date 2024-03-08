package com.fencing.midsouth.fmswebsite.model.dto;

import com.fencing.midsouth.fmswebsite.model.entity.Location;

import java.time.ZonedDateTime;

public class EventForm {

    private String name;

    private String type;

    private String description;

    private boolean registrationRequired;

    private String registrationLink;

    private String resultsLink;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private Location location;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRegistrationRequired() {
        return registrationRequired;
    }

    public String getRegistrationLink() {
        return registrationLink;
    }

    public String getResultsLink() {
        return resultsLink;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Location getLocation() {
        return location;
    }
}
