package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.ClienteEntrenamiento;
import com.eoi.NutriFit.Repositorios.ClienteEntrenamientoRepo;
import org.springframework.stereotype.Service;

/**
 * ClienteEntrenamientoServi es un servicio que gestiona las operaciones CRUD para la entidad {@link ClienteEntrenamiento}.
 * Hereda de {@link AbstractBusinessService} para utilizar la funcionalidad genérica de gestión
 * de entidades JPA. Esta clase está anotada con {@link Service}, lo que permite que sea detectada
 * y gestionada automáticamente por el contenedor de Spring, facilitando su inyección en otros
 * componentes de la aplicación.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class ClienteEntrenamientoServi extends AbstractBusinessService<ClienteEntrenamiento, Integer, ClienteEntrenamientoRepo> {

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link ClienteEntrenamiento}.
     *
     * @param clienteEntrenamientoRepo el repositorio JPA para la entidad {@link ClienteEntrenamiento}.
     */
    protected ClienteEntrenamientoServi(ClienteEntrenamientoRepo clienteEntrenamientoRepo) {
        super(clienteEntrenamientoRepo);
    }
}
