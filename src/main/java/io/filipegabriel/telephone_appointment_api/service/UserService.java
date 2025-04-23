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
        if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com o e-mail " + userDTO.getUserEmail());
        }

        User user = new User();

        user.setUserEmail(userDTO.getUserEmail());
        user.setUserPassword(userDTO.getUserPassword());
        user.setUserDTRegistration(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }

}
