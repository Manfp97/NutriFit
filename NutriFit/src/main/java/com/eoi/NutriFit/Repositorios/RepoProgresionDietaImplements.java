package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ProgresionDieta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class RepoProgresionDietaImplements implements Repo<ProgresionDieta> {

    private EntityManager em;

    public RepoProgresionDietaImplements(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ProgresionDieta> findAll(Integer num) {
        return em.createQuery("SELECT pd FROM ProgresionDieta pd", ProgresionDieta.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public ProgresionDieta findBy(Integer id) {
        return em.find(ProgresionDieta.class, id);
    }

    @Override
    public void save(ProgresionDieta progresion) {
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
            ProgresionDieta progresion = findBy(id);
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
