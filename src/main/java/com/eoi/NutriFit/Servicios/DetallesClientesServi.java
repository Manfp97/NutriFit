package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.DetallesCliente;
import com.eoi.NutriFit.Repositorios.DetallesClientesRepo;
import org.springframework.stereotype.Service;

/**
 * DetallesClientesServi es un servicio que gestiona las operaciones CRUD para la entidad {@link DetallesCliente}.
 * Hereda de {@link AbstractBusinessService} para aprovechar la funcionalidad genérica de gestión
 * de entidades JPA. Esta clase está anotada con {@link Service}, lo que facilita su detección y gestión
 * automática por el contenedor de Spring, permitiendo su inyección en otros componentes de la aplicación.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class DetallesClientesServi extends AbstractBusinessService<DetallesCliente, Integer, DetallesClientesRepo> {

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link DetallesCliente}.
     *
     * @param detallesClientesRepo el repositorio JPA para la entidad {@link DetallesCliente}.
     */
    protected DetallesClientesServi(DetallesClientesRepo detallesClientesRepo) {
        super(detallesClientesRepo);
    }
}
