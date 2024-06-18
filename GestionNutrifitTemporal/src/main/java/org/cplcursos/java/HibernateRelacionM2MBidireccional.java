package org.cplcursos.java;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Pieza;
import org.cplcursos.java.Entidades.Proveedor;
import org.cplcursos.java.Servicios.ServicioApp;
import org.cplcursos.java.Servicios.ServicioAppImpl;
import org.cplcursos.java.util.JpaUtil;

public class HibernateRelacionM2MBidireccional {
    public static void main(String[] args) {
        // Creamos el Entitymanager en la capa que use el repositorio
        EntityManager em = JpaUtil.getEntitymanager();
        // Creamos una instancia de la capa de servicio que vamos a utilizar
        ServicioApp srvcapp = new ServicioAppImpl(em);

        System.out.println("------------------ Buscamos un proveedor para añadirle una pieza --------------");
        Proveedor prov = srvcapp.porIdProv(2).get();
        System.out.println(prov);
        // creo piezas para el proveedor
        Pieza pz1 = new Pieza("PZ1","Pieza uno");
        Pieza pz2 = new Pieza("PZ2","Pieza dos");
        // añadimos las piezas al proveedor
        prov.nuevaPieza(pz1);
        srvcapp.guardarPieza(pz1);
        prov.nuevaPieza(pz2);
        srvcapp.guardarPieza(pz2);

        srvcapp.guardarProv(prov);

        System.out.println(prov);

        em.close();
    }



}