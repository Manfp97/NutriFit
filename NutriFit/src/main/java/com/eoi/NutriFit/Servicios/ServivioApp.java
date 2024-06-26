package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.*;

import java.util.List;
import java.util.Optional;

public interface ServivioApp {
    List<Usuario> listarUsuarios(Integer num);
    Optional<Usuario> buscarUsuario(Integer id);
    void guardarUsu(Usuario usuario);
    void eliminarUsu(Integer id);

    List<SesionColectiva> listarSesiones(Integer num);
    Optional<SesionColectiva> buscarSesion(Integer id);
    void guardarSesion(SesionColectiva sesion);
    void eliminarSesion(Integer id);

    List<SesionColectiva> listarSesiones();
    Optional<SesionColectiva> buscarSesiones(Integer id);
    void guardarSesiones(SesionColectiva sesion);
    void eliminarSesiones(Integer id);

    List<Roles> listarRoles(Integer num);
    Optional<Roles> buscarRole(Integer id);
    void guardarRole(Roles role);
    void eliminarRole(Integer id);

    List<Proveedores> listarProveedores(Integer num);
    Optional<Proveedores> buscarProveedores(Integer id);
    void guardarProveedor(Proveedores proveedor);
    void eliminarProveedor(Integer id);

    List<ProgresionesEntrenamiento> listarProgEntre(Integer num);
    Optional<ProgresionesEntrenamiento> buscarProgEntre(Integer id);
    void guardarProgEntrenamiento(ProgresionesEntrenamiento progresion);
    void eliminarProgEntrenamiento(Integer id);

    List<ProgresionDieta> listarProgDieta(Integer num);
    Optional<ProgresionDieta> buscarProgDieta(Integer id);
    void guardarProgDieta(ProgresionDieta progresion);
    void eliminarProgDieta(Integer id);


}
