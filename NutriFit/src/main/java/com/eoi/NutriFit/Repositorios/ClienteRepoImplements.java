package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Cliente;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class ClienteRepoImplements implements Repo<Cliente> {

    private EntityManager em;

    public ClienteRepoImplements(EntityManager em) {this.em =em; }

    @Override
    public List<Cliente> list(Integer num) {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public Cliente findby(Integer id) {
        return em.find(Cliente.class, id);
    }


    @Override
    public void save(Cliente cliente) {
        try {
            em.getTransaction().begin();
            if (cliente.getId() != null && cliente.getId() > 0) {
                em.merge(cliente);
            } else {
                em.persist(cliente);
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
            Cliente cliente = findby(id);
            if (cliente != null) {
                em.remove(cliente);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }
}
