package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Repositorios.DetalleUsuarioRepo;
import org.springframework.stereotype.Service;

/**
 * DetalleUsuarioService es un servicio que gestiona las operaciones CRUD para la entidad {@link DetalleUsuario}.
 * Esta clase extiende {@link AbstractBusinessService} para utilizar la funcionalidad genérica de gestión
 * de entidades JPA, ofreciendo operaciones básicas como crear, leer, actualizar y eliminar.
 *
 * La clase está anotada con {@link Service}, lo que permite que sea detectada automáticamente y gestionada
 * por el contenedor de Spring, facilitando su inyección en otros componentes de la aplicación y asegurando
 * que se gestione de manera adecuada dentro del contexto de Spring.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class DetalleUsuarioService extends AbstractBusinessService<DetalleUsuario, Integer, DetalleUsuarioRepo> {

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link DetalleUsuario}.
     *
     * @param detalleUsuarioRepo el repositorio JPA para la entidad {@link DetalleUsuario}.
     */
    protected DetalleUsuarioService(DetalleUsuarioRepo detalleUsuarioRepo) {
        super(detalleUsuarioRepo);
    }
}
