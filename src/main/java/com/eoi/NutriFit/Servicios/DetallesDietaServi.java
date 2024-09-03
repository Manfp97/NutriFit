package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.DetallesDieta;
import com.eoi.NutriFit.Repositorios.DetallesDietaRepo;
import org.springframework.stereotype.Service;

/**
 * DetallesDietaServi es un servicio que gestiona las operaciones CRUD para la entidad {@link DetallesDieta}.
 * Hereda de {@link AbstractBusinessService} para aprovechar la funcionalidad genérica de gestión
 * de entidades JPA. Esta clase está anotada con {@link Service}, lo que permite su detección y gestión
 * automática por el contenedor de Spring, facilitando su inyección en otros componentes de la aplicación.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class DetallesDietaServi extends AbstractBusinessService<DetallesDieta, Integer, DetallesDietaRepo> {

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link DetallesDieta}.
     *
     * @param detallesDietaRepo el repositorio JPA para la entidad {@link DetallesDieta}.
     */
    protected DetallesDietaServi(DetallesDietaRepo detallesDietaRepo) {
        super(detallesDietaRepo);
    }
}
