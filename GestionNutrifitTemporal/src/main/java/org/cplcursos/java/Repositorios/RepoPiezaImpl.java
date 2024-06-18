package org.cplcursos.java.Repositorios;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Producto;

import java.util.List;

public class RepoPiezaImpl implements Repo<Producto> {
    /*
        El repositorio utiliza el EntityManager, pero sabemos que hay que cerrarlo después de ser utilizado.
        Si lo cerramos en cualquiera de los métodos del repositorio, no podrá ser utilizado de nuevo en posteriores
        llamadas. Por tanto, el em debe ser proporcionado por la capa superior que utilice este repositorio y cerrado
        por esa misma capa
     */
    private EntityManager em;

    public RepoPiezaImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Producto> listar(Integer num) {
        return em.createQuery("SELECT pz FROM Producto pz", Producto.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public Producto porId(Integer id) {
        return em.find(Producto.class, id);
    }

    @Override
    public void guardar(Producto pieza) {
        try {
            em.getTransaction().begin();
            if (pieza.getId() != null && pieza.getId() > 0) {
                em.merge(pieza);
            } else {
                em.persist(pieza);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer id) {

    }
}