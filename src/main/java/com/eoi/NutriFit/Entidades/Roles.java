package com.eoi.NutriFit.Entidades;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre_rol", nullable = false, length = 45)
    private String nombreRol;

}