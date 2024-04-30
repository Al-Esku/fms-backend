package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Contact;
import com.fencing.midsouth.fmswebsite.model.entity.Event;
import com.fencing.midsouth.fmswebsite.model.entity.Session;
import com.fencing.midsouth.fmswebsite.repository.ContactRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        logger.info("Saving contact to database");
        contactRepository.save(contact);
    }

    public Contact getContactByUuid(String uuid) {
        logger.info("Finding session %s from database".formatted(uuid));
        return contactRepository.findContactByUuid(uuid);
    }

    public Contact updateContact(Contact contact) throws ObjectNotFoundException {
        if (contactRepository.existsByUuid(contact.getUuid())) {
            return contactRepository.save(contact);
        }
        throw new ObjectNotFoundException(UUID.fromString(contact.getUuid()), contact.getName());
    }
}
