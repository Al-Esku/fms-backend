package com.fencing.midsouth.fmswebsite.model.dto;

import com.fencing.midsouth.fmswebsite.model.entity.*;

import java.util.List;

public class ClubResponse {
    private final String shortName;

    private final String longName;

    private final String description;

    private final String logoImage;

    private final Location location;

    private final List<Session> sessions;

    private final List<Contact> contacts;

    private final List<EventResponse> events;

    private final List<Link> links;

    private final String uuid;

    public ClubResponse(String shortName, String longName, String description, String logoImage, Location location, List<Session> sessions, List<Contact> contacts, List<EventResponse> events, List<Link> links, String uuid) {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.logoImage = logoImage;
        this.location = location;
        this.sessions = sessions;
        this.contacts = contacts;
        this.events = events;
        this.links = links;
        this.uuid = uuid;
    }

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

    public List<Session> getSessions() {
        return sessions;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public List<EventResponse> getEvents() {
        return events;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getUuid() {
        return uuid;
    }
}
