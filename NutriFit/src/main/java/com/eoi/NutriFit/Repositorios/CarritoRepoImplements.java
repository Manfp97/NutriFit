package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Carrito;
import com.eoi.NutriFit.Entidades.Cliente;
import jakarta.persistence.EntityManager;
import com.eoi.NutriFit.Repositorios.CarritoRepoImplements;

import java.util.List;
import java.util.Optional;

public class CarritoRepoImplements implements Repo<Carrito> {

    private EntityManager em;

    public CarritoRepoImplements(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Carrito> list(Integer num) {
        return em.createQuery("SELECT a FROM Carrito a", Carrito.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public Carrito findby(Integer id) {
        return em.find(Carrito.class, id);
    }

    @Override
    public void save(Carrito carrito) {
        try {
            em.getTransaction().begin();
            if (carrito.getId() != null && carrito.getId() > 0) {
                em.merge(carrito);
            } else {
                em.persist(carrito);
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
            Carrito carrito = findby(id);
            if (carrito != null) {
                em.remove(carrito);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }


}
