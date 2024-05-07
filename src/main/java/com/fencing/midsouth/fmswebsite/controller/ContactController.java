package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.model.dto.ContactForm;
import com.fencing.midsouth.fmswebsite.model.dto.SessionForm;
import com.fencing.midsouth.fmswebsite.model.entity.Contact;
import com.fencing.midsouth.fmswebsite.model.entity.Session;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import com.fencing.midsouth.fmswebsite.model.map.ContactMapper;
import com.fencing.midsouth.fmswebsite.model.map.SessionMapper;
import com.fencing.midsouth.fmswebsite.service.ContactService;
import com.fencing.midsouth.fmswebsite.service.JwtService;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteContact(@PathVariable String uuid) {
        logger.info("DELETE /api/sessions/%s".formatted(uuid));
        contactService.deleteSessionByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<?> createContact(@Validated @RequestBody ContactForm contactForm,
                                           BindingResult bindingResult,
                                           @RequestHeader("Authorization") String bearerToken) {
        logger.info("POST /api/contacts/create");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Optional<User> user = userService.getUserByUsername(jwtService.extractUsername(token));
            if (user.isPresent()) {
                if (bindingResult.hasErrors()) {
                    return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
                }
                Contact contact = ContactMapper.map(contactForm, user.get().getClub());
                contactService.addContact(contact);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{uuid}")
    public ResponseEntity<?> editContact(@Validated @RequestBody ContactForm contactForm,
                                         BindingResult bindingResult,
                                         @RequestHeader("Authorization") String bearerToken,
                                         @PathVariable String uuid) {
        logger.info("PUT /api/contacts/%s".formatted(uuid));
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Optional<User> user = userService.getUserByUsername(jwtService.extractUsername(token));
            Contact contact = contactService.getContactByUuid(uuid);
            if (user.isPresent()) {
                if (bindingResult.hasErrors()) {
                    return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
                }
                Contact patchedContact = ContactMapper.patch(contact, contactForm);
                contactService.updateContact(patchedContact);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
