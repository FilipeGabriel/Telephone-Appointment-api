package io.filipegabriel.telephone_appointment_api.service;

import io.filipegabriel.telephone_appointment_api.entities.Contact;
import io.filipegabriel.telephone_appointment_api.entities.User;
import io.filipegabriel.telephone_appointment_api.repositories.ContactRepository;
import io.filipegabriel.telephone_appointment_api.repositories.UserRepository;
import io.filipegabriel.telephone_appointment_api.resources.dto.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    //Get

    public Contact findContactById(Long contact_id){
        Optional<Contact> contact = contactRepository.findById(contact_id);
        return contact.get();
    }

    public List<Contact> findAllContacts(Long user_id) {
        return contactRepository.findAllByUserId(user_id);
    }

    //Post

    public Contact insertContact(ContactDTO contactDTO){
        Contact contact = new Contact();
        User user = userRepository.findById(contactDTO.getContact_user_id()).orElseThrow(NoSuchElementException::new);

        contact.setContact_name(contactDTO.getContact_name());
        if (StringUtils.hasText(contactDTO.getContact_email())) {
            contact.setContact_email(contactDTO.getContact_email());
        }
        contact.setContact_cellPhone(contactDTO.getContact_cellPhone());
        if (StringUtils.hasText(contactDTO.getContact_telephone())) {
            contact.setContact_telephone(contactDTO.getContact_telephone());
        }
        contact.setContact_yn_favorite(contactDTO.getContact_yn_favorite());
        contact.setContact_yn_active(contactDTO.getContact_yn_active());
        contact.setContact_dt_registration(LocalDateTime.now());
        contact.setContact_user(user);

        contactRepository.save(contact);

        user.getUser_contacts().add(contact);

        userRepository.save(user);

        return contact;
    }

}
