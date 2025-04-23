package io.filipegabriel.telephone_appointment_api.service;

import io.filipegabriel.telephone_appointment_api.entities.Contact;
import io.filipegabriel.telephone_appointment_api.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    //Get

    public List<Contact> getAllContacts(Long user_id) {
        return contactRepository.findAllByUserId(user_id);
    }

}
