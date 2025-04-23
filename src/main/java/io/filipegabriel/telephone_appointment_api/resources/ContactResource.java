package io.filipegabriel.telephone_appointment_api.resources;

import io.filipegabriel.telephone_appointment_api.entities.Contact;
import io.filipegabriel.telephone_appointment_api.resources.dto.ContactDTO;
import io.filipegabriel.telephone_appointment_api.service.ContactService;
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

    @GetMapping("/{contact_id}")
    public ResponseEntity<Contact> findContactById(@PathVariable Long contact_id){
        Contact contact = contactService.findContactById(contact_id);
        return ResponseEntity.ok().body(contact);
    }

    @GetMapping("/find-all/{user_id}")
    public ResponseEntity<List<Contact>> findAllContacts(@PathVariable Long user_id){
        List<Contact> user_contacts = contactService.findAllContacts(user_id);
        return ResponseEntity.ok().body(user_contacts);
    }

    //Post

    @PostMapping
    public ResponseEntity<Contact> insertContact(@RequestBody ContactDTO contactDTO){
        Contact contact = contactService.insertContact(contactDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(contact.getContact_id()).toUri();
        return ResponseEntity.created(uri).body(contact);
    }

}
