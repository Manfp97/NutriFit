package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductoRepoImplements implements Repo<Producto> {

    private EntityManager em;

    public ProductoRepoImplements(EntityManager em) {this.em = em;}

    @Override
    public List<Producto> list(Integer num) {
        return em.createQuery("SELECT p FROM Producto p", Producto.class)
                .setFirstResult(num)
                .getResultList();
    }

    @Override
    public Producto findby(Integer id) {
        return em.find(Producto.class, id);
    }

    @Override
    public void save(Producto producto) {
        try {
            em.getTransaction().begin();
            if (producto.getId() != null && producto.getId() > 0) {
                em.merge(producto);
            } else {
                em.persist(producto);
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
            Producto producto = em.find(Producto.class, id);
            if (producto != null) {
                em.remove(producto);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
