package com.fencing.midsouth.fmswebsite.model.dto;

import com.fencing.midsouth.fmswebsite.model.entity.Location;

import java.time.ZonedDateTime;
import java.util.UUID;

public class EventResponse {
    private final String name;

    private final UUID uuid;

    private final String creatorName;

    private final String description;

    private final String type;

    private final boolean registrationRequired;

    private final String registrationLink;

    private final String resultsLink;

    private final ZonedDateTime startDate;

    private final ZonedDateTime endDate;

    private final Location location;

    public EventResponse(String name,
                         UUID uuid,
                         String creatorName,
                         String description,
                         String type,
                         boolean registrationRequired,
                         String registrationLink,
                         String resultsLink,
                         ZonedDateTime startDate,
                         ZonedDateTime endDate,
                         Location location) {
        this.name = name;
        this.uuid = uuid;
        this.creatorName = creatorName;
        this.description = description;
        this.type = type;
        this.registrationRequired = registrationRequired;
        this.registrationLink = registrationLink;
        this.resultsLink = resultsLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
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
