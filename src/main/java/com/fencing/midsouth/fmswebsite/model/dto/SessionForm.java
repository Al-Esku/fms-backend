package com.fencing.midsouth.fmswebsite.model.dto;

import com.fencing.midsouth.fmswebsite.asset.WeekDay;
import com.fencing.midsouth.fmswebsite.model.validation.EndTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@EndTime(fieldName = "endTime")
public class SessionForm {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Weekday is required")
    @Pattern(regexp = "(?i)(monday)|(tuesday)|(wednesday)|(thursday)|(friday)|(saturday)|(sunday)|(^$)", message = "Invalid weekday")
    private String weekDay;

    private String startTime;

    private String endTime;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
