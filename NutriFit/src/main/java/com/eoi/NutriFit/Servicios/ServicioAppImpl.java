package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.*;
import com.eoi.NutriFit.Repositorios.*;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class ServicioAppImpl implements ServivioApp {

    private Repo<Usuario> usuarioRepo;
    private Repo<SesionColectiva> sesionColectivaRepo;
    private Repo<Roles> rolesRepo;
    private Repo<Proveedores> proveedoresRepo;
    private Repo<ProgresionesEntrenamiento> progresionesEntrenamientoRepo;
    private Repo<ProgresionDieta> progresionDietaRepo;

    public ServicioAppImpl(EntityManager em) {
        this.usuarioRepo = new RepoUsuarioImplements(em);
        this.sesionColectivaRepo = new RepoSesionColectivaImplements(em);
        this.rolesRepo = new RepoRolesImplements(em);
        this.proveedoresRepo = new RepoProveedoresImplements(em);
        this.progresionesEntrenamientoRepo = new RepoProgresionEntrenamientoImplements(em);
        this.progresionDietaRepo = new RepoProgresionDietaImplements(em);
    }

    // Métodos para Usuario
    @Override
    public List<Usuario> listarUsuarios(Integer num) {
        return usuarioRepo.findAll(num);
    }

    @Override
    public Optional<Usuario> buscarUsuario(Integer id) {
        return Optional.ofNullable(usuarioRepo.findBy(id));
    }

    @Override
    public void guardarUsu(Usuario usuario) {
        usuarioRepo.save(usuario);
    }

    @Override
    public void eliminarUsu(Integer id) {
        usuarioRepo.delete(id);
    }

    // Métodos para SesionColectiva
    @Override
    public List<SesionColectiva> listarSesiones(Integer num) {
        return sesionColectivaRepo.findAll(num);
    }

    @Override
    public Optional<SesionColectiva> buscarSesion(Integer id) {
        return Optional.ofNullable(sesionColectivaRepo.findBy(id));
    }

    @Override
    public void guardarSesion(SesionColectiva sesion) {
        sesionColectivaRepo.save(sesion);
    }

    @Override
    public void eliminarSesion(Integer id) {
        sesionColectivaRepo.delete(id);
    }

    // Métodos adicionales para SesionColectiva (repetitivos, se podrían unificar)
    @Override
    public List<SesionColectiva> listarSesiones() {
        return sesionColectivaRepo.findAll(9999);
    }

    @Override
    public Optional<SesionColectiva> buscarSesiones(Integer id) {
        return Optional.ofNullable(sesionColectivaRepo.findBy(id));
    }

    @Override
    public void guardarSesiones(SesionColectiva sesion) {
        sesionColectivaRepo.save(sesion);
    }

    @Override
    public void eliminarSesiones(Integer id) {
        sesionColectivaRepo.delete(id);
    }

    // Métodos para Roles
    @Override
    public List<Roles> listarRoles(Integer num) {
        return rolesRepo.findAll(num);
    }

    @Override
    public Optional<Roles> buscarRole(Integer id) {
        return Optional.ofNullable(rolesRepo.findBy(id));
    }

    @Override
    public void guardarRole(Roles role) {
        rolesRepo.save(role);
    }

    @Override
    public void eliminarRole(Integer id) {
        rolesRepo.delete(id);
    }

    // Métodos para Proveedores
    @Override
    public List<Proveedores> listarProveedores(Integer num) {
        return proveedoresRepo.findAll(num);
    }

    @Override
    public Optional<Proveedores> buscarProveedores(Integer id) {
        return Optional.ofNullable(proveedoresRepo.findBy(id));
    }

    @Override
    public void guardarProveedor(Proveedores proveedor) {
        proveedoresRepo.save(proveedor);
    }

    @Override
    public void eliminarProveedor(Integer id) {
        proveedoresRepo.delete(id);
    }

    // Métodos para ProgresionesEntrenamiento
    @Override
    public List<ProgresionesEntrenamiento> listarProgEntre(Integer num) {
        return progresionesEntrenamientoRepo.findAll(num);
    }

    @Override
    public Optional<ProgresionesEntrenamiento> buscarProgEntre(Integer id) {
        return Optional.ofNullable(progresionesEntrenamientoRepo.findBy(id));
    }

    @Override
    public void guardarProgEntrenamiento(ProgresionesEntrenamiento progresion) {
        progresionesEntrenamientoRepo.save(progresion);
    }

    @Override
    public void eliminarProgEntrenamiento(Integer id) {
        progresionesEntrenamientoRepo.delete(id);
    }

    // Métodos para ProgresionDieta
    @Override
    public List<ProgresionDieta> listarProgDieta(Integer num) {
        return progresionDietaRepo.findAll(num);
    }

    @Override
    public Optional<ProgresionDieta> buscarProgDieta(Integer id) {
        return Optional.ofNullable(progresionDietaRepo.findBy(id));
    }

    @Override
    public void guardarProgDieta(ProgresionDieta progresion) {
        progresionDietaRepo.save(progresion);
    }

    @Override
    public void eliminarProgDieta(Integer id) {
        progresionDietaRepo.delete(id);
    }
}
