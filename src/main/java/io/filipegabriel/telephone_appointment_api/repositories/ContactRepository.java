package io.filipegabriel.telephone_appointment_api.repositories;

import io.filipegabriel.telephone_appointment_api.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT COUNT(c) > 0 FROM Contact c WHERE c.contactCellPhone = :cellPhone AND c.contactUser.userId = :userId")
    boolean existsByContactCellPhoneAndUserId(@Param("cellPhone") String cellPhone, @Param("userId") Long userId);

    @Query("SELECT c FROM Contact c WHERE c.contactUser.userId = :userId")
    List<Contact> findAllByContactUserId(@Param("userId") Long userId);

}
