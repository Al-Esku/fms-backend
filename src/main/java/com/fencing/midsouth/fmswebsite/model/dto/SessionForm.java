package com.fencing.midsouth.fmswebsite.model.dto;

import com.fencing.midsouth.fmswebsite.asset.WeekDay;

public class SessionForm {

    private String name;

    private String description;

    private WeekDay weekDay;

    private String startTime;

    private String endTime;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
