package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteEvent(@PathVariable String uuid) {
        logger.info("DELETE /api/sessions/%s".formatted(uuid));
        contactService.deleteSessionByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
