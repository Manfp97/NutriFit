package org.cplcursos.java;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Producto;
import org.cplcursos.java.Entidades.Proveedores;
import org.cplcursos.java.Servicios.ServicioApp;
import org.cplcursos.java.Servicios.ServicioAppImpl;
import org.cplcursos.java.util.JpaUtil;

public class HibernateRelacionM2M {
    public static void main(String[] args) {
        // Creamos el Entitymanager en la capa que use el repositorio
        EntityManager em = JpaUtil.getEntitymanager();
        // Creamos una instancia de la capa de servicio que vamos a utilizar
        ServicioApp srvcapp = new ServicioAppImpl(em);

        System.out.println("------------------ Buscamos un proveedor para añadirle una pieza --------------");
        Proveedores prov = srvcapp.porIdProv(2).get();
        System.out.println(prov);
        // creo piezas para el proveedor
        Producto pz1 = new Producto("PZ1","Pieza uno");
        Producto pz2 = new Producto("PZ2","Pieza dos");
        // añadimos las piezas al proveedor
        prov.getPiezas().add(pz1);
        srvcapp.guardarPieza(pz1);
        prov.getPiezas().add(pz2);
        srvcapp.guardarPieza(pz2);

        System.out.println(prov);

        em.close();
    }



}