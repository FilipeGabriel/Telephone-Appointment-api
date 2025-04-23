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
    private Long contact_id;

    @Column(nullable = false, length = 100)
    private String contact_name;

    @Column(length = 255)
    private String contact_email;

    @Column(nullable = false, length = 11)
    private String contactCellPhone;

    @Column(length = 10)
    private String contact_telephone;

    private byte contact_yn_favorite;

    private byte contact_yn_active;

    private LocalDateTime contact_dt_registration;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contact_user_id")
    private User contact_user;

}
