package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.service.LinkService;
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
@RequestMapping("/api/links")
public class LinkController {
    @Autowired
    private LinkService linkService;

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteLink(@PathVariable String uuid) {
        logger.info("DELETE /api/sessions/%s".formatted(uuid));
        linkService.deleteLinkByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
