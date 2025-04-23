package io.filipegabriel.telephone_appointment_api.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDTO {

    private String contact_name;

    private String contact_email;

    private String contact_cellPhone;

    private String contact_telephone;

    private byte contact_yn_favorite;

    private byte contact_yn_active;

    private Long contact_user_id;

}
