package com.fencing.midsouth.fmswebsite.asset;

public enum WeekDay {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");
    private String value;

    private WeekDay(String value) {
        this.value = value;
    }
}
