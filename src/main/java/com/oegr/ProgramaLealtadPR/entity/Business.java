package com.oegr.ProgramaLealtadPR.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    @NotEmpty(message = "EL nombre del negocio no puede estar vacío")
    private String name;

    @Column(name = "address", nullable = false)
    @NotEmpty(message = "La dirección no puede estar vacía")
    private String address;

    @Column(name = "telephone", nullable = false)
    @NotEmpty(message = "El telefono no puede estar vacío")
    @Size(min = 10, max = 10, message = "La telefono debe registrarse con 10 digitos")
    private String telephone;

    @Column(name = "picture",length = 1000)
    @URL(message = "Debe proporcionar una URL válida")
    private String picture;

    @Column(name = "facebook")
    @URL(message = "Debe proporcionar una URL válida")
    private String facebook;

    @Column(name = "instagram")
    @URL(message = "Debe proporcionar una URL válida")
    private String instagram;

    @Column(name = "whatsapp")
    @URL(message = "Debe proporcionar una URL válida")
    private String whatsapp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
