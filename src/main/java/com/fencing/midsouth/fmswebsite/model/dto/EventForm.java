package com.fencing.midsouth.fmswebsite.model.dto;

import com.fencing.midsouth.fmswebsite.asset.Type;
import com.fencing.midsouth.fmswebsite.model.entity.Location;
import com.fencing.midsouth.fmswebsite.model.entity.User;

import com.fencing.midsouth.fmswebsite.model.validation.Description;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.Optional;

@Description(fieldName = "description")
public class EventForm {

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(regexp = "(Competition)|(Training)|(Other)|(^$)", message = "Invalid Type")
    @NotBlank(message = "Type is required")
    private String type;

    private String description;

    private boolean registrationRequired;

    private String registrationLink;

    private String resultsLink;

    @NotBlank(message = "Start Date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private Location location;

    private Optional<User> optionalUser;

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

    public Optional<User> getUser() {
        return optionalUser;
    }
}
