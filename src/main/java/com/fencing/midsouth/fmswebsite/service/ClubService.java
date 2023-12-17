package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.repository.ClubRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClubService {
    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public Optional<Club> getClubByName(String name) {
        return clubRepository.findClubByShortName(name);
    }
}
