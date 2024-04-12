package com.fencing.midsouth.fmswebsite.model.map;

import com.fencing.midsouth.fmswebsite.model.dto.ClubResponse;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Contact;
import com.fencing.midsouth.fmswebsite.model.entity.Link;
import com.fencing.midsouth.fmswebsite.model.entity.Session;

import java.util.List;

public class ClubMapper {
    public static ClubResponse map(Club club,
                                   List<Session> sessions,
                                   List<Contact> contacts,
                                   List<EventResponse> events,
                                   List<Link> links) {
        return new ClubResponse(club.getShortName(),
                club.getLongName(),
                club.getDescription(),
                club.getLogoImage(),
                club.getLocation(),
                sessions,
                contacts,
                events,
                links);
    }
}
