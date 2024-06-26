package com.fencing.midsouth.fmswebsite.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fencing.midsouth.fmswebsite.model.dto.SessionForm;
import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.model.entity.Location;
import com.fencing.midsouth.fmswebsite.model.entity.Session;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import com.fencing.midsouth.fmswebsite.model.map.EventMapper;
import com.fencing.midsouth.fmswebsite.model.map.SessionMapper;
import com.fencing.midsouth.fmswebsite.service.JwtService;
import com.fencing.midsouth.fmswebsite.service.SessionService;
import com.fencing.midsouth.fmswebsite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteSession(@PathVariable String uuid) {
        logger.info("DELETE /api/sessions/%s".formatted(uuid));
        sessionService.deleteSessionByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<?> createSession(@Validated @RequestBody SessionForm sessionForm,
                                           BindingResult bindingResult,
                                           @RequestHeader("Authorization") String bearerToken) {
        logger.info("POST /api/sessions/create");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Optional<User> user = userService.getUserByUsername(jwtService.extractUsername(token));
            if (user.isPresent()) {
                try {
                    Time.valueOf(sessionForm.getStartTime());
                } catch (IllegalArgumentException e) {
                    bindingResult.rejectValue("startTime", "Invalid start time", "Invalid start time");
                }
                try {
                    Time.valueOf(sessionForm.getEndTime());
                } catch (IllegalArgumentException e) {
                    bindingResult.rejectValue("endTime", "Invalid end time", "Invalid end time");
                }
                if (bindingResult.hasErrors()) {
                    return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
                }
                Session session = SessionMapper.map(sessionForm, user.get().getClub());
                sessionService.addSession(session);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{uuid}")
    public ResponseEntity<?> editSession(@Validated @RequestBody SessionForm sessionForm,
                                         BindingResult bindingResult,
                                         @RequestHeader("Authorization") String bearerToken,
                                         @PathVariable String uuid) {
        logger.info("POST /api/sessions/create");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Optional<User> user = userService.getUserByUsername(jwtService.extractUsername(token));
            Session session = sessionService.getSessionByUuid(uuid);
            if (user.isPresent()) {
                try {
                    Time.valueOf(sessionForm.getStartTime());
                } catch (IllegalArgumentException e) {
                    bindingResult.rejectValue("startTime", "Invalid start time", "Invalid start time");
                }
                try {
                    Time.valueOf(sessionForm.getEndTime());
                } catch (IllegalArgumentException e) {
                    bindingResult.rejectValue("endTime", "Invalid end time", "Invalid end time");
                }
                if (bindingResult.hasErrors()) {
                    return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
                }
                Session patchedSession = SessionMapper.patch(session, sessionForm);
                sessionService.updateSession(patchedSession);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
