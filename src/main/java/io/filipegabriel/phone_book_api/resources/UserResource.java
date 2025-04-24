package io.filipegabriel.phone_book_api.resources;

import io.filipegabriel.phone_book_api.entities.User;
import io.filipegabriel.phone_book_api.resources.dto.UserDTO;
import io.filipegabriel.phone_book_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/api/users")
public class UserResource {

    @Autowired
    private UserService userService;

    //Get

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable Long userId){
        User user = userService.findUserById(userId);
        return ResponseEntity.ok().body(user);
    }

    //Put

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(@PathVariable Long userId, @RequestBody UserDTO newUser){
        User user = userService.updateUser(userId, newUser);
        return ResponseEntity.ok().body(user);
    }

}
