package io.filipegabriel.phone_book_api.repositories;

import io.filipegabriel.phone_book_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

}
