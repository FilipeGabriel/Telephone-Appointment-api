package io.filipegabriel.telephone_appointment_api.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private String userEmail;

    private String userPassword;

    private String userNewPassword;

}
