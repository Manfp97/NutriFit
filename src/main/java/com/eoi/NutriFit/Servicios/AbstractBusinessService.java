package com.eoi.NutriFit.Servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * AbstractBusinessService es una clase abstracta que proporciona la implementación básica
 * de un servicio de negocio para manejar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre entidades JPA. Esta clase utiliza un repositorio genérico que extiende JpaRepository.
 *
 * @param <E>   el tipo de la entidad.
 * @param <ID>  el tipo del identificador de la entidad.
 * @param <REPO> el tipo del repositorio JPA para la entidad.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 */
public abstract class AbstractBusinessService<E, ID, REPO extends JpaRepository<E,ID>>  {
    private final REPO repo;

    /**
     * Constructor protegido para inicializar el servicio con un repositorio específico.
     *
     * @param repo el repositorio JPA para la entidad.
     */
    protected AbstractBusinessService(REPO repo) {
        this.repo = repo;
    }

    /**
     * Busca y devuelve todas las entidades de la base de datos.
     *
     * @return una lista de todas las entidades.
     */
    public List<E> buscarEntidades(){
        return  this.repo.findAll();
    }

    /**
     * Busca y devuelve todas las entidades de la base de datos como un conjunto (Set).
     *
     * @return un conjunto de todas las entidades.
     */
    public Set<E> buscarEntidadesSet(){
        Set<E> eSet = new HashSet<E>(this.repo.findAll());
        return eSet;
    }

    /**
     * Busca una entidad por su identificador.
     *
     * @param id el identificador de la entidad.
     * @return un Optional que contiene la entidad si se encuentra.
     */
    public Optional<E> encuentraPorIdEntity(ID id){
        return this.repo.findById(id);
    }

    /**
     * Busca una entidad por su identificador.
     *
     * @param id el identificador de la entidad.
     * @return un Optional que contiene la entidad si se encuentra.
     */
    public Optional<E> encuentraPorId(ID id){
        return this.repo.findById(id);
    }

    /**
     * Busca todas las entidades de forma paginada.
     *
     * @param pageable información de paginación.
     * @return una página de entidades.
     */
    public Page<E> buscarTodos(Pageable pageable){
        return repo.findAll(pageable);
    }

    /**
     * Busca y devuelve todas las entidades de la base de datos como un conjunto (Set).
     *
     * @return un conjunto de todas las entidades.
     */
    public Set<E> buscarTodosSet(){
        Set<E> eSet = new HashSet<E>(this.repo.findAll());
        return eSet;
    }

    /**
     * Guarda una entidad en la base de datos por su identificador.
     *
     * @param id el identificador de la entidad.
     */
    public void guardarPorId(ID id){
        this.repo.save(this.repo.getOne(id));
    }

    /**
     * Guarda una entidad en la base de datos.
     *
     * @param entidad la entidad a guardar.
     * @return la entidad guardada.
     * @throws Exception si ocurre un error durante el guardado.
     */
    public E guardar(E entidad) throws Exception {
        E entidadGuardada = repo.save(entidad);
        return entidadGuardada;
    }

    /**
     * Guarda una lista de entidades en la base de datos.
     *
     * @param ents la lista de entidades a guardar.
     * @throws Exception si ocurre un error durante el guardado.
     */
    public void guardar(List<E> ents) throws Exception {
        Iterator<E> it = ents.iterator();
        while(it.hasNext()){
            E e = it.next();
            repo.save(e);
        }
    }

    /**
     * Elimina una entidad de la base de datos por su identificador.
     *
     * @param id el identificador de la entidad a eliminar.
     * @throws EntityNotFoundException si no se encuentra una entidad con el identificador dado.
     */
    public void eliminarPorId(ID id) {
        if (this.repo.existsById(id)) {
            this.repo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Entidad con id " + id + " no encontrada.");
        }
    }

    /**
     * Obtiene el repositorio asociado al servicio.
     *
     * @return el repositorio de la entidad.
     */
    public REPO getRepo() {
        return repo;
    }
}
