package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.*;
import com.eoi.NutriFit.Repositorios.*;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class ServicioAppImpl implements ServicioApp {
    private Repo<Carrito> repoCarr;
    private Repo<Cliente> repoCli;
    private Repo<ClienteDieta> repoCliDieta;
    private Repo<ClienteEntrenamiento> repoCliEntrenamiento;
    private Repo<DetallesCliente> repoCliDetalles;
    private Repo<DetallesDieta> repoCliDetallesDieta;
    private Repo<DetallesEntrenamiento> repoCliDetallesEntrenamiento;
    private Repo<DetalleUsuario> repoCliDetallesUsuario;
    private Repo<Dieta> repoDieta;

    public ServicioAppImpl(EntityManager em) {
        this.repoCarr = new CarritoRepoImplements(em);
        this.repoCli = new ClienteRepoImplements(em);
        this.repoCliDieta = new ClienteDietaRepoImplements(em);
        this.repoCliEntrenamiento = new ClienteEntrenamientoRepoImplements(em);
        this.repoCliDetalles = new DetallesClienteRepoImplements(em);
        this.repoCliDetallesDieta = new DetallesDietaRepoImplements(em);
        this.repoCliDetallesEntrenamiento  = new DetallesEntrenamientoRepoImplements(em);
        this.repoCliDetallesUsuario = new DetalleUsuarioRepoImplements(em);
        this.repoDieta = new DietaRepoImplements(em);
    }

    @Override
    public List<Carrito> listarCarrito(Integer num) {
        return repoCarr.list(num);
    }

    @Override
    public Optional<Carrito> buscarUsuario(Integer id) {
        return Optional.ofNullable(repoCarr.findby(id));
    }

    @Override
    public void guardarCarrito(Carrito carrito) {
        repoCarr.save(carrito);
    }

    @Override
    public void borrarCarrito(Carrito carrito) {
        repoCarr.delete(carrito.getId());
    }

    @Override
    public List<Carrito> listarCliente(Integer num) {
        return repoCarr.list(num);
    }

    @Override
    public Optional<Cliente> buscarCliente(Integer id) {
        return Optional.ofNullable(repoCli.findby(id));
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        repoCli.save(cliente);
    }

    @Override
    public void borrarCliente(Cliente cliente) {
        repoCli.delete(cliente.getId());
    }

    @Override
    public List<ClienteDieta> listarClienteDieta(Integer num) {
        return repoCliDieta.list(num);
    }

    @Override
    public Optional<ClienteDieta> buscarClienteDieta(Integer id) {
        return Optional.ofNullable(repoCliDieta.findby(id));
    }

    @Override
    public void guardarClienteDieta(ClienteDieta clienteDieta) {
        repoCliDieta.save(clienteDieta);
    }

    @Override
    public void borrarClienteDieta(ClienteDieta clienteDieta) {
        repoCliDieta.delete(clienteDieta.getId());
    }

    @Override
    public List<ClienteEntrenamiento> listarClienteEntrenamiento(Integer num) {
        return repoCliEntrenamiento.list(num);
    }

    @Override
    public Optional<ClienteEntrenamiento> buscarClienteEntrenamiento(Integer id) {
        return Optional.ofNullable(repoCliEntrenamiento.findby(id));
    }

    @Override
    public void guardarClienteEntramiento(ClienteEntrenamiento clienteEntrenamiento) {
        repoCliEntrenamiento.save(clienteEntrenamiento);
    }

    @Override
    public void borrarClienteEntramiento(ClienteEntrenamiento clienteEntrenamiento) {
        repoCliEntrenamiento.delete(clienteEntrenamiento.getId());
    }

    @Override
    public List<DetallesCliente> listarDetallesCliente(Integer num) {
        return repoCliDetalles.list(num);
    }

    @Override
    public Optional<DetallesCliente> buscarDetallesCliente(Integer id) {
        return Optional.ofNullable(repoCliDetalles.findby(id));
    }

    @Override
    public void guardarDetallesCliente(DetallesCliente detallesCliente) {
        repoCliDetalles.save(detallesCliente);
    }

    @Override
    public void borrarDetallesCliente(DetallesCliente detallesCliente) {
        repoCliDetalles.delete(detallesCliente.getId());
    }

    @Override
    public List<DetallesDieta> listarDetallesDieta(Integer num) {
        return repoCliDetallesDieta.list(num);
    }

    @Override
    public Optional<DetallesDieta> buscarDetallesDieta(Integer id) {
        return Optional.ofNullable(repoCliDetallesDieta.findby(id));
    }

    @Override
    public void guardarDetallesDieta(DetallesDieta detallesDieta) {
        repoCliDetallesDieta.save(detallesDieta);
    }

    @Override
    public void borrarDetallesDieta(DetallesDieta detallesDieta) {
        repoCliDetallesDieta.delete(detallesDieta.getId());
    }

    @Override
    public List<DetallesEntrenamiento> listarDetallesEntrenamiento(Integer num) {
        return repoCliDetallesEntrenamiento.list(num);
    }

    @Override
    public Optional<DetallesEntrenamiento> buscarDetalleEntrenamiento(Integer id) {
        return Optional.ofNullable(repoCliDetallesEntrenamiento.findby(id));
    }

    @Override
    public void guardarDetallesEntrenamiento(DetallesEntrenamiento detallesEntrenamiento) {
        repoCliDetallesEntrenamiento.save(detallesEntrenamiento);
    }

    @Override
    public void borrarDetallesEntrenamiento(DetallesEntrenamiento detallesEntrenamiento) {
        repoCliDetallesEntrenamiento.delete(detallesEntrenamiento.getId());
    }

    @Override
    public List<DetalleUsuario> listarDetalleUsuario(Integer num) {
        return repoCliDetallesUsuario.list(num);
    }

    @Override
    public Optional<DetalleUsuario> buscarDetalleUsuario(Integer id) {
        return Optional.ofNullable(repoCliDetallesUsuario.findby(id));
    }

    @Override
    public void guardarDetalleUsuario(DetalleUsuario detalleUsuario) {
        repoCliDetallesUsuario.save(detalleUsuario);
    }

    @Override
    public void borrarDetalleUsuario(DetalleUsuario detalleUsuario) {
        repoCliDetallesUsuario.delete(detalleUsuario.getId());
    }

    @Override
    public List<Dieta> listarDieta(Integer num) {
        return repoDieta.list(num);
    }

    @Override
    public Optional<Dieta> buscarDieta(Integer id) {
        return Optional.ofNullable(repoDieta.findby(id));
    }

    @Override
    public void guardarDieta(Dieta dieta) {
        repoDieta.save(dieta);
    }

    @Override
    public void borrarDieta(Dieta dieta) {
        repoDieta.delete(dieta.getId());
    }
}
