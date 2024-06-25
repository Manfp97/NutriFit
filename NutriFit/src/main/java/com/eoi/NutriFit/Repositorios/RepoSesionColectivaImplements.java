package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.SesionColectiva;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RepoSesionColectivaImplements implements RepoInterfaz<SesionColectiva> {
    // Usamos EntityManager para las operaciones de persistencia con JPA y Hibernate
    private EntityManager em;

    // Constructor para inyectar el EntityManager
    public RepoSesionColectivaImplements(EntityManager em) {
        this.em = em;
    }

    // Recupera una lista de SesionColectiva con un máximo de 'num' resultados
    @Override
    public List<SesionColectiva> findAll(Integer num) {
        return em.createQuery("SELECT s FROM SesionColectiva s", SesionColectiva.class)
                .setMaxResults(num)
                .getResultList();
    }

    // Busca una SesionColectiva por su ID
    @Override
    public SesionColectiva findBy(Integer id) {
        return em.find(SesionColectiva.class, id);
    }

    // Guarda o actualiza una SesionColectiva en la base de datos
    @Override
    public void save(SesionColectiva sesionColectiva) {
        try {
            em.getTransaction().begin();
            if (sesionColectiva.getId() != null && sesionColectiva.getId() > 0) {
                em.merge(sesionColectiva);
            } else {
                em.persist(sesionColectiva);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException("Error al guardar SesionColectiva", e);
        }
    }

    // Elimina una SesionColectiva por su ID
    @Override
    public void delete(Integer id) {
        try {
            em.getTransaction().begin();
            SesionColectiva sesionColectiva = findBy(id);
            if (sesionColectiva != null) {
                em.remove(sesionColectiva);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar SesionColectiva", e);
        }
    }
}

