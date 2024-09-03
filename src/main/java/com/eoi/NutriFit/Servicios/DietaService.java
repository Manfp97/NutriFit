package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Dieta;
import com.eoi.NutriFit.Repositorios.DietaRepo;
import org.springframework.stereotype.Service;

/**
 * DietaService es un servicio que gestiona las operaciones CRUD para la entidad {@link Dieta}.
 * Esta clase extiende {@link AbstractBusinessService} para ofrecer una implementación genérica
 * de las operaciones de gestión de entidades JPA, como crear, leer, actualizar y eliminar.
 *
 * La clase está anotada con {@link Service}, lo que permite su detección automática y su gestión
 * por el contenedor de Spring. Esto facilita la inyección del servicio en otros componentes de la
 * aplicación y asegura que el servicio se maneje correctamente dentro del contexto de Spring.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class DietaService extends AbstractBusinessService<Dieta, Integer, DietaRepo> {

    /**
     * Constructor protegido que inicializa el servicio con un repositorio específico de {@link Dieta}.
     *
     * @param dietaRepo el repositorio JPA para la entidad {@link Dieta}.
     */
    protected DietaService(DietaRepo dietaRepo) {
        super(dietaRepo);
    }
}
