package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.repository.ClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClubService {
    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public Optional<Club> getClubByName(String name) {
        return clubRepository.findClubByShortName(name);
    }

    public Optional<Club> getClubByUuid(String uuid) {
        return clubRepository.findClubByUuid(uuid);
    }

    public void updateClub(Club club) {
        if (clubRepository.existsByUuid(club.getUuid())) {
            clubRepository.save(club);
        }
    }

    public List<Club> getClubs() {
        return StreamSupport.stream(clubRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
