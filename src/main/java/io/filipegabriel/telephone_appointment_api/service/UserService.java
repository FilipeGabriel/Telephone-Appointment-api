package io.filipegabriel.telephone_appointment_api.service;

import io.filipegabriel.telephone_appointment_api.entities.User;
import io.filipegabriel.telephone_appointment_api.repositories.UserRepository;
import io.filipegabriel.telephone_appointment_api.resources.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    //Post

    public User insertUser(UserDTO userDTO){
        User user = new User();

        user.setUser_email(userDTO.getUser_email());
        user.setUser_password(userDTO.getUser_password());
        user.setUser_dt_registration(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }

}
