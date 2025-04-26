package io.filipegabriel.phone_book_api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.filipegabriel.phone_book_api.config.security.TokenService;
import io.filipegabriel.phone_book_api.entities.User;
import io.filipegabriel.phone_book_api.repositories.UserRepository;
import io.filipegabriel.phone_book_api.resources.records.AuthenticationDTO;
import io.filipegabriel.phone_book_api.resources.records.RegisterDTO;
import io.filipegabriel.phone_book_api.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationResource.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve realizar login com sucesso e retornar token")
    void testLogin() throws Exception {
        AuthenticationDTO authRequest = new AuthenticationDTO("userEmail", "userPassword");
        String fakeToken = "fake-jwt-token";

        User mockUser = new User();
        mockUser.setUserEmail(authRequest.userEmail());
        mockUser.setUserPassword(authRequest.userPassword());

        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getPrincipal()).thenReturn(mockUser);
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(auth);

        Mockito.when(userRepository.findByUserEmail(Mockito.anyString()))
                .thenReturn(mockUser);

        Mockito.when(tokenService.generateToken(Mockito.any()))
                .thenReturn(fakeToken);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(fakeToken));
    }

    @Test
    @DisplayName("Deve realizar registro de usuário com sucesso")
    void testRegister() throws Exception {
        RegisterDTO registerRequest = new RegisterDTO("userEmail", "userPassword");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());
    }
}