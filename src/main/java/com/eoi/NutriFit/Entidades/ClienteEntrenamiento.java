package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="clienteentrenamiento")
public class ClienteEntrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "detalles_ejercicios")
    private String detallesEjercicios; // Changed to camelCase for consistency

    @Lob
    private byte [] recursosMultimedia; // Changed to camelCase for consistency

    @Column(name = "planificacion_frecuencia")
    private String planificacionFrecuencia; // Changed to camelCase for consistency

    // Usuario cliente es entrenado por Usuario entrenador
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idclienteentrenado", foreignKey = @ForeignKey(name = "Fk_cliente_entrenamiento_cliente"))
    private Cliente clienteEntrenado; // Renamed to camelCase for consistency

    // Usuario entrenador entrena a cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "identrenadorcliente", foreignKey = @ForeignKey(name = "Fk_cliente_entrenamiento_entrenador"))
    private Cliente entrenadorCliente; // Renamed to camelCase for consistency
}
