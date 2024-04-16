package com.fencing.midsouth.fmswebsite.model.dto;

import com.fencing.midsouth.fmswebsite.model.entity.Role;

import java.util.Optional;
import java.util.Set;

public class UserResponse {
    private final String name;

    private final Set<Role> roles;

    private final ClubResponse club;

    public UserResponse(String name, Set<Role> roles, ClubResponse club) {
        this.name = name;
        this.roles = roles;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public ClubResponse getClub() {
        return club;
    }
}
