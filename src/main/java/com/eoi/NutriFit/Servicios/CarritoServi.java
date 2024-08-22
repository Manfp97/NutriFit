package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Carrito;
import com.eoi.NutriFit.Repositorios.CarritoRepo;
import org.springframework.stereotype.Service;

/**
 * CarritoServi es un servicio que gestiona las operaciones CRUD para la entidad {@link Carrito}.
 * Hereda de {@link AbstractBusinessService} para aprovechar la funcionalidad genérica de gestión
 * de entidades JPA. Esta clase está marcada como un {@link Service} de Spring, lo que la hace
 * candidata para la detección automática de componentes y permite su inyección en otros
 * componentes de la aplicación.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class CarritoServi extends AbstractBusinessService<Carrito, Integer, CarritoRepo> {

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link Carrito}.
     *
     * @param carritoRepo el repositorio JPA para la entidad {@link Carrito}.
     */
    protected CarritoServi(CarritoRepo carritoRepo) {
        super(carritoRepo);
    }
}
