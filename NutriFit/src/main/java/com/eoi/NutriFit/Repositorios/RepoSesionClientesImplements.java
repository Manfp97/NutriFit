package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.SesionClientes;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RepoSesionClientesImplements implements Repo<SesionClientes> {

    // Usamos EntityManager para las operaciones de persistencia con JPA y Hibernate
    private EntityManager em;

    // Constructor para inyectar el EntityManager
    public RepoSesionClientesImplements(EntityManager em) {
        this.em = em;
    }

    // Recupera una lista de SesionClientes con un máximo de 'num' resultados
    @Override
    public List<SesionClientes> findAll(Integer num) {
        return em.createQuery("SELECT s FROM SesionClientes s", SesionClientes.class)
                .setMaxResults(num)
                .getResultList();
    }

    // Busca un SesionClientes por su ID
    @Override
    public SesionClientes findBy(Integer id) {
        return em.find(SesionClientes.class, id);
    }

    // Guarda o actualiza un SesionClientes en la base de datos
    @Override
    public void save(SesionClientes sesionClientes) {
        try {
            em.getTransaction().begin();
            if (sesionClientes.getId() != null && sesionClientes.getId() > 0) {
                em.merge(sesionClientes);
            } else {
                em.persist(sesionClientes);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException("Error al guardar SesionClientes", e);
        }
    }

    // Elimina un SesionClientes por su ID
    @Override
    public void delete(Integer id) {
        try {
            em.getTransaction().begin();
            SesionClientes sesionClientes = findBy(id);
            if (sesionClientes != null) {
                em.remove(sesionClientes);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar SesionClientes", e);
        }
    }
}

