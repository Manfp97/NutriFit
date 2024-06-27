package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetallesEntrenamiento;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DetallesEntrenamientoRepoImplements implements Repo<DetallesEntrenamiento> {

    private EntityManager em;
    public DetallesEntrenamientoRepoImplements(EntityManager em) {this.em = em;}

    @Override
    public List<DetallesEntrenamiento> list(Integer num) {
        return em.createQuery("SELECT d FROM DetallesEntrenamiento d", DetallesEntrenamiento.class)
                .setFirstResult(num)
                .getResultList();
    }

    @Override
    public DetallesEntrenamiento findby(Integer id) {
        return em.find(DetallesEntrenamiento.class, id);
    }

    @Override
    public void save(DetallesEntrenamiento detallesEntrenamiento) {
        try {
            em.getTransaction().begin();
            if (detallesEntrenamiento.getId() != null && detallesEntrenamiento.getId() > 0) {
                em.merge(detallesEntrenamiento);
            } else {
                em.persist(detallesEntrenamiento);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try{
            em.getTransaction().begin();
            DetallesEntrenamiento entrenamiento = em.find(DetallesEntrenamiento.class, id);
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
