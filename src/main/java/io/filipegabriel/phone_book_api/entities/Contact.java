package io.filipegabriel.phone_book_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "contato", schema = "desafio")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "contato_id")
    private Long contactId;

    @Column(name = "contato_nome", nullable = false, length = 100)
    private String contactName;

    @Column(name = "contato_email", length = 255)
    private String contactEmail;

    @Column(name = "contato_celular", nullable = false, length = 11)
    private String contactCellPhone;

    @Column(name = "contato_telefone", length = 10)
    private String contactTelephone;

    @Column(name = "contato_sn_favorito")
    private byte contactYNFavorite;

    @Column(name = "contato_sn_ativo")
    private byte contactYNActive;

    @Column(name = "contato_dh_cad")
    private LocalDateTime contactDTRegistration;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contactUserId")
    private User contactUser;

}
