package io.filipegabriel.telephone_appointment_api.resources;

import io.filipegabriel.telephone_appointment_api.entities.User;
import io.filipegabriel.telephone_appointment_api.resources.dto.UserDTO;
import io.filipegabriel.telephone_appointment_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/v1/api/users")
public class UserResource {

    @Autowired
    private UserService userService;

    //Get

    @GetMapping("/{user_id}")
    public ResponseEntity<User> findUserById(@PathVariable Long user_id){
        User user = userService.findUserById(user_id);
        return ResponseEntity.ok().body(user);
    }

    //Post

    @PostMapping
    public ResponseEntity<Void> insertUser(@RequestBody UserDTO userDTO){
        User user = userService.insertUser(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(user.getUser_id()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
