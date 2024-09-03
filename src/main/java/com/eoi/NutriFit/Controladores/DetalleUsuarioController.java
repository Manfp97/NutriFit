package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Servicios.DetalleUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <code>DetalleUsuarioController</code> es un controlador REST que gestiona las operaciones CRUD (Crear, Leer,
 * Actualizar, Eliminar) para los detalles de usuario en la aplicación NutriFit.
 * Proporciona endpoints para interactuar con los detalles de usuario a través de HTTP.
 *
 * <p>Esta clase está anotada con <code>@RestController</code> para indicar que es un controlador de RESTful web services
 * y <code>@RequestMapping</code> para definir la ruta base para todas las solicitudes.</p>
 *
 * <p>Las operaciones soportadas por este controlador incluyen:</p>
 * <ul>
 *     <li>Obtener todos los detalles de usuario.</li>
 *     <li>Obtener un detalle de usuario específico por su ID.</li>
 *     <li>Crear un nuevo detalle de usuario.</li>
 *     <li>Actualizar un detalle de usuario existente.</li>
 *     <li>Eliminar un detalle de usuario por ID.</li>
 * </ul>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@RestController
@RequestMapping("/detallesUsuario")
public class DetalleUsuarioController {

    @Autowired
    private DetalleUsuarioService service;

    /**
     * Obtiene todos los detalles de usuario y los agrega al modelo para su visualización.
     *
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>detalleUsuarios</code> con la lista de detalles de usuario.
     */
    @GetMapping
    public String getAll(Model model) {
        List<DetalleUsuario> listaDetalles = service.buscarEntidades();
        model.addAttribute("detalleUsuarios", listaDetalles);
        return "detalleUsuarios";
    }

    /**
     * Obtiene un detalle de usuario específico identificado por su ID.
     *
     * @param id El ID del detalle de usuario a obtener.
     * @return Una respuesta HTTP con el detalle de usuario si se encuentra, o un estado <code>404 Not Found</code>
     *         si no se encuentra el detalle.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetalleUsuario> getById(@PathVariable Integer id) {
        Optional<DetalleUsuario> detalleUsuario = service.encuentraPorId(id);
        if (detalleUsuario.isPresent()) {
            return ResponseEntity.ok(detalleUsuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo detalle de usuario.
     *
     * @param detalleUsuario El objeto <code>DetalleUsuario</code> que se desea crear.
     * @return El detalle de usuario creado.
     * @throws Exception Si ocurre algún error durante el proceso de creación.
     */
    @PostMapping
    public DetalleUsuario create(@RequestBody DetalleUsuario detalleUsuario) throws Exception {
        return service.guardar(detalleUsuario);
    }

    /**
     * Actualiza un detalle de usuario existente identificado por su ID.
     *
     * @param id El ID del detalle de usuario a actualizar.
     * @param detalleUsuario El objeto <code>DetalleUsuario</code> con los nuevos datos.
     * @return Una respuesta HTTP con el detalle de usuario actualizado si se encuentra, o un estado <code>404 Not Found</code>
     *         si no se encuentra el detalle.
     * @throws Exception Si ocurre algún error durante el proceso de actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetalleUsuario> update(@PathVariable Integer id, @RequestBody DetalleUsuario detalleUsuario) throws Exception {
        Optional<DetalleUsuario> existingDetalleUsuario = service.encuentraPorId(id);
        if (existingDetalleUsuario.isPresent()) {
            detalleUsuario.setId(id);
            return ResponseEntity.ok(service.guardar(detalleUsuario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un detalle de usuario específico identificado por su ID.
     *
     * @param id El ID del detalle de usuario a eliminar.
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
