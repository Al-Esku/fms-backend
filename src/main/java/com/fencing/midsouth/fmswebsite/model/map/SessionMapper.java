package com.fencing.midsouth.fmswebsite.model.map;

import com.fencing.midsouth.fmswebsite.model.dto.SessionForm;
import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Session;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;

public class SessionMapper {
    public static Session map(SessionForm sessionForm, Club club) {
        return new Session(sessionForm.getName(),
                sessionForm.getDescription(),
                Time.valueOf(sessionForm.getStartTime()),
                Time.valueOf(sessionForm.getEndTime()),
                club,
                sessionForm.getWeekDay());
    }
}
