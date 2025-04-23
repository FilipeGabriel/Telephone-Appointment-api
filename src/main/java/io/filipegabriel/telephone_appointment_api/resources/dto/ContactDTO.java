package io.filipegabriel.telephone_appointment_api.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDTO {

    private String contactName;

    private String contactEmail;

    private String contactCellPhone;

    private String contactTelephone;

    private byte contactYNFavorite;

    private byte contactYNActive;

    private Long contactUserId;

}
