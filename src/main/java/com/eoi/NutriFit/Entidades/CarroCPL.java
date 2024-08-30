package com.eoi.NutriFit.Entidades;

/*
    Esta clase representa la tabla intermedia que contiene el carro de la compra.
    Debe declararse de forma explícita porque tiene que contener, entre otros, las unidades de cada producto. Además,
    no va a permitir que un cliente ponga en el carro el mismo producto dos veces (se creará un error de BBDD)

    Eso debe ser controlado desde el código (controlador y/o JS)
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="carrocpl")
@NoArgsConstructor
@AllArgsConstructor
public class CarroCPL {
    @EmbeddedId
    private CarroId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clienteId")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productoId")
    private Producto producto;

    private Long cantidad;

    @Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
    private LocalDateTime creadoEl = LocalDateTime.now();;
    private LocalDateTime actualizadoEl = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CarroCPL that = (CarroCPL) o;
        return Objects.equals(cliente, that.cliente) &&
                Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, producto);
    }
}
