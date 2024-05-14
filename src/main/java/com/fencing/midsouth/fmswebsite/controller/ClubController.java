package com.fencing.midsouth.fmswebsite.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fencing.midsouth.fmswebsite.model.dto.ClubForm;
import com.fencing.midsouth.fmswebsite.model.dto.ClubResponse;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.dto.HomeClubResponse;
import com.fencing.midsouth.fmswebsite.model.entity.*;
import com.fencing.midsouth.fmswebsite.model.map.ClubMapper;
import com.fencing.midsouth.fmswebsite.model.map.EventMapper;
import com.fencing.midsouth.fmswebsite.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {
    private final ClubService clubService;
    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

    private final SessionService sessionService;

    private final ContactService contactService;

    private final EventService eventService;

    private final LinkService linkService;

    @Autowired
    private LocationService locationService;

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

    @GetMapping("")
    public ResponseEntity<List<HomeClubResponse>> getClubs() {
        logger.info("GET /api/clubs");
        List<Club> clubs = clubService.getClubs();
        return ResponseEntity.ok(clubs.stream().map(ClubMapper::homeMap).collect(Collectors.toList()));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{uuid}")
    public ResponseEntity<?> updateClub(@PathVariable String uuid,
                                        @Validated @RequestBody ClubForm clubForm,
                                        BindingResult bindingResult) {
        logger.info("GET /api/clubs/{}", uuid);
        Optional<Club> club = clubService.getClubByUuid(uuid);
        if (club.isPresent()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.readValue(clubForm.getLocation(), Location.class);
            } catch (JsonProcessingException e) {
                bindingResult.rejectValue("location", "Invalid location", "Invalid location");
            } catch (IllegalArgumentException ignored) {

            }
            if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
            }
            Club patchedClub = ClubMapper.patch(club.get(), clubForm);
            if (patchedClub.getLocation() != null) {
                locationService.saveLocation(patchedClub.getLocation());
            }
            clubService.updateClub(patchedClub);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
