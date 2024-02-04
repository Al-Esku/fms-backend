package com.fencing.midsouth.fmswebsite.controller.parser;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    private final DateTimeFormatter dateTimeFormatter;

    public LocalDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public LocalDateTime parse(String text, Locale locale) {
        return LocalDateTime.parse(text, dateTimeFormatter);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.format(dateTimeFormatter);
    }
}
