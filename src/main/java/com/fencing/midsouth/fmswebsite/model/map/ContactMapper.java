package com.fencing.midsouth.fmswebsite.model.map;

import com.fencing.midsouth.fmswebsite.model.dto.ContactForm;
import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.Contact;

public class ContactMapper {
    public static Contact map(ContactForm contactForm, Club club) {
        return new Contact(contactForm.getName(), contactForm.getDetail(), club);
    }

    public static Contact patch(Contact contact, ContactForm contactForm) {
        if (contactForm.getName() != null && !contactForm.getName().isBlank()) {
            contact.setName(contactForm.getName());
        }
        if (contactForm.getDetail() != null && !contactForm.getDetail().isBlank()) {
            contact.setDetail(contactForm.getDetail());
        }
        return contact;
    }
}
