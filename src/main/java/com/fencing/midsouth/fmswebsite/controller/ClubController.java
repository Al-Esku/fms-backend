package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.model.dto.ClubForm;
import com.fencing.midsouth.fmswebsite.model.dto.ClubResponse;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Contact;
import com.fencing.midsouth.fmswebsite.model.entity.Link;
import com.fencing.midsouth.fmswebsite.model.entity.Session;
import com.fencing.midsouth.fmswebsite.model.map.ClubMapper;
import com.fencing.midsouth.fmswebsite.model.map.EventMapper;
import com.fencing.midsouth.fmswebsite.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {
    private final ClubService clubService;
    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

    private final SessionService sessionService;

    private final ContactService contactService;

    private final EventService eventService;

    private final LinkService linkService;

    public ClubController(ClubService clubService, SessionService sessionService, ContactService contactService, EventService eventService, LinkService linkService) {
        this.clubService = clubService;
        this.sessionService = sessionService;
        this.contactService = contactService;
        this.eventService = eventService;
        this.linkService = linkService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<ClubResponse> getClub(@PathVariable String name) {
        logger.info("GET /api/clubs/{}", name);
        Optional<Club> club = clubService.getClubByName(name);
        if (club.isPresent()) {
            List<Session> sessions = sessionService.getSessionByClub(club.get());
            List<Contact> contacts = contactService.getContactsByClub(club.get());
            List<EventResponse> events = eventService.getTop2UpcomingEventsByClub(club.get()).stream().map(EventMapper::responseMap).toList();
            List<Link> links = linkService.getLinksByClub(club.get());
            return ResponseEntity.ok(ClubMapper.map(club.get(), sessions, contacts, events, links));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{uuid}")
    public ResponseEntity<?> updateClub(@PathVariable String uuid,
                                        @RequestBody ClubForm clubForm) {
        logger.info("GET /api/clubs/{}", uuid);
        Optional<Club> club = clubService.getClubByUuid(uuid);
        if (club.isPresent()) {
            Club patchedClub = ClubMapper.patch(club.get(), clubForm);
            clubService.updateClub(patchedClub);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
