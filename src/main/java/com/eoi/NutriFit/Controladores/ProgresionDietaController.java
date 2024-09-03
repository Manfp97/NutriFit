package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.ProgresionDieta;
import com.eoi.NutriFit.Servicios.ProgresionDietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de la progresión de dieta.
 * Este controlador maneja las operaciones CRUD relacionadas con la entidad ProgresionDieta,
 * así como la visualización de las progresiones de dieta.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/progresionDieta")
public class ProgresionDietaController {

    @Autowired
    private ProgresionDietaService service;

    /**
     * Maneja las solicitudes GET para obtener todas las progresiones de dieta.
     *
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre de la vista que muestra todas las progresiones de dieta.
     */
    @GetMapping
    public String getAll(Model model) {
        List<ProgresionDieta> listaDetalles = service.buscarEntidades();
        model.addAttribute("progresionDietas", listaDetalles);
        return "progresionesdietas";
    }

    /**
     * Maneja las solicitudes GET para obtener una progresión de dieta específica por su ID.
     *
     * @param id Identificador de la progresión de dieta a recuperar.
     * @return Una respuesta con el objeto ProgresionDieta si se encuentra, o un estado de no encontrado si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgresionDieta> getById(@PathVariable Integer id) {
        Optional<ProgresionDieta> progresionDieta = service.encuentraPorId(id);
        if (progresionDieta.isPresent()) {
            return ResponseEntity.ok(progresionDieta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Maneja las solicitudes POST para crear una nueva progresión de dieta.
     *
     * @param progresionDieta Objeto ProgresionDieta a ser creado.
     * @return El objeto ProgresionDieta creado.
     * @throws Exception En caso de error durante la creación.
     */
    @PostMapping
    public ProgresionDieta create(@RequestBody ProgresionDieta progresionDieta) throws Exception {
        return service.guardar(progresionDieta);
    }

    /**
     * Maneja las solicitudes PUT para actualizar una progresión de dieta existente por su ID.
     *
     * @param id Identificador de la progresión de dieta a actualizar.
     * @param progresionDieta Objeto ProgresionDieta con los datos actualizados.
     * @return Una respuesta con el objeto ProgresionDieta actualizado si se encuentra, o un estado de no encontrado si no existe.
     * @throws Exception En caso de error durante la actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProgresionDieta> update(@PathVariable Integer id, @RequestBody ProgresionDieta progresionDieta) throws Exception {
        Optional<ProgresionDieta> existingProgresionDieta = service.encuentraPorId(id);
        if (existingProgresionDieta.isPresent()) {
            progresionDieta.setId(id);
            return ResponseEntity.ok(service.guardar(progresionDieta));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Maneja las solicitudes DELETE para eliminar una progresión de dieta por su ID.
     *
     * @param id Identificador de la progresión de dieta a eliminar.
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
