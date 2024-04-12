package com.fencing.midsouth.fmswebsite.model.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class Blacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Date timestamp;

    public Blacklist() {
    }

    public Blacklist(User user, Date timestamp) {
        this.user = user;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
