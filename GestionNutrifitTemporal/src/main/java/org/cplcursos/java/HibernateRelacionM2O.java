package org.cplcursos.java;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Albaran;
import org.cplcursos.java.Entidades.Cliente;
import org.cplcursos.java.Servicios.ServicioApp;
import org.cplcursos.java.Servicios.ServicioAppImpl;
import org.cplcursos.java.util.JpaUtil;

import java.time.LocalDateTime;

public class HibernateRelacionM2O {
    public static void main(String[] args) {
        // Creamos el Entitymanager en la capa que use el repositorio
        EntityManager em = JpaUtil.getEntitymanager();
        // Creamos una instancia de la capa de servicio que vamos a utilizar
        ServicioApp srvcapp = new ServicioAppImpl(em);

        System.out.println("------------------ Añadir albarán a un cliente --------------");
        try {
            // Creamos un albarán que asignamos al cliente 6
            Albaran alb = new Albaran();
            alb.setFecha(LocalDateTime.now());
            alb.setImporte(456.45F);
            // cargamos los datos del cliente para asignaros al alabarán
            Cliente cli = srvcapp.porId(6).get();
            alb.setCliente(cli);

            // Iniciamos la transacción
            em.getTransaction().begin();
            em.persist(alb);
            /*
                Como se ha definido una relación "muchos a uno" ente Albaran y Cliente, JPA asigna automáticamente
                el id del cliente -propiedad "cliente.id"- al campo "id_cliente" de la tabla "albaranes" a la hora de
                guardar los datos en la bbdd
             */

            // Volcamos las operaciones realizadas a la BBDD -transaction.commit()-
            em.getTransaction().commit();
            // Mostramos los datos del albarán
            System.out.println(alb);
        } catch (Exception e) {
            // Deshacemos las operaciones pendientes anteriores
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            // Cerramos la EntityManager
            em.close();
        }

    }
}