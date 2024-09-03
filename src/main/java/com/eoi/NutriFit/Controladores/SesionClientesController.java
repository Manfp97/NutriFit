package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.SesionClientes;
import com.eoi.NutriFit.Servicios.SesionClientesServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de sesiones de clientes.
 * Este controlador maneja las operaciones CRUD relacionadas con la entidad SesionClientes, incluyendo la visualización, creación, actualización y eliminación de sesiones de clientes.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/sesionesClientes")
public class SesionClientesController {

    @Autowired
    private SesionClientesServi service;

    /**
     * Maneja las solicitudes GET para listar todas las sesiones de clientes.
     *
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre de la vista que muestra la lista de sesiones de clientes.
     */
    @GetMapping
    public String getAll(Model model) {
        List<SesionClientes> listaSesiones = service.buscarEntidades();
        model.addAttribute("sesionClientes", listaSesiones);
        return "sesionClientes";
    }

    /**
     * Maneja las solicitudes GET para obtener una sesión de cliente específica por su ID.
     *
     * @param id Identificador de la sesión de cliente a recuperar.
     * @return Una respuesta que contiene la sesión de cliente si se encuentra, o una respuesta 404 si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SesionClientes> getById(@PathVariable Integer id) {
        Optional<SesionClientes> sesionCliente = service.encuentraPorId(id);
        if (sesionCliente.isPresent()) {
            return ResponseEntity.ok(sesionCliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Maneja las solicitudes POST para crear una nueva sesión de cliente.
     *
     * @param sesionCliente Objeto SesionClientes con los datos de la nueva sesión de cliente.
     * @return La sesión de cliente recién creada.
     * @throws Exception Si ocurre un error al guardar la sesión de cliente.
     */
    @PostMapping
    public SesionClientes create(@RequestBody SesionClientes sesionCliente) throws Exception {
        return service.guardar(sesionCliente);
    }

    /**
     * Maneja las solicitudes PUT para actualizar una sesión de cliente existente por su ID.
     *
     * @param id Identificador de la sesión de cliente a actualizar.
     * @param sesionCliente Objeto SesionClientes con los datos actualizados.
     * @return Una respuesta que contiene la sesión de cliente actualizada si se encuentra, o una respuesta 404 si no se encuentra.
     * @throws Exception Si ocurre un error al guardar la sesión de cliente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SesionClientes> update(@PathVariable Integer id, @RequestBody SesionClientes sesionCliente) throws Exception {
        Optional<SesionClientes> existingSesionCliente = service.encuentraPorId(id);
        if (existingSesionCliente.isPresent()) {
            sesionCliente.setId(id);
            return ResponseEntity.ok(service.guardar(sesionCliente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Maneja las solicitudes DELETE para eliminar una sesión de cliente por su ID.
     *
     * @param id Identificador de la sesión de cliente a eliminar.
     * @return Una respuesta que indica si la eliminación fue exitosa o si la sesión de cliente no se encontró.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.encuentraPorId(id).isPresent()) {
            service.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
