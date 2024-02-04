package com.fencing.midsouth.fmswebsite.model.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public class EventResponse {
    private String name;

    private UUID uuid;

    private String creatorName;

    private String description;

    private String type;

    private boolean registrationRequired;

    private String registrationLink;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    public EventResponse(String name,
                         UUID uuid,
                         String creatorName,
                         String description,
                         String type,
                         boolean registrationRequired,
                         String registrationLink,
                         ZonedDateTime startDate,
                         ZonedDateTime endDate) {
        this.name = name;
        this.uuid = uuid;
        this.creatorName = creatorName;
        this.description = description;
        this.type = type;
        this.registrationRequired = registrationRequired;
        this.registrationLink = registrationLink;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }
}
