package com.eoi.NutriFit.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

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
    private String detalles_ejercicios;

    @Lob
    private byte [] recursos_multimedia;

    @Column(name = "planificacion_frecuencia")
    private String planificacion_frecuencia;

    // Usuario cliente es entrenado por Usuario entrenador
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idclienteentrenado",foreignKey=@ForeignKey(name = "Fk_cliente_entrenamiento_cliente"))
    private Cliente clienteentrenado;

    //Usuario entrenador entrena a cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "identrenadorcliente",foreignKey=@ForeignKey(name = "Fk_cliente_entrenamiento_entrenador"))
    private Cliente entrenadorcliente;

}