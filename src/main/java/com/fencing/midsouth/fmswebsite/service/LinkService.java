package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Link;
import com.fencing.midsouth.fmswebsite.repository.LinkRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getLinksByClub(Club club) {
        Sort.Order sort = new Sort.Order(Sort.Direction.ASC, "name");
        return linkRepository.findLinksByClub(club, Sort.by(sort));
    }

    @Transactional
    public void deleteLinkByUuid(String uuid) {
        linkRepository.deleteLinkByUuid(uuid);
    }
}
