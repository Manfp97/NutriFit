package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Roles;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RepoRolesImplements implements Repo<Roles> {

    //Usamos la entityManager para usar los repositorios con jpa e Hibernate
    private EntityManager em;

    // Constructor para inyectar el EntityManager
    public RepoRolesImplements(EntityManager em) {
        this.em = em;
    }

    // Recupera una lista de Roles con un máximo de 'num' resultados
    @Override
    public List<Roles> findAll(Integer num) {
        return em.createQuery("SELECT r FROM Roles r", Roles.class)
                .setMaxResults(num)
                .getResultList();
    }

    // Busca un Roles por su ID
    @Override
    public Roles findBy(Integer id) {
        return em.find(Roles.class, id);
    }

    // Guarda o actualiza un Roles en la base de datos
    @Override
    public void save(Roles roles) {
        try {
            em.getTransaction().begin();
            if (roles.getId() != null && roles.getId() > 0) {
                em.merge(roles);
            } else {
                em.persist(roles);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Elimina un Roles por su ID
    @Override
    public void delete(Integer id) {
        try {
            em.getTransaction().begin();
            Roles roles = findBy(id);
            if (roles != null) {
                em.remove(roles);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

