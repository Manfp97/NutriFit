package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="sesion_clientes")
public class SesionClientes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sesion_colectiva", foreignKey=@ForeignKey(name = "fk_colectiva_cliente"))
    private SesionColectiva sesionColectiva;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", foreignKey=@ForeignKey(name = "fk_cliente_sesion"))
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empleado_id") // adjust name if needed
    private Cliente empleado;

}
