package com.fencing.midsouth.fmswebsite.model.dto;

import java.time.ZonedDateTime;

public class EventForm {

    private String name;

    private String type;

    private String description;

    private boolean registrationRequired;

    private String registrationLink;

    private boolean results;

    private String resultsLink;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

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

    public boolean isResults() {
        return results;
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
}
