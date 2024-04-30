package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.model.dto.ContactForm;
import com.fencing.midsouth.fmswebsite.model.dto.LinkForm;
import com.fencing.midsouth.fmswebsite.model.entity.Contact;
import com.fencing.midsouth.fmswebsite.model.entity.Link;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import com.fencing.midsouth.fmswebsite.model.map.ContactMapper;
import com.fencing.midsouth.fmswebsite.model.map.LinkMapper;
import com.fencing.midsouth.fmswebsite.service.JwtService;
import com.fencing.midsouth.fmswebsite.service.LinkService;
import com.fencing.midsouth.fmswebsite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/links")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteLink(@PathVariable String uuid) {
        logger.info("DELETE /api/sessions/%s".formatted(uuid));
        linkService.deleteLinkByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<?> createLink(@RequestBody LinkForm linkForm,
                                        @RequestHeader("Authorization") String bearerToken) {
        logger.info("POST /api/links/create");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Optional<User> user = userService.getUserByUsername(jwtService.extractUsername(token));
            if (user.isPresent()) {
                Link link = LinkMapper.map(linkForm, user.get().getClub());
                linkService.addLink(link);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{uuid}")
    public ResponseEntity<?> editContact(@RequestBody LinkForm linkForm,
                                         @RequestHeader("Authorization") String bearerToken,
                                         @PathVariable String uuid) {
        logger.info("PUT /api/contacts/%s".formatted(uuid));
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Optional<User> user = userService.getUserByUsername(jwtService.extractUsername(token));
            Link link = linkService.getLinkByUuid(uuid);
            if (user.isPresent()) {
                Link patchedLink = LinkMapper.patch(link, linkForm);
                linkService.updateLink(patchedLink);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
