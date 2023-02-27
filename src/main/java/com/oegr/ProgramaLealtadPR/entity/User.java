package com.oegr.ProgramaLealtadPR.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    @NotEmpty(message = "EL nombre no puede estar vacío")
    private String name;

    @Column(name = "paternal_surname", nullable = false)
    @NotEmpty(message = "El apellido paterno no puede estar vacío")
    private String paternalSurname;

    @Column(name = "maternal_surname", nullable = false)
    @NotEmpty(message = "El apellido materno no puede estar vacío")
    private String maternalSurname;

    @Column(name = "birthday", nullable = false)
    @NotNull(message = "La fecha de cumpleaños no puede estar vacío")
    @Past(message = "La fecha no puede ser furtura")
    private Date birthday;

    @Column(name = "email", nullable = false)
    @Email(message = "Ingresa un email valido")
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "creationDate")
    @CreationTimestamp
    private Date creationDate;

    @Column(name = "updateDate")
    @UpdateTimestamp
    private Date updateDate;

    @OneToMany
    @JoinColumn(name = "transaction_id")
    Set<Transaction> transactions;

}
