package com.fencing.midsouth.fmswebsite.model.dto;

import com.fencing.midsouth.fmswebsite.asset.Type;
import com.fencing.midsouth.fmswebsite.model.entity.Location;
import com.fencing.midsouth.fmswebsite.model.entity.User;

import com.fencing.midsouth.fmswebsite.model.validation.Description;
import com.fencing.midsouth.fmswebsite.model.validation.EndDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.Optional;

@Description(fieldName = "description")
@EndDate(fieldName = "endDate")
public class EventForm {

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(regexp = "(Competition)|(Training)|(Other)|(^$)", message = "Invalid Type")
    @NotBlank(message = "Type is required")
    private String type;

    private String description;

    private boolean registrationRequired;

    @Pattern(regexp = "(((https?://(www\\.)?)|(www\\.))[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*))|(^$)", message = "Invalid registration link")
    private String registrationLink;

    @Pattern(regexp = "(((https?://(www\\.)?)|(www\\.))[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*))|(^$)", message = "Invalid results link")
    private String resultsLink;

    private String startDate;

    private String endDate;

    private String location;

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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public Optional<User> getUser() {
        return optionalUser;
    }
}
