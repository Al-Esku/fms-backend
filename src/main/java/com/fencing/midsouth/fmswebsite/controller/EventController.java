package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.asset.GenericConstants;
import com.fencing.midsouth.fmswebsite.model.dto.EventForm;
import com.fencing.midsouth.fmswebsite.model.dto.EventResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import com.fencing.midsouth.fmswebsite.model.map.EventMapper;
import com.fencing.midsouth.fmswebsite.service.EventService;
import com.fencing.midsouth.fmswebsite.service.JwtService;
import com.fencing.midsouth.fmswebsite.service.LocationService;
import com.fencing.midsouth.fmswebsite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static com.fencing.midsouth.fmswebsite.model.map.EventMapper.patch;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    private final JwtService jwtService;

    private final UserService userService;

    private final LocationService locationService;

    public EventController(EventService eventService, JwtService jwtService, UserService userService, LocationService locationService) {
        this.eventService = eventService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.locationService = locationService;
    }

    @GetMapping("")
    public ResponseEntity<Page<EventResponse>> getUpcomingEvents(
            @RequestParam(name = "upcoming", defaultValue = "true") boolean upcoming,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "types", required = false) String[] types,
            @RequestParam(name = "creators", required = false) String[] creators,
            @RequestParam(name = "before", required = false) ZonedDateTime before,
            @RequestParam(name = "after", required = false) ZonedDateTime after) {
        logger.info("GET /api/events");
        Page<Event> events;
        if (before != null) {
            if (after != null) {
                events = eventService.getEventsBetweenDates(name.toLowerCase(), offset, GenericConstants.DEFAULT_PAGE_SIZE, types, creators, before.plusDays(1).minusNanos(1), after);
            } else {
                events = eventService.getEventsBeforeDate(name.toLowerCase(), offset, GenericConstants.DEFAULT_PAGE_SIZE, types, creators, before.plusDays(1).minusNanos(1));
            }
        } else if (after != null) {
            events = eventService.getEventsAfterDate(name.toLowerCase(), offset, GenericConstants.DEFAULT_PAGE_SIZE, types, creators, after);
        } else if (upcoming) {
            events = eventService.getUpcomingEvents(name.toLowerCase(), offset, GenericConstants.DEFAULT_PAGE_SIZE, types, creators);
        } else {
            events = eventService.getPastEvents(name.toLowerCase(), offset, GenericConstants.DEFAULT_PAGE_SIZE, types, creators);
        }
        Page<EventResponse> responses = events.map(EventMapper::responseMap);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable String uuid) {
        EventResponse eventResponse = EventMapper.responseMap(eventService.getEventFromUuid(uuid));
        return new ResponseEntity<>(eventResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createEvent(@Validated @RequestBody EventForm eventForm,
                                         BindingResult bindingResult,
                                         @RequestHeader("Authorization") String bearerToken) {
        logger.info("POST /api/events/create");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Optional<User> user = userService.getUserByUsername(jwtService.extractUsername(token));
            if (user.isPresent()) {
                try {
                    ZonedDateTime.parse(eventForm.getStartDate());
                } catch (DateTimeParseException e) {
                    bindingResult.rejectValue("startDate", "Invalid start date", "Invalid start date");
                } catch (NullPointerException e) {
                    bindingResult.rejectValue("startDate", "Start date is required", "Start date is required");
                }
                try {
                    ZonedDateTime.parse(eventForm.getEndDate());
                } catch (DateTimeParseException e) {
                    bindingResult.rejectValue("endDate", "Invalid end date", "Invalid end date");
                } catch (NullPointerException e) {
                    bindingResult.rejectValue("endDate", "End date is required", "End date is required");
                }
                if (bindingResult.hasErrors()) {
                    return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
                }
                Event event = EventMapper.map(eventForm);
                event.setUser(user.get());
                if (event.getLocation() != null) {
                    locationService.saveLocation(event.getLocation());
                }
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

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteEvent(@PathVariable String uuid) {
        logger.info("DELETE /api/events/%s".formatted(uuid));
        eventService.deleteEventByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> editEvent(@PathVariable String uuid,
                                       @Validated @RequestBody EventForm eventForm,
                                       BindingResult bindingResult,
                                       @RequestHeader("Authorization") String bearerToken) {
        logger.info("PUT /api/events/%s".formatted(uuid));
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Event event = eventService.getEventFromUuid(uuid);
            Optional<User> user = userService.getUserByUsername(jwtService.extractUsername(token));
            if (user.isPresent() && user.get() == event.getUser()) {
                try {
                    ZonedDateTime.parse(eventForm.getStartDate());
                } catch (DateTimeParseException | NullPointerException e) {
                    bindingResult.rejectValue("startDate", "Invalid start date", "Invalid start date");
                }
                try {
                    ZonedDateTime.parse(eventForm.getEndDate());
                } catch (DateTimeParseException | NullPointerException e) {
                    bindingResult.rejectValue("endDate", "Invalid end date", "Invalid end date");
                }
                if (bindingResult.hasErrors()) {
                    return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
                }
                Event patchedEvent = patch(event, eventForm);
                if (patchedEvent.getLocation() != null) {
                    locationService.saveLocation(patchedEvent.getLocation());
                }
                eventService.updateEvent(patchedEvent);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
