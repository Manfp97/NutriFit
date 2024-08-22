package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.ClienteDieta;
import com.eoi.NutriFit.Repositorios.ClienteDietaRepo;
import org.springframework.stereotype.Service;

/**
 * ClienteDietaServi es un servicio que gestiona las operaciones CRUD para la entidad {@link ClienteDieta}.
 * Hereda de {@link AbstractBusinessService} para aprovechar la funcionalidad genérica de gestión
 * de entidades JPA. Esta clase está marcada como un {@link Service} de Spring, lo que la convierte
 * en un componente gestionado por el contenedor de Spring y facilita su inyección en otros
 * componentes de la aplicación.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class ClienteDietaServi extends AbstractBusinessService<ClienteDieta, Integer, ClienteDietaRepo> {

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link ClienteDieta}.
     *
     * @param clienteDietaRepo el repositorio JPA para la entidad {@link ClienteDieta}.
     */
    protected ClienteDietaServi(ClienteDietaRepo clienteDietaRepo) {
        super(clienteDietaRepo);
    }
}
