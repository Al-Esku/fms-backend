package com.fencing.midsouth.fmswebsite.model.entity;

import com.fencing.midsouth.fmswebsite.asset.WeekDay;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Time;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Time startTime;

    @Column
    private Time endTime;

    @Column
    private WeekDay weekDay;

    @ManyToOne
    @JoinColumn(name = "club_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;

    public Session() {
    }

    public Session(String name, String description, Time startTime, Time endTime, Club club, WeekDay day) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.club = club;
        this.weekDay = day;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }
}
