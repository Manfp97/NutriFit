package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ClienteDieta;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class ClienteDietaRepoImplements implements Repo<ClienteDieta> {
    private EntityManager em;

    public ClienteDietaRepoImplements(EntityManager em) { this.em = em;}

    @Override
    public List<ClienteDieta> list(Integer num) {
        return em.createQuery("SELECT c FROM ClienteDieta c", ClienteDieta.class).setFirstResult(num)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public ClienteDieta findby(Integer id) {
        return em.find(ClienteDieta.class, id);
    }

    @Override
    public void save(ClienteDieta clienteDieta) {
        try {
            em.getTransaction().begin();
            if (clienteDieta.getId() != null && clienteDieta.getId() > 0) {
                em.merge(clienteDieta);
            } else {
                em.persist(clienteDieta);
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
            ClienteDieta clienteDieta = findby(id);
            if (clienteDieta != null) {
                em.remove(clienteDieta);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }


}
