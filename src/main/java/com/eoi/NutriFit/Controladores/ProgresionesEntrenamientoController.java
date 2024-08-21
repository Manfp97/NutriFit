package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.ProgresionesEntrenamiento;
import com.eoi.NutriFit.Servicios.ProgresionesEntrenamientoServi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de progresiones de entrenamiento.
 * Este controlador maneja las operaciones CRUD relacionadas con la entidad ProgresionesEntrenamiento.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/progresionesEntrenamientos")
public class ProgresionesEntrenamientoController {

    private final ProgresionesEntrenamientoServi service;

    /**
     * Constructor para inicializar el controlador con el servicio de progresiones de entrenamiento.
     *
     * @param service Servicio de progresiones de entrenamiento.
     */
    public ProgresionesEntrenamientoController(ProgresionesEntrenamientoServi service) {
        this.service = service;
    }

    /**
     * Maneja las solicitudes GET para obtener todas las progresiones de entrenamiento.
     *
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre de la vista que muestra todas las progresiones de entrenamiento.
     */
    @GetMapping
    public String getAll(Model model) {
        List<ProgresionesEntrenamiento> listaProgresiones = service.buscarEntidades();
        model.addAttribute("progresionesEntrenamiento", listaProgresiones);
        return "progresionesentrenamiento"; // El nombre del archivo Thymeleaf que muestra la lista de progresiones
    }

    /**
     * Maneja las solicitudes GET para obtener una progresión de entrenamiento específica por su ID.
     *
     * @param id Identificador de la progresión de entrenamiento a recuperar.
     * @return Una respuesta con el objeto ProgresionesEntrenamiento si se encuentra, o un estado de no encontrado si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgresionesEntrenamiento> getById(@PathVariable Integer id) {
        Optional<ProgresionesEntrenamiento> progresionEntrenamiento = service.encuentraPorId(id);
        if (progresionEntrenamiento.isPresent()) {
            return ResponseEntity.ok(progresionEntrenamiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Maneja las solicitudes POST para crear una nueva progresión de entrenamiento.
     *
     * @param progresionEntrenamiento Objeto ProgresionesEntrenamiento a ser creado.
     * @return El objeto ProgresionesEntrenamiento creado.
     * @throws Exception En caso de error durante la creación.
     */
    @PostMapping
    public ProgresionesEntrenamiento create(@RequestBody ProgresionesEntrenamiento progresionEntrenamiento) throws Exception {
        return service.guardar(progresionEntrenamiento);
    }

    /**
     * Maneja las solicitudes PUT para actualizar una progresión de entrenamiento existente por su ID.
     *
     * @param id Identificador de la progresión de entrenamiento a actualizar.
     * @param progresionEntrenamiento Objeto ProgresionesEntrenamiento con los datos actualizados.
     * @return Una respuesta con el objeto ProgresionesEntrenamiento actualizado si se encuentra, o un estado de no encontrado si no existe.
     * @throws Exception En caso de error durante la actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProgresionesEntrenamiento> update(@PathVariable Integer id, @RequestBody ProgresionesEntrenamiento progresionEntrenamiento) throws Exception {
        Optional<ProgresionesEntrenamiento> existingProgresionEntrenamiento = service.encuentraPorId(id);
        if (existingProgresionEntrenamiento.isPresent()) {
            progresionEntrenamiento.setId(id);
            return ResponseEntity.ok(service.guardar(progresionEntrenamiento));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Maneja las solicitudes DELETE para eliminar una progresión de entrenamiento por su ID.
     *
     * @param id Identificador de la progresión de entrenamiento a eliminar.
     * @return Una respuesta con estado sin contenido si la eliminación es exitosa, o un estado de no encontrado si no existe.
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
