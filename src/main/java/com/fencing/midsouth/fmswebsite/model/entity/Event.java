package com.fencing.midsouth.fmswebsite.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "char(36)")
    private String uuid = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Location location;

    @Column(length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column
    private String type;

    @Column
    private boolean registrationRequired;

    @Column
    private String registrationLink;

    @Column
    private String resultsLink;

    @Column
    private ZonedDateTime startDate;

    @Column
    private ZonedDateTime endDate;

    public Event(String name,
                 String description,
                 String type,
                 Location location,
                 boolean registrationRequired,
                 String registrationLink,
                 String resultsLink,
                 ZonedDateTime startDate,
                 ZonedDateTime endDate) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.location = location;
        this.registrationRequired = registrationRequired;
        this.registrationLink = registrationLink;
        this.resultsLink = resultsLink;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event() {

    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return (uuid == null) ? null : UUID.fromString(uuid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public boolean getRegistrationRequired() {
        return registrationRequired;
    }

    public String getRegistrationLink() {
        return registrationLink;
    }

    public String getResultsLink() {
        return resultsLink;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
