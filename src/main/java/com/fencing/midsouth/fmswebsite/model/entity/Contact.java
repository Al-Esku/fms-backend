package com.fencing.midsouth.fmswebsite.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String detail;

    @ManyToOne
    @JoinColumn(name = "club_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;

    public Contact() {
    }

    public Contact(String name, String detail, Club club) {
        this.name = name;
        this.detail = detail;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }
}
