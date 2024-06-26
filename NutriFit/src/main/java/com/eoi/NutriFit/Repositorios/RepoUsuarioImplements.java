package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RepoUsuarioImplements implements Repo<Usuario> {

    //Usamos la entityManager para usar los repositorios con jpa e Hibernate
    private EntityManager em;


    // Constructor para inyectar el EntityManager

    public RepoUsuarioImplements(EntityManager em) {
        this.em = em;
    }

    // Recupera una lista de Roles con un máximo de 'num' resultados
    @Override
    public List<Usuario> findAll(Integer num) {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class)
                .setMaxResults(num)
                .getResultList();
    }

    // Busca un Roles por su ID
    @Override
    public Usuario findBy(Integer id) {
        return em.find(Usuario.class, id);
    }

    // Guarda o actualiza un Roles en la base de datos
    @Override
    public void save(Usuario usuario) {
        try{
            em.getTransaction().begin();
            if (usuario.getId() != null && usuario.getId() > 0) {
                em.merge(usuario);
            }else {
                em.persist(usuario);
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
           Usuario usuario = findBy(id);
           if (usuario != null) {
               em.remove(usuario);
               em.getTransaction().commit();
           }
       }catch (Exception e) {
           em.getTransaction().rollback();
           e.printStackTrace();
       }
    }
}
