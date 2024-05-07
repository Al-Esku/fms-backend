package com.fencing.midsouth.fmswebsite.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LinkForm {
    @NotBlank(message = "Type is required")
    @Pattern(regexp = "(facebook)|(instagram)|(other)|(^$)", message = "Invalid Type")
    private String name;

    @NotBlank(message = "URL is required")
    @Pattern(regexp = "(((https?://(www\\.)?)|(www\\.)?)[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*))|(^$)", message = "Invalid URL")
    private String address;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
