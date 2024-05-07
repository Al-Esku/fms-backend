package com.fencing.midsouth.fmswebsite.model.dto;

import com.fencing.midsouth.fmswebsite.model.validation.Description;

@Description(fieldName = "description")
public class ClubForm extends DescriptionForm {

    private String description;

    private String location;

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }
}
