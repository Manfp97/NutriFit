package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Cliente;
import com.eoi.NutriFit.Repositorios.ClienteRepo;
import org.springframework.stereotype.Service;

/**
 * ClienteServi es un servicio que gestiona las operaciones CRUD para la entidad {@link Cliente}.
 * Extiende de {@link AbstractBusinessService} para utilizar la funcionalidad genérica de gestión
 * de entidades JPA. Esta clase está anotada con {@link Service}, lo que permite su detección
 * automática y gestión por el contenedor de Spring, facilitando su inyección en otros componentes
 * de la aplicación.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class ClienteServi extends AbstractBusinessService<Cliente, Integer, ClienteRepo> {

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link Cliente}.
     *
     * @param clienteRepo el repositorio JPA para la entidad {@link Cliente}.
     */
    protected ClienteServi(ClienteRepo clienteRepo) {
        super(clienteRepo);
    }
}
