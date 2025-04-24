package io.filipegabriel.phone_book_api.resources.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private String userEmail;

    private String userPassword;

    private String userNewPassword;

}
