package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.SesionColectiva;
import com.eoi.NutriFit.Servicios.SesionColectivaServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de sesiones colectivas.
 * Este controlador maneja las operaciones CRUD relacionadas con la entidad SesionColectiva, incluyendo la visualización, creación, actualización y eliminación de sesiones colectivas.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/sesionColectiva")
public class SesionColectivaController {

    @Autowired
    private SesionColectivaServi service;

    /**
     * Maneja las solicitudes GET para listar todas las sesiones colectivas.
     *
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre de la vista que muestra la lista de sesiones colectivas.
     */
    @GetMapping
    public String getAll(Model model) {
        List<SesionColectiva> listaDetalles = service.buscarEntidades();
        model.addAttribute("detalleSesionColectivas", listaDetalles);
        return "detalleSesionColectivas"; // Suponiendo que este es el nombre del archivo Thymeleaf
    }

    /**
     * Maneja las solicitudes GET para obtener una sesión colectiva específica por su ID.
     *
     * @param id Identificador de la sesión colectiva a recuperar.
     * @return Una respuesta que contiene la sesión colectiva si se encuentra, o una respuesta 404 si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SesionColectiva> getById(@PathVariable Integer id) {
        Optional<SesionColectiva> detalleSesionColectiva = service.encuentraPorId(id);
        return detalleSesionColectiva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Maneja las solicitudes POST para crear una nueva sesión colectiva.
     *
     * @param detalleSesionColectiva Objeto SesionColectiva con los datos de la nueva sesión colectiva.
     * @return Una respuesta que contiene la sesión colectiva recién creada, o una respuesta 500 si ocurre un error.
     */
    @PostMapping
    public ResponseEntity<SesionColectiva> create(@RequestBody SesionColectiva detalleSesionColectiva) {
        try {
            SesionColectiva createdSesionColectiva = service.guardar(detalleSesionColectiva);
            return ResponseEntity.ok(createdSesionColectiva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Maneja las solicitudes PUT para actualizar una sesión colectiva existente por su ID.
     *
     * @param id Identificador de la sesión colectiva a actualizar.
     * @param detalleSesionColectiva Objeto SesionColectiva con los datos actualizados.
     * @return Una respuesta que contiene la sesión colectiva actualizada si se encuentra, o una respuesta 404 si no se encuentra.
     *         Una respuesta 500 en caso de error durante la actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SesionColectiva> update(@PathVariable Integer id, @RequestBody SesionColectiva detalleSesionColectiva) {
        try {
            Optional<SesionColectiva> existingDetalleSesionColectiva = service.encuentraPorId(id);
            if (existingDetalleSesionColectiva.isPresent()) {
                detalleSesionColectiva.setId(id);
                SesionColectiva updatedSesionColectiva = service.guardar(detalleSesionColectiva);
                return ResponseEntity.ok(updatedSesionColectiva);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Maneja las solicitudes DELETE para eliminar una sesión colectiva por su ID.
     *
     * @param id Identificador de la sesión colectiva a eliminar.
     * @return Una respuesta 204 si la eliminación fue exitosa, una respuesta 404 si la sesión colectiva no se encuentra, o una respuesta 500 en caso de error durante la eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            if (service.encuentraPorId(id).isPresent()) {
                service.eliminarPorId(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
