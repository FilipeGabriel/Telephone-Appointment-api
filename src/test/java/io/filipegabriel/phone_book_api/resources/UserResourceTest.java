package io.filipegabriel.phone_book_api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.filipegabriel.phone_book_api.config.security.TokenService;
import io.filipegabriel.phone_book_api.entities.User;
import io.filipegabriel.phone_book_api.repositories.UserRepository;
import io.filipegabriel.phone_book_api.resources.dto.UserDTO;
import io.filipegabriel.phone_book_api.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserResource.class)
class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Deve retornar um usuário pelo ID com sucesso")
    @WithMockUser
    void shouldReturnUserByIdSuccessfully() throws Exception {
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setUserEmail("mockuser@example.com");
        mockUser.setUserPassword("encryptedPassword");

        Mockito.when(userService.findUserById(1L)).thenReturn(mockUser);

        mockMvc.perform(get("/v1/api/users/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.userEmail").value("mockuser@example.com"));
    }

    @Test
    @DisplayName("Deve atualizar o usuário com sucesso")
    @WithMockUser
    void shouldUpdateUserSuccessfully() throws Exception {
        UserDTO updateUserDTO = new UserDTO();
        updateUserDTO.setUserEmail("updated@example.com");
        updateUserDTO.setUserPassword("oldPassword");
        updateUserDTO.setUserNewPassword("newPassword");

        User updatedUser = new User();
        updatedUser.setUserId(1L);
        updatedUser.setUserEmail("updated@example.com");
        updatedUser.setUserPassword("encryptedNewPassword");

        Mockito.when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/v1/api/users/{userId}", 1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.userEmail").value("updated@example.com"));
    }
}