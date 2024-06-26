package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ClienteEntrenamiento;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteEntrenamientoRepoImplements implements Repo<ClienteEntrenamiento> {

    private EntityManager em;

    public ClienteEntrenamientoRepoImplements(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ClienteEntrenamiento> list(Integer num) {
        return em.createQuery("SELECT c FROM ClienteEntrenamiento c", ClienteEntrenamiento.class).getResultList();
    }

    @Override
    public ClienteEntrenamiento findby(Integer id) {
        return em.find(ClienteEntrenamiento.class, id);
    }

    @Override
    public void save(ClienteEntrenamiento clienteEntrenamiento) {
        try {
            em.getTransaction().begin();
            if (clienteEntrenamiento.getId() != null && clienteEntrenamiento.getId() > 0) {
                em.merge(clienteEntrenamiento);
            } else {
                em.persist(clienteEntrenamiento);
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
            ClienteEntrenamiento clienteEntrenamiento = findby(id);
            if (clienteEntrenamiento != null) {
                em.remove(clienteEntrenamiento);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
