package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.asset.GenericConstants;
import com.fencing.midsouth.fmswebsite.model.dto.EventForm;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import com.fencing.midsouth.fmswebsite.model.map.EventMapper;
import com.fencing.midsouth.fmswebsite.service.EventService;
import com.fencing.midsouth.fmswebsite.service.JwtService;
import com.fencing.midsouth.fmswebsite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    private final JwtService jwtService;

    private final UserService userService;

    public EventController(EventService eventService, JwtService jwtService, UserService userService) {
        this.eventService = eventService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/past")
    public ResponseEntity<Page<EventResponse>> getPastEvents(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "offset", defaultValue = "0") int offset) {
        logger.info("GET /api/events/past");
        Page<Event> events = eventService.getPastEvents(name.toLowerCase(), offset, GenericConstants.DEFAULT_PAGE_SIZE);
        Page<EventResponse> responses = new PageImpl<>(events.stream().map(EventMapper::responseMap).toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Page<EventResponse>> getUpcomingEvents(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "offset", defaultValue = "0") int offset) {
        logger.info("GET /api/events/upcoming");
        Page<Event> events = eventService.getUpcomingEvents(name.toLowerCase(), offset, GenericConstants.DEFAULT_PAGE_SIZE);
        Page<EventResponse> responses = events.map(EventMapper::responseMap);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createEvent(@RequestBody EventForm eventForm, @RequestHeader("Authorization") String bearerToken) {
        logger.info("POST /api/events/create");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Event event = EventMapper.map(eventForm);
            Optional<User> user = userService.getUserByUsername(jwtService.extractUsername(token));
            if (user.isPresent()) {
                event.setUser(user.get());
                eventService.addEvent(event);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/month")
    public ResponseEntity<List<Event>> getEventsByDates(@RequestParam(name = "year") int year,
                                                        @RequestParam(name = "month") int month) {
        List<Event> events = eventService.getEventsByMonth(year, month);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
