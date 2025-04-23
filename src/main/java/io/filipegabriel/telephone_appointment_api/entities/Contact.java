package io.filipegabriel.telephone_appointment_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long contactId;

    @Column(nullable = false, length = 100)
    private String contactName;

    @Column(length = 255)
    private String contactEmail;

    @Column(nullable = false, length = 11)
    private String contactCellPhone;

    @Column(length = 10)
    private String contactTelephone;

    private byte contactYNFavorite;

    private byte contactYNActive;

    private LocalDateTime contactDTRegistration;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contactUserId")
    private User contactUser;

}
