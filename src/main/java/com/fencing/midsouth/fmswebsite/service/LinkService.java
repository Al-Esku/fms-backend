package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Contact;
import com.fencing.midsouth.fmswebsite.model.entity.Link;
import com.fencing.midsouth.fmswebsite.repository.LinkRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    private static final Logger logger = LoggerFactory.getLogger(LinkService.class);


    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getLinksByClub(Club club) {
        Sort.Order sort = new Sort.Order(Sort.Direction.ASC, "name");
        return linkRepository.findLinksByClub(club, Sort.by(sort));
    }

    public void addLink(Link link) {
        logger.info("Saving link to database");
        linkRepository.save(link);
    }

    public Link getLinkByUuid(String uuid) {
        return linkRepository.findLinkByUuid(uuid);
    }

    public Link updateLink(Link link) throws ObjectNotFoundException {
        if (linkRepository.existsByUuid(link.getUuid())) {
            return linkRepository.save(link);
        }
        throw new ObjectNotFoundException(UUID.fromString(link.getUuid()), link.getName());
    }

    @Transactional
    public void deleteLinkByUuid(String uuid) {
        linkRepository.deleteLinkByUuid(uuid);
    }
}
