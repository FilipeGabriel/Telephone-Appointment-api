package io.filipegabriel.telephone_appointment_api.repositories;

import io.filipegabriel.telephone_appointment_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserEmail(String userEmail);

}
