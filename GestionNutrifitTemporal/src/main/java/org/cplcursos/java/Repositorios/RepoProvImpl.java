package org.cplcursos.java.Repositorios;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Proveedores;

import java.util.List;

public class RepoProvImpl implements Repo<Proveedores>{
    /*
        El repositorio utiliza el EntityManager, pero sabemos que hay que cerrarlo después de ser utilizado.
        Si lo cerramos en cualquiera de los métodos del repositorio, no podrá ser utilizado de nuevo en posteriores
        llamadas. Por tanto, el em debe ser proporcionado por la capa superior que utilice este repositorio y cerrado
        por esa misma capa
     */
    private EntityManager em;

    public RepoProvImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Proveedores> listar(Integer num) {
        return em.createQuery("SELECT p FROM Proveedores p", Proveedores.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public Proveedores porId(Integer id) {
        return em.find(Proveedores.class, id);
    }

    @Override
    public void guardar(Proveedores proveedor) {
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
    public void eliminar(Integer id) {

    }
}