package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * La clase <code>SesionColectiva</code> representa una entidad que define una clase colectiva en el sistema.
 * Cada sesión colectiva incluye información sobre el tipo de clase, los horarios de entrada y salida,
 * el límite de reservas y los clientes asociados a la sesión.
 *
 * <p>La entidad está anotada con <code>@Entity</code>, lo que indica que se mapea a una tabla en la base de datos,
 * denominada <code>ClaseColectiva</code>.</p>
 *
 * <p>Se utilizan las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code> y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter y un constructor sin argumentos, respectivamente.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="ClaseColectiva")
public class SesionColectiva {

    /**
     * Identificador único de la sesión colectiva.
     * Este campo es autogenerado y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Tipo de clase que se imparte en la sesión colectiva.
     * Este campo es obligatorio y tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "tipo_clase", nullable = false, length = 45)
    private String tipo_clase;

    /**
     * Hora de inicio de la sesión colectiva.
     * Representado como un <code>LocalDateTime</code>.
     */
    @Column(name = "horario_entrada")
    private LocalDateTime horario_entrada;

    /**
     * Hora de finalización de la sesión colectiva.
     * Representado como un <code>LocalDateTime</code>.
     */
    @Column(name = "horario_salida")
    private LocalDateTime horario_salida;

    /**
     * Límite máximo de reservas permitidas para la sesión colectiva.
     * Representado como un valor de tipo <code>Byte</code>.
     */
    @Column(name = "limite_reservas")
    private Byte limite_reservas;

    /**
     * Conjunto de clientes que participan en la sesión colectiva.
     * Relación One-to-Many con la entidad <code>SesionClientes</code>.
     */
    @OneToMany
    private Set<SesionClientes> sesionClientes;

}
