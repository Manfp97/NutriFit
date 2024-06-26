package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.*;

import java.util.List;
import java.util.Optional;

public interface ServicioApp {
    List<Carrito> listarCarrito(Integer num);
    Optional<Carrito> buscarUsuario(Integer id);
    void guardarCarrito(Carrito carrito);
    void borrarCarrito(Carrito carrito);

    List<Carrito> listarCliente(Integer num);
    Optional<Cliente> buscarCliente(Integer id);
    void guardarCliente(Cliente cliente);
    void borrarCliente(Cliente cliente);

    List<ClienteDieta> listarClienteDieta(Integer num);
    Optional<ClienteDieta> buscarClienteDieta(Integer id);
    void guardarClienteDieta(ClienteDieta clienteDieta);
    void borrarClienteDieta(ClienteDieta clienteDieta);

    List<ClienteEntrenamiento> listarClienteEntrenamiento(Integer num);
    Optional<ClienteEntrenamiento> buscarClienteEntrenamiento(Integer id);
    void guardarClienteEntramiento(ClienteEntrenamiento clienteEntrenamiento);
    void borrarClienteEntramiento(ClienteEntrenamiento clienteEntrenamiento);

    List<DetallesCliente> listarDetallesCliente(Integer num);
    Optional<DetallesCliente> buscarDetallesCliente(Integer id);
    void guardarDetallesCliente(DetallesCliente detallesCliente);
    void borrarDetallesCliente(DetallesCliente detallesCliente);

    List<DetallesDieta> listarDetallesDieta(Integer num);
    Optional<DetallesDieta> buscarDetallesDieta(Integer id);
    void guardarDetallesDieta(DetallesDieta detallesDieta);
    void borrarDetallesDieta(DetallesDieta detallesDieta);

    List<DetallesEntrenamiento> listarDetallesEntrenamiento(Integer num);
    Optional<DetallesEntrenamiento> buscarDetalleEntrenamiento(Integer id);
    void guardarDetallesEntrenamiento(DetallesEntrenamiento detallesEntrenamiento);
    void borrarDetallesEntrenamiento(DetallesEntrenamiento detallesEntrenamiento);

    List<DetalleUsuario> listarDetalleUsuario(Integer num);
    Optional<DetalleUsuario> buscarDetalleUsuario(Integer id);
    void guardarDetalleUsuario(DetalleUsuario detalleUsuario);
    void borrarDetalleUsuario(DetalleUsuario detalleUsuario);

    List<Dieta> listarDieta(Integer num);
    Optional<Dieta> buscarDieta(Integer id);
    void guardarDieta(Dieta dieta);
    void borrarDieta(Dieta dieta);
}
