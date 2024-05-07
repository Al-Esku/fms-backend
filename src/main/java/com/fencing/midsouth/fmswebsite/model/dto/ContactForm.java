package com.fencing.midsouth.fmswebsite.model.dto;

import jakarta.validation.constraints.NotBlank;

public class ContactForm {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Info is required")
    private String info;

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}
