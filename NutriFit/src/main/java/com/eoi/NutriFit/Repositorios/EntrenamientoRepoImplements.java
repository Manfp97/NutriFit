package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EntrenamientoRepoImplements implements Repo<Entrenamiento> {

    private EntityManager em;

    public EntrenamientoRepoImplements(EntityManager em) {
        this.em = em;
    }
    @Override
    public List<Entrenamiento> list(Integer num) {
        return em.createQuery("SELECT r FROM Entrenamiento r", Entrenamiento.class)
                .setFirstResult(num)
                .getResultList();
    }

    @Override
    public Entrenamiento findby(Integer id) {
        return em.find(Entrenamiento.class, id);
    }

    @Override
    public void save(Entrenamiento entrenamiento) {
        try {
            em.getTransaction().begin();
            if (entrenamiento.getId() != null && entrenamiento.getId() > 0) {
                em.merge(entrenamiento);
            } else {
                em.persist(entrenamiento);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            em.getTransaction().begin();
            Entrenamiento entrenamiento = findby(id);
            if (entrenamiento != null) {
                em.remove(entrenamiento);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
