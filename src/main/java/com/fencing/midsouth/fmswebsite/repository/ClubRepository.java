package com.fencing.midsouth.fmswebsite.repository;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClubRepository extends CrudRepository<Club, Long> {
    Optional<Club> findClubByShortName(String name);
}
