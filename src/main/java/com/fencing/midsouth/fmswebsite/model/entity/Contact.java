package com.fencing.midsouth.fmswebsite.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String info;

    @ManyToOne
    @JoinColumn(name = "club_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;

    @Column(unique = true, nullable = false, columnDefinition = "char(36)")
    private String uuid = UUID.randomUUID().toString();

    public Contact() {
    }

    public Contact(String name, String info, Club club) {
        this.name = name;
        this.info = info;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String detail) {
        this.info = detail;
    }
}
