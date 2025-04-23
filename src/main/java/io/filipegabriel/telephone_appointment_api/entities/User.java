package io.filipegabriel.telephone_appointment_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long user_id;

    @Column(unique = true, nullable = false)
    private String user_email;

    @Column(nullable = false)
    private String user_password;

    private LocalDateTime user_dt_registration;

    @OneToMany(mappedBy = "contact_user")
    private List<Contact> user_contacts = new ArrayList<>();

}
