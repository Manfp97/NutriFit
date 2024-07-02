package com.eoi.NutriFit.Servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public abstract class AbstractBusinessService<E, ID, DTO, REPO extends JpaRepository<E, ID>,
        MAPPER extends AbstractBusinessServiceMapper<E, DTO>> {

    private final REPO repo;
    private final MAPPER serviceMapper;

    /**
     * Constructor que inicializa el repositorio y el mapeador del servicio.
     *
     * @param repo         El repositorio para operaciones de base de datos.
     * @param serviceMapper El mapeador para convertir entre entidad y DTO.
     */
    protected AbstractBusinessService(REPO repo, MAPPER serviceMapper) {
        this.repo = repo;
        this.serviceMapper = serviceMapper;
    }

    /**
     * Busca una entidad por su ID.
     *
     * @param id El ID de la entidad a buscar.
     * @return Un Optional que contiene la entidad encontrada, o vacío si no se encuentra.
     */
    public Optional<E> buscarPorId(ID id) {
        return this.repo.findById(id);
    }

    /**
     * Guarda una entidad y devuelve la entidad guardada.
     *
     * @param entidad La entidad a guardar.
     * @return La entidad guardada.
     */
    public E guardar(E entidad) {
        return this.repo.save(entidad);
    }

    /**
     * Guarda un DTO y devuelve el DTO guardado.
     *
     * @param dto El DTO a guardar.
     * @return El DTO guardado.
     */
    public DTO guardarDTO(DTO dto) {

    }

    /**
     * Elimina una entidad por su ID.
     *
     * @param id El ID de la entidad a eliminar.
     */
    public void eliminarPorId(ID id) {
        this.repo.deleteById(id);
    }

    /**
     * Busca todas las entidades.
     *
     * @return Una lista de todas las entidades.
     */
    public List<E> buscarTodos() {
        return this.repo.findAll();
    }

    /**
     * Busca todos los DTOs.
     *
     * @return Una lista de todos los DTOs.
     */
    public List<DTO> buscarTodosDTOs() {

    }

    /**
     * Busca todas las entidades con paginación.
     *
     * @param pageable Información de paginación.
     * @return Una página de entidades.
     */
    public Page<E> buscarTodos(Pageable pageable) {
        return this.repo.findAll(pageable);
    }

    /**
     * Busca todos los DTOs con paginación.
     *
     * @param pageable Información de paginación.
     * @return Una página de DTOs.
     */
    public Page<DTO> buscarTodosDTOs(Pageable pageable) {

    }
}
