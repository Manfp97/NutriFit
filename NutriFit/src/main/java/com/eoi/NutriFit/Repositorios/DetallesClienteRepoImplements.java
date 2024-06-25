package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetallesCliente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DetallesClienteRepoImplements implements Repo<DetallesCliente> {
    private EntityManager em;

    public DetallesClienteRepoImplements(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<DetallesCliente> list(Integer num) {
        return em.createQuery("SELECT a FROM DetallesCliente a", DetallesCliente.class).getResultList();
    }

    @Override
    public DetallesCliente findby(Integer id) {
        return em.find(DetallesCliente.class, id);
    }

    @Override
    public void save(DetallesCliente detallesCliente) {
        try {
            em.getTransaction().begin();
            if (detallesCliente.getId() != null && detallesCliente.getId() > 0) {
                em.merge(detallesCliente);
            } else {
                em.persist(detallesCliente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            em.getTransaction().begin();
            DetallesCliente detallesCliente = em.find(DetallesCliente.class, id);
            if (detallesCliente != null) {
                em.remove(detallesCliente);
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }

    }
}
