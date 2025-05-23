package io.filipegabriel.phone_book_api.service;

import io.filipegabriel.phone_book_api.entities.Contact;
import io.filipegabriel.phone_book_api.entities.User;
import io.filipegabriel.phone_book_api.repositories.ContactRepository;
import io.filipegabriel.phone_book_api.repositories.UserRepository;
import io.filipegabriel.phone_book_api.resources.dto.ContactDTO;
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
        return contactRepository.findAllByContactUserId(user_id);
    }

    //Post

    public Contact insertContact(ContactDTO contactDTO){
        Contact contact = new Contact();
        User user = userRepository.findById(contactDTO.getContactUserId()).orElseThrow(NoSuchElementException::new);



        contact.setContactName(contactDTO.getContactName());
        if (StringUtils.hasText(contactDTO.getContactEmail())) {
            contact.setContactEmail(contactDTO.getContactEmail());
        }
        contact.setContactCellPhone(contactDTO.getContactCellPhone());
        if (cellPhoneExists(contactDTO.getContactCellPhone(), user.getUserId())) {
            throw new IllegalArgumentException("O número de celular " + contactDTO.getContactCellPhone() + " já está cadastrado.");
        }
        if (StringUtils.hasText(contactDTO.getContactTelephone())) {
            contact.setContactTelephone(contactDTO.getContactTelephone());
        }
        contact.setContactYNFavorite(contactDTO.getContactYNFavorite());
        contact.setContactYNActive(contactDTO.getContactYNActive());
        contact.setContactDTRegistration(LocalDateTime.now());
        contact.setContactUser(user);

        contactRepository.save(contact);

        user.getUserContacts().add(contact);

        userRepository.save(user);

        return contact;
    }

    public boolean cellPhoneExists(String cellPhone, Long user_id) {
        return contactRepository.existsByContactCellPhoneAndUserId(cellPhone, user_id);
    }

    //Put

    public Contact updateContact(Long contactId, ContactDTO newContact){
        Contact oldContact = contactRepository.findById(contactId).orElseThrow(NoSuchElementException::new);
        updateContact(oldContact, newContact);

        contactRepository.save(oldContact);
        return oldContact;
    }

    public void updateContact(Contact oldContact, ContactDTO newContact){
        oldContact.setContactName(newContact.getContactName());
        oldContact.setContactEmail(newContact.getContactEmail());
        if(!oldContact.getContactCellPhone().equals(newContact.getContactCellPhone())){
            if (cellPhoneExists(newContact.getContactCellPhone(), oldContact.getContactUser().getUserId())) {
                throw new IllegalArgumentException("O número de celular " + newContact.getContactCellPhone() + " já está cadastrado.");
            }
        }
        oldContact.setContactCellPhone(newContact.getContactCellPhone());
        oldContact.setContactTelephone(newContact.getContactTelephone());
        oldContact.setContactYNFavorite(newContact.getContactYNFavorite());
        oldContact.setContactYNActive(newContact.getContactYNActive());
    }

    //Delete

    public void delete(Long id){
        contactRepository.deleteById(id);
    }

}
