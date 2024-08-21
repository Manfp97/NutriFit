package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.ClienteEntrenamiento;
import com.eoi.NutriFit.Servicios.ClienteEntrenamientoServi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <code>ClienteEntrenamientoController</code> es el controlador responsable de gestionar las operaciones
 * relacionadas con la entidad <code>ClienteEntrenamiento</code>. Proporciona métodos para listar,
 * mostrar, crear y eliminar registros de entrenamientos asociados a clientes.
 *
 * <p>Las principales funcionalidades incluyen:</p>
 * <ul>
 *     <li>Listar todos los clientes y sus entrenamientos asociados.</li>
 *     <li>Mostrar los detalles del entrenamiento de un cliente específico.</li>
 *     <li>Crear un nuevo registro de cliente y entrenamiento.</li>
 *     <li>Eliminar un registro existente de cliente y entrenamiento.</li>
 * </ul>
 *
 * <p>Esta clase está anotada con <code>@Slf4j</code> para permitir el uso de logging,
 * y con <code>@Controller</code> para indicar que es un controlador dentro del patrón MVC de Spring.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Slf4j
@Controller
@RequestMapping("/cliente-entrenamiento")
public class ClienteEntrenamientoController {

    @Autowired
    private ClienteEntrenamientoServi clienteEntrenamientoServi;

    /**
     * Muestra una lista de todos los registros de clientes asociados a entrenamientos.
     *
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>clientes-entrenamiento</code>, que mostrará la lista de clientes y sus entrenamientos.
     */
    @GetMapping
    public String listarClientesEntrenamiento(Model model) {
        List<ClienteEntrenamiento> listaClientesEntrenamiento = clienteEntrenamientoServi.buscarEntidades();
        model.addAttribute("clientesEntrenamiento", listaClientesEntrenamiento);
        return "clientes-entrenamiento";
    }

    /**
     * Muestra los detalles del entrenamiento de un cliente específico identificado por su ID.
     *
     * @param id El ID del registro <code>ClienteEntrenamiento</code> a mostrar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>cliente-entrenamiento-detalles</code> si el registro existe,
     *         o redirige a la lista de clientes-entrenamiento si no se encuentra el registro.
     */
    @GetMapping("/{id}")
    public String mostrarClienteEntrenamiento(@PathVariable Integer id, Model model) {
        Optional<ClienteEntrenamiento> clienteEntrenamientoOpt = clienteEntrenamientoServi.encuentraPorId(id);
        if (clienteEntrenamientoOpt.isPresent()) {
            ClienteEntrenamiento clienteEntrenamiento = clienteEntrenamientoOpt.get();
            model.addAttribute("clienteEntrenamiento", clienteEntrenamiento);
            // Aquí podrías agregar más atributos al modelo si es necesario
            return "cliente-entrenamiento-detalles";
        } else {
            return "redirect:/cliente-entrenamiento";
        }
    }

    /**
     * Crea un nuevo registro de cliente y entrenamiento en la base de datos.
     *
     * @param clienteEntrenamiento El objeto <code>ClienteEntrenamiento</code> que se va a crear.
     * @return Redirige a la lista de clientes-entrenamiento después de crear el nuevo registro.
     * @throws Exception Si ocurre algún error durante la creación del registro.
     */
    @PostMapping
    public String crearClienteEntrenamiento(@RequestBody ClienteEntrenamiento clienteEntrenamiento) throws Exception {
        clienteEntrenamientoServi.guardar(clienteEntrenamiento);
        return "redirect:/cliente-entrenamiento";
    }

    /**
     * Elimina un registro específico de cliente y entrenamiento identificado por su ID.
     *
     * @param id El ID del registro <code>ClienteEntrenamiento</code> a eliminar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return Redirige a la lista de clientes-entrenamiento después de eliminar el registro.
     */
    @DeleteMapping("/{id}")
    public String eliminarClienteEntrenamiento(@PathVariable Integer id, Model model) {
        clienteEntrenamientoServi.eliminarPorId(id);
        return "redirect:/cliente-entrenamiento";
    }
}
