package com.fencing.midsouth.fmswebsite.model.map;


import com.fencing.midsouth.fmswebsite.model.entity.Location;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class LocationMapper {
    public static Location mapFromJson(JsonObject json) {
        Location location = new Location();
        location.setName(json.get("place_name").getAsString());
        if (json.has("address")) {
            location.setAddressLineOne("%s %s".formatted(json.get("address").getAsString(), json.get("text").getAsString()));
        } else {
            location.setAddressLineOne(json.get("text").getAsString());
        }
        location.setAddressLineTwo("");

        JsonArray context = json.get("context").getAsJsonArray();
        location.setPostcode(context.get(0).getAsJsonObject().get("text").getAsString());
        location.setSuburb(context.get(1).getAsJsonObject().get("text").getAsString());
        location.setCity(context.get(2).getAsJsonObject().get("text").getAsString());
        location.setCountry(context.get(4).getAsJsonObject().get("text").getAsString());

        return location;
    }
}
