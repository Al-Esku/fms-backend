package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Contact;
import com.fencing.midsouth.fmswebsite.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContactsByClub(Club club) {
        return contactRepository.findContactsByClub(club);
    }
}