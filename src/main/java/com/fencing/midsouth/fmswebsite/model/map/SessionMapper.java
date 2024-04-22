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

    public static Session patch(Session session, SessionForm sessionForm) {
        if (sessionForm.getName() != null && !sessionForm.getName().isBlank()) {
            session.setName(sessionForm.getName());
        }
        if (sessionForm.getDescription() != null && !sessionForm.getDescription().isBlank()) {
            session.setDescription(sessionForm.getDescription());
        }
        if (sessionForm.getStartTime() != null && !sessionForm.getStartTime().isBlank()) {
            session.setStartTime(Time.valueOf(sessionForm.getStartTime()));
        }
        if (sessionForm.getEndTime() != null && !sessionForm.getEndTime().isBlank()) {
            session.setEndTime(Time.valueOf(sessionForm.getEndTime()));
        }
        if (sessionForm.getWeekDay() != null) {
            session.setWeekDay(sessionForm.getWeekDay());
        }
        return session;
    }
}
