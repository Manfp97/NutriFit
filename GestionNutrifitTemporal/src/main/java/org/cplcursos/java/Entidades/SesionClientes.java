package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@ToString
@Table(name="ClaseClientes")
public class SesionClientes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idsesioncolectivasesionclientes",foreignKey=@ForeignKey(name = "Fk_colectiva_cliente"))
    private SesionColectiva idsesioncolectiva;

    @ManyToOne
    @Column(name = "idcliente")
    private Cliente idcliente;

}