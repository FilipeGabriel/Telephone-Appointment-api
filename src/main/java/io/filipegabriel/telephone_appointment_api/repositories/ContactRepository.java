package io.filipegabriel.telephone_appointment_api.repositories;

import io.filipegabriel.telephone_appointment_api.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
