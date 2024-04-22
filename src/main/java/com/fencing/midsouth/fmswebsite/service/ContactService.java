package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Contact;
import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.repository.ContactRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);


    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContactsByClub(Club club) {
        return contactRepository.findContactsByClub(club);
    }

    @Transactional
    public void deleteSessionByUuid(String uuid) {
        contactRepository.deleteContactByUuid(uuid);
    }

    public void addContact(Contact contact) {
        logger.info("Saving event to database");
        contactRepository.save(contact);
    }
}
