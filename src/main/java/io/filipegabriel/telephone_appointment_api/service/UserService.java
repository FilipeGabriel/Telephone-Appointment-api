package io.filipegabriel.telephone_appointment_api.service;

import io.filipegabriel.telephone_appointment_api.entities.User;
import io.filipegabriel.telephone_appointment_api.repositories.UserRepository;
import io.filipegabriel.telephone_appointment_api.resources.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Get

    public User findUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    //Put

    public User updateUser (Long userId, UserDTO newUser) {
        User oldUser = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        updateUser(oldUser, newUser);

        userRepository.save(oldUser);
        return oldUser;
    }

    public void updateUser(User oldUser, UserDTO newUser){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(newUser.getUserPassword(), oldUser.getUserPassword())) {
            oldUser.setUserPassword(passwordEncoder.encode(newUser.getUserNewPassword()));
        } else {
            throw new IllegalArgumentException("Senha atual incorreta! Tente novamente.");
        }
    }

}
