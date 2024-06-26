package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Proveedores;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RepoProveedoresImplements implements Repo<Proveedores> {

    private EntityManager em;

    public RepoProveedoresImplements(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Proveedores> findAll(Integer num) {
        return em.createQuery("SELECT p FROM Proveedores p", Proveedores.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public Proveedores findBy(Integer id) {
        return em.find(Proveedores.class, id);
    }

    @Override
    public void save(Proveedores proveedor) {
        try {
            em.getTransaction().begin();
            if (proveedor.getId() != null && proveedor.getId() > 0) {
                em.merge(proveedor);
            } else {
                em.persist(proveedor);
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
            Proveedores proveedor = findBy(id);
            if (proveedor != null) {
                em.remove(proveedor);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
