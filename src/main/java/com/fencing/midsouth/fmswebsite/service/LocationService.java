package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.map.LocationMapper;
import com.fencing.midsouth.fmswebsite.repository.LocationRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.fencing.midsouth.fmswebsite.model.entity.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private final String apiKey;

    private final HttpClient httpClient;

    private final LocationRepository locationRepository;

    public LocationService(@Value("${mapbox.key:null}") String apiKey, LocationRepository locationRepository) {
        this.apiKey = apiKey;
        this.locationRepository = locationRepository;
        this.httpClient = HttpClient.newBuilder().build();
    }

    public List<Location> findLocationsFromQuery(String query) {
        List<Location> mappedLocations = new ArrayList<>();
        try {
            query = query.replace(" ", "%20");
            HttpRequest request = HttpRequest.newBuilder(
                    URI.create("https://api.mapbox.com/geocoding/v5/mapbox.places/"
                    + query + ".json"
                    + "?proximity=172.636645%2C-43.530955&types=address"
                    + "&access_token=" + apiKey)
                    ).build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            JsonArray locations = jsonObject.get("features").getAsJsonArray();

            locations.forEach(jsonElement -> mappedLocations.add(LocationMapper.mapFromJson(jsonElement.getAsJsonObject())));
        } catch (Exception e) {

        }
        return mappedLocations;
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }
}
