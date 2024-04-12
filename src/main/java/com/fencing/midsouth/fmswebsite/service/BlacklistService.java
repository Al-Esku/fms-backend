package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Blacklist;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import com.fencing.midsouth.fmswebsite.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public boolean isBlacklisted(User user, String token, Date date) {
        Optional<Blacklist> blacklist = blacklistRepository.findBlacklistByUser(user);
        return blacklist.filter(value -> (date.before(value.getTimestamp()))).isPresent();
    }

    public void addBlacklist(User user) {
        Blacklist blacklist = new Blacklist(user, new Date());
        blacklistRepository.save(blacklist);
    }

    public void removeBlacklist(User user) {
        blacklistRepository.findBlacklistByUser(user).ifPresent(blacklistRepository::delete);
    }
}
