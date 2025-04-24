package io.filipegabriel.phone_book_api.resources;

import io.filipegabriel.phone_book_api.entities.Contact;
import io.filipegabriel.phone_book_api.resources.dto.ContactDTO;
import io.filipegabriel.phone_book_api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/api/contacts")
public class ContactResource {

    @Autowired
    private ContactService contactService;

    //Get

    @GetMapping("/{contactId}")
    public ResponseEntity<Contact> findContactById(@PathVariable Long contactId){
        Contact contact = contactService.findContactById(contactId);
        return ResponseEntity.ok().body(contact);
    }

    @GetMapping("/find-all/{userId}")
    public ResponseEntity<List<Contact>> findAllContacts(@PathVariable Long userId){
        List<Contact> user_contacts = contactService.findAllContacts(userId);
        return ResponseEntity.ok().body(user_contacts);
    }

    //Post

    @PostMapping
    public ResponseEntity<Contact> insertContact(@RequestBody ContactDTO contactDTO){
        Contact contact = contactService.insertContact(contactDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(contact.getContactId()).toUri();
        return ResponseEntity.created(uri).body(contact);
    }

    //Put

    @PutMapping("/{contactId}")
    public ResponseEntity<Contact> update(@PathVariable Long contactId, @RequestBody ContactDTO newContact){
        Contact contact = contactService.updateContact(contactId, newContact);
        return ResponseEntity.ok().body(contact);
    }

    //Delete

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> delete(@PathVariable Long contactId){
        contactService.delete(contactId);
        return ResponseEntity.noContent().build();
    }

}
