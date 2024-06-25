package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetallesCliente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RepoDetallesClienteImplements implements RepoInterfaz<DetallesCliente> {

    //Usamos la entityManager para usar los repositorios con jpa e Hibernate
    private EntityManager em;

    // Constructor para inyectar el EntityManager
    public RepoDetallesClienteImplements(EntityManager em) {
        this.em = em;
    }
    // Recupera una lista de Roles con un máximo de 'num' resultados
    @Override
    public List<DetallesCliente>findAll(Integer num) {
        return em.createQuery("SELECT d FROM DetallesCliente d", DetallesCliente.class)
                .setMaxResults(num)
                .getResultList();
    }
    // Busca un Roles por su ID
    @Override
    public DetallesCliente findBy(Integer id) {
        return em.find(DetallesCliente.class, id);
    }

    // Guarda o actualiza un Roles en la base de datos
    @Override
    public void save(DetallesCliente detallesCliente) {
        try {
            em.getTransaction().begin();
            if (detallesCliente.getId() != null && detallesCliente.getId() > 0) {
                em.merge(detallesCliente);
            }else {
                em.persist(detallesCliente);
            }
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    // Elimina un Roles por su ID
    @Override
    public void delete(Integer id) {
       try{
           em.getTransaction().begin();
           DetallesCliente detallesCliente = findBy(id);
           if (detallesCliente != null) {
               em.remove(detallesCliente);
               em.getTransaction().commit();
           }
       }catch (Exception e) {
           em.getTransaction().rollback();
           e.printStackTrace();
       }
    }
}
