package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.model.entity.Session;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import com.fencing.midsouth.fmswebsite.repository.SessionRepository;
import com.fencing.midsouth.fmswebsite.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);


    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public List<Session> getSessionByClub(Club club) {
        return sessionRepository.findSessionByClub(club);
    }

    @Transactional
    public void deleteSessionByUuid(String uuid) {
        logger.info("Deleting session from database");
        sessionRepository.deleteSessionByUuid(uuid);
    }

    public void addSession(Session session) {
        logger.info("Saving session to database");
        sessionRepository.save(session);
    }
}
