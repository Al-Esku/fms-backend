package com.fencing.midsouth.fmswebsite.model.entity;

import com.fencing.midsouth.fmswebsite.asset.WeekDay;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Time;
import java.util.UUID;

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
    private String weekDay;

    @ManyToOne
    @JoinColumn(name = "club_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;

    @Column(unique = true, nullable = false, columnDefinition = "char(36)")
    private String uuid = UUID.randomUUID().toString();

    public Session() {
    }

    public Session(String name, String description, Time startTime, Time endTime, Club club, String day) {
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

    public String getWeekDay() {
        return weekDay;
    }

    public String getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
