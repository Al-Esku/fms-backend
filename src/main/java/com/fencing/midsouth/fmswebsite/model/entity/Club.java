package com.fencing.midsouth.fmswebsite.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String shortName;

    @Column(length = 100)
    private String longName;

    @Column(length = 1000)
    private String description;

    @Column(length = 20)
    private String logoImage;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Location location;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public String getDescription() {
        return description;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public Location getLocation() {
        return location;
    }

    public User getUser() {
        return user;
    }
}
