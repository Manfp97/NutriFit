package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="clientes")
@Getter
@Setter
@ToString
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente", nullable = false)
    private Integer idcliente;

    @OneToOne(mappedBy ="idusuario")
    private Usuario idusuario;

    @OneToOne(mappedBy = "id_carrito")
    private Carrito carrito;

    @OneToOne(mappedBy = "idcontacto_entrenamiento")
    private Contacto_entrenamiento idcontacto_entrenamiento;
    
}