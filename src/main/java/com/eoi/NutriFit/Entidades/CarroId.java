package com.eoi.NutriFit.Entidades;

/*
    Creamos una clase para representar la clave primaria de la tabla intermedia entre Cliente y Producto que contiene
    el carro.
    (ver https://vladmihalcea.com/the-best-way-to-map-a-composite-primary-key-with-jpa-and-hibernate/)

    Debe heredar de Serializable y ser etiquetada con @Embeddable
 */
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CarroId implements Serializable {

    @Column(name = "idCliente")
    private Long clienteId;

    @Column(name = "idProduct")
    private Long productoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CarroId that = (CarroId) o;
        return Objects.equals(clienteId, that.clienteId) &&
                Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clienteId, productoId);
    }
}

