package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.model.entity.Location;
import com.fencing.midsouth.fmswebsite.service.LocationService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("")
    public ResponseEntity<List<Location>> getLocations(@RequestParam String query) {
        List<Location> locations = locationService.findLocationsFromQuery(query);
        return new ResponseEntity<>(locations, HttpStatusCode.valueOf(200));
    }
}
