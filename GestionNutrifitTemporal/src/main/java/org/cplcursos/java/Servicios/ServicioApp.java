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

    List<Cita> listaCitas();
    Optional<Cita> porIdCita(Integer id);
    void guardarCita(Cita cita);

    List<Proveedor> listaProv(Integer num);
    Optional<Proveedor> porIdProv(Integer id);
    void guardarProv(Proveedor prov);

    List<Pieza> listaPiezas(Integer num);
    Optional<Pieza> porIdPieza(Integer id);
    void guardarPieza(Pieza pz);
}