package com.oegr.ProgramaLealtadPR.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="description", nullable = false)
    @NotEmpty(message = "La description no puede estar vacía")
    private String description;

    @Column(name = "picture",length = 1000, nullable = false)
    @URL(message = "Ingresa una url de imagen correcta")
    private String picture;

    @Column(name = "cost", nullable = false)
    @NotNull(message = "El costo en puntos no pueden estar vacio")
    @Positive(message = "El costo en puntos debe ser una cantidad positiva mayor que cero ")
    private int cost;

    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "id")
    private Business business;
}
