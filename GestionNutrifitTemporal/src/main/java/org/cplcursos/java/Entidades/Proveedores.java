package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="proveedores")
public class Proveedores {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idproveedores", nullable = false)
    private Integer idproveedores;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "idproductoproveedor",foreignKey=@ForeignKey(name = "Fk_proveedor_producto"))
private Producto productoproveedor;
}