package com.fencing.midsouth.fmswebsite.repository;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Session;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    List<Session> findSessionByClub(Club club);

    Session findSessionByUuid(String uuid);

    void deleteSessionByUuid(String uuid);

    boolean existsByUuid(String string);
}
