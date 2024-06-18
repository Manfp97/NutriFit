package org.cplcursos.java.Repositorios;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Cliente;

import java.util.List;

public class ClienteRepoImpl implements Repo<Cliente> {
    /*
        El repositorio utiliza el EntityManager, pero sabemos que hay que cerrarlo después de ser utilizado.
        Si lo cerramos en cualquiera de los métodos del repositorio, no podrá ser utilizado de nuevo en posteriores
        llamadas. Por tanto, el em debe ser proporcionado por la capa superior que utilice este repositorio y cerrado
        por esa misma capa
     */
    private EntityManager em;

    public ClienteRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Cliente> listar(Integer num) {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public Cliente porId(Integer id) {
        return em.find(Cliente.class, id);
    }

    @Override
    public void guardar(Cliente cliente) {
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
    public void eliminar(Integer id) {
        try{
            em.getTransaction().begin();
            Cliente cli = porId(id);
            if (cli != null) {
                em.remove(cli);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}