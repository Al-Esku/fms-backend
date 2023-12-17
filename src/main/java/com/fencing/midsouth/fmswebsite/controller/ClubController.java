package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.service.ClubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {
    private final ClubService clubService;
    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);


    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Club> getClub(@PathVariable String name) {
        logger.info("GET /api/clubs/{}", name);
        Optional<Club> club = clubService.getClubByName(name);
        return club.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

}
