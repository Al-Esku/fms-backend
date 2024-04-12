package com.fencing.midsouth.fmswebsite.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String address;

    @ManyToOne
    @JoinColumn(name = "club_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;

    public Link() {
    }

    public Link(String name, String address, Club club) {
        this.name = name;
        this.address = address;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
