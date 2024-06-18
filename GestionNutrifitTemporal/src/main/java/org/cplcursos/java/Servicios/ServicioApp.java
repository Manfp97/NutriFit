package org.cplcursos.java.Servicios;

import org.cplcursos.java.Entidades.*;

import java.util.List;
import java.util.Optional;

public interface ServicioApp {
    List<Cliente> listaCli(Integer num);
    Optional<Cliente> porId(Integer id);
    void guardar(Cliente cli);
    void eliminar(Integer id);

    List<Albaran> listaAlb(Integer num);
    Optional<Albaran> porIdAlbaran(Integer id);
    void guardarAlb(Albaran alb);
    void eliminarAlb(Integer id);

    List<Clase_Clientes> listaCitas();
    Optional<Clase_Clientes> porIdCita(Integer id);
    void guardarCita(Clase_Clientes claseClientes);

    List<Proveedores> listaProv(Integer num);
    Optional<Proveedores> porIdProv(Integer id);
    void guardarProv(Proveedores prov);

    List<Producto> listaPiezas(Integer num);
    Optional<Producto> porIdPieza(Integer id);
    void guardarPieza(Producto pz);
}