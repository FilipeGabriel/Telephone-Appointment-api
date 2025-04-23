package io.filipegabriel.telephone_appointment_api.repositories;

import io.filipegabriel.telephone_appointment_api.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT COUNT(c) > 0 FROM Contact c WHERE c.contactCellPhone = :cellPhone AND c.contact_user.user_id = :userId")
    boolean existsByCellPhoneAndUserId(String cellPhone, Long userId);

    @Query("SELECT c FROM Contact c WHERE c.contact_user.user_id = :userId")
    List<Contact> findAllByUserId(Long userId);

}
