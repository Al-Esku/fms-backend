package com.fencing.midsouth.fmswebsite.model.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fencing.midsouth.fmswebsite.model.dto.ClubForm;
import com.fencing.midsouth.fmswebsite.model.dto.ClubResponse;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.dto.HomeClubResponse;
import com.fencing.midsouth.fmswebsite.model.entity.*;

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
                links,
                club.getUuid());
    }

    public static Club patch(Club club, ClubForm clubForm) {
        if (clubForm.getDescription() != null && !clubForm.getDescription().isBlank()) {
            club.setDescription(clubForm.getDescription());
        }
        if (clubForm.getLocation() != null && !clubForm.getLocation().isBlank()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                club.setLocation(objectMapper.readValue(clubForm.getLocation(), Location.class));
            } catch (JsonProcessingException | IllegalArgumentException e) {
                club.setLocation(null);
            }
        }
        return club;
    }

    public static HomeClubResponse homeMap(Club club) {
        return new HomeClubResponse(club.getShortName(), club.getLongName(), club.getLogoImage());
    }
}
