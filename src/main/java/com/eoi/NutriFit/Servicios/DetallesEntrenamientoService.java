package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.DetallesEntrenamiento;
import com.eoi.NutriFit.Repositorios.DetallesEntrenamientoRepo;
import org.springframework.stereotype.Service;

/**
 * DetallesEntrenamientoService es un servicio que gestiona las operaciones CRUD para la entidad {@link DetallesEntrenamiento}.
 * Extiende de {@link AbstractBusinessService} para aprovechar la funcionalidad genérica de gestión
 * de entidades JPA. Esta clase está anotada con {@link Service}, lo que permite su detección automática
 * y gestión por el contenedor de Spring, facilitando su inyección en otros componentes de la aplicación.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class DetallesEntrenamientoService extends AbstractBusinessService<DetallesEntrenamiento, Integer, DetallesEntrenamientoRepo> {

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link DetallesEntrenamiento}.
     *
     * @param detallesEntrenamientoRepo el repositorio JPA para la entidad {@link DetallesEntrenamiento}.
     */
    protected DetallesEntrenamientoService(DetallesEntrenamientoRepo detallesEntrenamientoRepo) {
        super(detallesEntrenamientoRepo);
    }
}
