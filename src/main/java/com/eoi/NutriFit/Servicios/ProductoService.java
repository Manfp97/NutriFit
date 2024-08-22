package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Repositorios.ProductoRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 * ProductoService es un servicio que gestiona las operaciones CRUD para la entidad {@link Producto}.
 * Extiende de {@link AbstractBusinessService} para proporcionar una implementación genérica de
 * las operaciones de gestión de entidades JPA, como crear, leer, actualizar y eliminar.
 *
 * Esta clase está anotada con {@link Service}, lo que permite su detección automática y su gestión
 * por el contenedor de Spring, facilitando su inyección en otros componentes de la aplicación y
 * asegurando su adecuada administración dentro del contexto de Spring.
 *
 * Además, se proporciona una implementación personalizada para la eliminación de entidades,
 * que utiliza el método {@link ProductoRepo#delete(Object)} en lugar del método genérico
 * {@link AbstractBusinessService#eliminarPorId(Object)}.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
@Service
public class ProductoService extends AbstractBusinessService<Producto, Integer, ProductoRepo> {
    private final ProductoRepo productoRepo;

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico de {@link Producto}.
     *
     * @param productoRepo el repositorio JPA para la entidad {@link Producto}.
     */
    protected ProductoService(ProductoRepo productoRepo) {
        super(productoRepo);
        this.productoRepo = productoRepo;
    }

    /**
     * Elimina una entidad {@link Producto} por su identificador. Primero verifica si la entidad existe
     * en el repositorio. Si existe, la elimina utilizando el método {@link ProductoRepo#delete(Object)}.
     * Si no existe, lanza una {@link EntityNotFoundException} con un mensaje indicando que la entidad
     * no se encontró.
     *
     * @param id el identificador de la entidad {@link Producto} a eliminar.
     * @throws EntityNotFoundException si no se encuentra una entidad con el identificador proporcionado.
     */
    @Override
    public void eliminarPorId(Integer id) {
        if (getRepo().existsById(id)) {
            //getRepo().deleteById(id);
            productoRepo.delete(getRepo().getById(id));
        } else {
            throw new EntityNotFoundException("Entidad con id " + id + " no encontrada.");
        }
    }
}
