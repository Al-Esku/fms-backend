package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.model.dto.EventDateDto;
import com.fencing.midsouth.fmswebsite.model.dto.EventForm;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import com.fencing.midsouth.fmswebsite.model.map.EventMapper;
import com.fencing.midsouth.fmswebsite.service.CustomUserDetailsService;
import com.fencing.midsouth.fmswebsite.service.EventService;
import com.fencing.midsouth.fmswebsite.service.JwtService;
import com.fencing.midsouth.fmswebsite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<EventResponse>> getPastEvents(@RequestParam(name = "name",
            required = false, defaultValue = "") String name) {
        logger.info("GET /api/events/past");
        List<Event> events = eventService.getPastEvents(name.toLowerCase());
        List<EventResponse> responses = events.stream().map(EventMapper::responseMap).toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponse>> getUpcomingEvents(@RequestParam(name = "name",
            required = false, defaultValue = "") String name) {
        logger.info("GET /api/events/upcoming");
        List<Event> events = eventService.getUpcomingEvents(name.toLowerCase());
        List<EventResponse> responses = events.stream().map(EventMapper::responseMap).toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createEvent(@RequestBody EventForm eventForm, @RequestHeader("Authorization") String bearerToken) {
        logger.info("POST /api/events/create");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Event event = EventMapper.map(eventForm);
            event.setUser(userService.getUserByUsername(jwtService.extractUsername(token)).get());
            eventService.addEvent(event);
            return ResponseEntity.status(201).build();
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
