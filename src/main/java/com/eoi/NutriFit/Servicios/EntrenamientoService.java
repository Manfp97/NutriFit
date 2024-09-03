package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import com.eoi.NutriFit.Repositorios.EntrenamientoRepo;
import org.springframework.stereotype.Service;

/**
 * EntrenamientoService es un servicio que gestiona las operaciones CRUD para la entidad {@link Entrenamiento}.
 * Esta clase extiende {@link AbstractBusinessService} para proporcionar una implementación genérica
 * de las operaciones de gestión de entidades JPA, incluyendo operaciones básicas como crear, leer,
 * actualizar y eliminar registros.
 *
 * La clase está anotada con {@link Service}, lo que permite que sea detectada automáticamente
 * y gestionada por el contenedor de Spring, facilitando su inyección en otros componentes de la
 * aplicación y garantizando que se administre de manera adecuada en el contexto de Spring.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class EntrenamientoService extends AbstractBusinessService<Entrenamiento, Integer, EntrenamientoRepo> {

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link Entrenamiento}.
     *
     * @param entrenamientoRepo el repositorio JPA para la entidad {@link Entrenamiento}.
     */
    protected EntrenamientoService(EntrenamientoRepo entrenamientoRepo) {
        super(entrenamientoRepo);
    }
}
