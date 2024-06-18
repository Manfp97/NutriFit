package org.cplcursos.java.Repositorios;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Albaran;

import java.util.List;

public class AlabranRepoImpl implements Repo<Albaran> {
    /*
        El repositorio utiliza el EntityManager, pero sabemos que hay que cerrarlo después de ser utilizado.
        Si lo cerramos en cualquiera de los métodos del repositorio, no podrá ser utilizado de nuevo en posteriores
        llamadas. Por tanto, el em debe ser proporcionado por la capa superior que utilice este repositorio y cerrado
        por esa misma capa
     */
    private EntityManager em;

    public AlabranRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Albaran> listar(Integer num) {
        return em.createQuery("SELECT a FROM Albaran a", Albaran.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public Albaran porId(Integer id) {
        return em.find(Albaran.class, id);
    }

    @Override
    public void guardar(Albaran albaran) {
        try {
            em.getTransaction().begin();
            if (albaran.getId() != null && albaran.getId() > 0) {
                em.merge(albaran);
            } else {
                em.persist(albaran);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer id) {
        try{
            em.getTransaction().begin();
            Albaran alb = porId(id);
            if (alb != null) {
                em.remove(alb);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
