package io.filipegabriel.telephone_appointment_api.resources;

import io.filipegabriel.telephone_appointment_api.config.security.TokenService;
import io.filipegabriel.telephone_appointment_api.entities.User;
import io.filipegabriel.telephone_appointment_api.repositories.UserRepository;
import io.filipegabriel.telephone_appointment_api.resources.records.AuthenticationDTO;
import io.filipegabriel.telephone_appointment_api.resources.records.LoginResponseDTO;
import io.filipegabriel.telephone_appointment_api.resources.records.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        User user = (User) userRepository.findByUserEmail(data.userEmail());
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.userEmail(), data.userPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        var userId = user.getUserId();

        return ResponseEntity.ok(new LoginResponseDTO(token, userId));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data){
        if(this.userRepository.findByUserEmail(data.userEmail()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.userPassword());
        User newUser = new User();

        newUser.setUserEmail(data.userEmail());
        newUser.setUserPassword(encryptedPassword);
        newUser.setUserDTRegistration(LocalDateTime.now());

        userRepository.save(newUser);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getUserId()).toUri();
        return ResponseEntity.created(uri).build();

    }

}
