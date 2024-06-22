package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Table(name="usuarios")
@Getter
@Setter
//@NoArgsConstructor
@ToString
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombreUsuario", nullable = false, length = 100)
    private String nombreUsuario;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Basic(optional = false)
    private boolean activo = true;

    @OneToOne()
    private Cliente clienteUsuario;

    @OneToOne()
    private Roles roles;





}