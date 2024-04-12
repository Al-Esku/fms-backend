package com.fencing.midsouth.fmswebsite.repository;

import com.fencing.midsouth.fmswebsite.model.entity.Blacklist;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlacklistRepository extends CrudRepository<Blacklist, Long> {

    Optional<Blacklist> findBlacklistByUser(User user);
}
