package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ProgresionesEntrenamiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class RepoProgresionEntrenamientoImplements implements RepoInterfaz<ProgresionesEntrenamiento> {

    private EntityManager em;

    public RepoProgresionEntrenamientoImplements(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ProgresionesEntrenamiento> findAll(Integer num) {
        return em.createQuery("SELECT p FROM ProgresionesEntrenamiento p", ProgresionesEntrenamiento.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public ProgresionesEntrenamiento findBy(Integer id) {
        return em.find(ProgresionesEntrenamiento.class, id);
    }

    @Override
    public void save(ProgresionesEntrenamiento progresion) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (progresion.getId() != null && progresion.getId() > 0) {
                em.merge(progresion);
            } else {
                em.persist(progresion);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Manejar la excepción de manera específica según tu aplicación
        }
    }

    @Override
    public void delete(Integer id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            ProgresionesEntrenamiento progresion = findBy(id);
            if (progresion != null) {
                em.remove(progresion);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Manejar la excepción de manera específica según tu aplicación
        }
    }
}
