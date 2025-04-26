package io.filipegabriel.phone_book_api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.filipegabriel.phone_book_api.config.security.TokenService;
import io.filipegabriel.phone_book_api.entities.Contact;
import io.filipegabriel.phone_book_api.repositories.UserRepository;
import io.filipegabriel.phone_book_api.resources.dto.ContactDTO;
import io.filipegabriel.phone_book_api.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactResource.class)
class ContactResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private ContactService contactService;

    @MockBean
    private UserRepository userRepository;

    private Contact mockContact;
    private ContactDTO mockContactDTO;

    @BeforeEach
    void setUp() {
        mockContact = new Contact();
        mockContact.setContactId(1L);
        mockContact.setContactName("John Doe");
        mockContact.setContactEmail("john.doe@example.com");
        mockContact.setContactCellPhone("12345678901");
        mockContact.setContactTelephone("1234567890");
        mockContact.setContactYNFavorite((byte) 1);
        mockContact.setContactYNActive((byte) 1);
        mockContact.setContactDTRegistration(LocalDateTime.now());

        mockContactDTO = new ContactDTO();
        mockContactDTO.setContactName("John Doe");
        mockContactDTO.setContactEmail("john.doe@example.com");
        mockContactDTO.setContactCellPhone("12345678901");
        mockContactDTO.setContactTelephone("1234567890");
        mockContactDTO.setContactYNFavorite((byte) 1);
        mockContactDTO.setContactYNActive((byte) 1);
        mockContactDTO.setContactUserId(10L);
    }

    @Test
    @DisplayName("Deve retornar um contato por ID")
    @WithMockUser
    void shouldFindContactById() throws Exception {
        when(contactService.findContactById(1L)).thenReturn(mockContact);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/contacts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactId").value(1L))
                .andExpect(jsonPath("$.contactName").value("John Doe"))
                .andExpect(jsonPath("$.contactEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$.contactCellPhone").value("12345678901"))
                .andExpect(jsonPath("$.contactTelephone").value("1234567890"))
                .andExpect(jsonPath("$.contactYNFavorite").value(1))
                .andExpect(jsonPath("$.contactYNActive").value(1));
    }

    @Test
    @DisplayName("Deve retornar todos os contatos de um usuário")
    @WithMockUser
    void shouldFindAllContacts() throws Exception {
        List<Contact> contacts = Arrays.asList(mockContact);
        when(contactService.findAllContacts(10L)).thenReturn(contacts);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/contacts/find-all/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contactId").value(1L))
                .andExpect(jsonPath("$[0].contactName").value("John Doe"))
                .andExpect(jsonPath("$[0].contactEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].contactCellPhone").value("12345678901"))
                .andExpect(jsonPath("$[0].contactTelephone").value("1234567890"))
                .andExpect(jsonPath("$[0].contactYNFavorite").value(1))
                .andExpect(jsonPath("$[0].contactYNActive").value(1));
    }

    @Test
    @DisplayName("Deve inserir um novo contato")
    @WithMockUser
    void shouldInsertContact() throws Exception {
        when(contactService.insertContact(any(ContactDTO.class))).thenReturn(mockContact);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockContactDTO))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contactId").value(1L))
                .andExpect(jsonPath("$.contactName").value("John Doe"))
                .andExpect(jsonPath("$.contactEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$.contactCellPhone").value("12345678901"))
                .andExpect(jsonPath("$.contactTelephone").value("1234567890"))
                .andExpect(jsonPath("$.contactYNFavorite").value(1))
                .andExpect(jsonPath("$.contactYNActive").value(1));
    }

    @Test
    @DisplayName("Deve atualizar um contato existente")
    @WithMockUser
    void shouldUpdateContact() throws Exception {
        when(contactService.updateContact(any(Long.class), any(ContactDTO.class))).thenReturn(mockContact);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/contacts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockContactDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactId").value(1L))
                .andExpect(jsonPath("$.contactName").value("John Doe"))
                .andExpect(jsonPath("$.contactEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$.contactCellPhone").value("12345678901"))
                .andExpect(jsonPath("$.contactTelephone").value("1234567890"))
                .andExpect(jsonPath("$.contactYNFavorite").value(1))
                .andExpect(jsonPath("$.contactYNActive").value(1));
    }

    @Test
    @DisplayName("Deve deletar um contato por ID")
    @WithMockUser
    void shouldDeleteContact() throws Exception {
        doNothing().when(contactService).delete(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/contacts/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}