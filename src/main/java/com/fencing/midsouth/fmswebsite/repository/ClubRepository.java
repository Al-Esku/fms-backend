package com.fencing.midsouth.fmswebsite.repository;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends CrudRepository<Club, Long> {
    Optional<Club> findClubByShortName(String name);

    Optional<Club> findClubByUuid(String uuid);

    boolean existsByUuid(String uuid);
}
