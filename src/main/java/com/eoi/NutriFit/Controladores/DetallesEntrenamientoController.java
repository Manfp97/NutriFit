package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.DetallesEntrenamiento;
import com.eoi.NutriFit.Servicios.DetallesEntrenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <code>DetallesEntrenamientoController</code> es el controlador REST encargado de gestionar las operaciones
 * relacionadas con los detalles de entrenamiento en la aplicación NutriFit.
 * Este controlador proporciona endpoints para realizar operaciones CRUD sobre los detalles de entrenamiento.
 *
 * <p>Las principales funcionalidades de este controlador incluyen:</p>
 * <ul>
 *     <li>Obtener todos los detalles de entrenamiento.</li>
 *     <li>Obtener detalles de entrenamiento por ID.</li>
 *     <li>Crear un nuevo detalle de entrenamiento.</li>
 *     <li>Actualizar un detalle de entrenamiento existente.</li>
 *     <li>Eliminar un detalle de entrenamiento por ID.</li>
 * </ul>
 *
 * <p>La clase está anotada con <code>@RestController</code> para definir un controlador de RESTful web services,
 * y con <code>@RequestMapping</code> para definir la ruta base para todas las solicitudes.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@RestController
@RequestMapping("/detallesEntrenamiento")
public class DetallesEntrenamientoController {

    @Autowired
    private DetallesEntrenamientoService service;

    /**
     * Obtiene todos los detalles de entrenamiento y los agrega al modelo para la vista.
     *
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>detallesEntrenamientos</code> con la lista de detalles de entrenamiento.
     */
    @GetMapping
    public String getAll(Model model) {
        List<DetallesEntrenamiento> listaDetalles = service.buscarEntidades();
        model.addAttribute("detallesEntrenamientos", listaDetalles);
        return "detallesEntrenamientos";
    }

    /**
     * Obtiene un detalle de entrenamiento específico identificado por su ID.
     *
     * @param id El ID del detalle de entrenamiento a obtener.
     * @return Una respuesta HTTP con el detalle de entrenamiento si se encuentra, o un estado <code>404 Not Found</code>
     *         si no se encuentra el detalle.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetallesEntrenamiento> getById(@PathVariable Integer id) {
        Optional<DetallesEntrenamiento> detallesEntrenamiento = service.encuentraPorId(id);
        if (detallesEntrenamiento.isPresent()) {
            return ResponseEntity.ok(detallesEntrenamiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo detalle de entrenamiento.
     *
     * @param detallesEntrenamiento El objeto <code>DetallesEntrenamiento</code> a crear.
     * @return El detalle de entrenamiento creado.
     * @throws Exception Si ocurre algún error durante el proceso de creación.
     */
    @PostMapping
    public DetallesEntrenamiento create(@RequestBody DetallesEntrenamiento detallesEntrenamiento) throws Exception {
        return service.guardar(detallesEntrenamiento);
    }

    /**
     * Actualiza un detalle de entrenamiento existente identificado por su ID.
     *
     * @param id El ID del detalle de entrenamiento a actualizar.
     * @param detallesEntrenamiento El objeto <code>DetallesEntrenamiento</code> con los nuevos datos.
     * @return Una respuesta HTTP con el detalle de entrenamiento actualizado si se encuentra, o un estado <code>404 Not Found</code>
     *         si no se encuentra el detalle.
     * @throws Exception Si ocurre algún error durante el proceso de actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetallesEntrenamiento> update(@PathVariable Integer id, @RequestBody DetallesEntrenamiento detallesEntrenamiento) throws Exception {
        Optional<DetallesEntrenamiento> existingDetallesEntrenamiento = service.encuentraPorId(id);
        if (existingDetallesEntrenamiento.isPresent()) {
            detallesEntrenamiento.setId(id);
            return ResponseEntity.ok(service.guardar(detallesEntrenamiento));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un detalle de entrenamiento específico identificado por su ID.
     *
     * @param id El ID del detalle de entrenamiento a eliminar.
     * @return Una respuesta HTTP con estado <code>204 No Content</code> si el detalle fue eliminado, o un estado <code>404 Not Found</code>
     *         si no se encuentra el detalle.
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
