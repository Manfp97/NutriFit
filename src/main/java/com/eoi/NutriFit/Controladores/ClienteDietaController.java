package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.ClienteDieta;
import com.eoi.NutriFit.Servicios.ClienteDietaServi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <code>ClienteDietaController</code> es el controlador encargado de gestionar las operaciones
 * relacionadas con la entidad <code>ClienteDieta</code>. Proporciona métodos para listar,
 * mostrar, crear y eliminar registros de clientes asociados a dietas.
 *
 * <p>Las principales funcionalidades incluyen:</p>
 * <ul>
 *     <li>Listar todos los clientes y sus dietas asociadas.</li>
 *     <li>Mostrar los detalles de la dieta de un cliente específico.</li>
 *     <li>Crear un nuevo registro de cliente y dieta.</li>
 *     <li>Eliminar un registro existente de cliente y dieta.</li>
 * </ul>
 *
 * <p>Esta clase está anotada con <code>@Slf4j</code> para facilitar el uso de logging,
 * y con <code>@Controller</code> para indicar que es un controlador dentro del patrón MVC de Spring.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Slf4j
@Controller
@RequestMapping("/cliente-dieta")
public class ClienteDietaController {

    @Autowired
    private ClienteDietaServi clienteDietaServi;

    /**
     * Muestra una lista de todos los registros de clientes asociados a dietas.
     *
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>clientes-dieta</code>, que mostrará la lista de clientes y sus dietas.
     */
    @GetMapping
    public String listarClientesDieta(Model model) {
        List<ClienteDieta> listaClientesDieta = clienteDietaServi.buscarEntidades();
        model.addAttribute("clientesDieta", listaClientesDieta);
        return "clientes-dieta";
    }

    /**
     * Muestra los detalles de la dieta de un cliente específico identificado por su ID.
     *
     * @param id El ID del registro <code>ClienteDieta</code> a mostrar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>cliente-dieta-detalles</code> si el registro existe,
     *         o redirige a la lista de clientes-dieta si no se encuentra el registro.
     */
    @GetMapping("/{id}")
    public String mostrarClienteDieta(@PathVariable Integer id, Model model) {
        Optional<ClienteDieta> clienteDietaOpt = clienteDietaServi.encuentraPorId(id);
        if (clienteDietaOpt.isPresent()) {
            ClienteDieta clienteDieta = clienteDietaOpt.get();
            model.addAttribute("clienteDieta", clienteDieta);
            // Aquí podrías agregar más atributos al modelo si es necesario
            return "cliente-dieta-detalles";
        } else {
            return "redirect:/cliente-dieta";
        }
    }

    /**
     * Crea un nuevo registro de cliente y dieta en la base de datos.
     *
     * @param clienteDieta El objeto <code>ClienteDieta</code> que se va a crear.
     * @return Redirige a la lista de clientes-dieta después de crear el nuevo registro.
     * @throws Exception Si ocurre algún error durante la creación del registro.
     */
    @PostMapping
    public String crearClienteDieta(@RequestBody ClienteDieta clienteDieta) throws Exception {
        clienteDietaServi.guardar(clienteDieta);
        return "redirect:/cliente-dieta";
    }

    /**
     * Elimina un registro específico de cliente y dieta identificado por su ID.
     *
     * @param id El ID del registro <code>ClienteDieta</code> a eliminar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return Redirige a la lista de clientes-dieta después de eliminar el registro.
     */
    @DeleteMapping("/{id}")
    public String eliminarClienteDieta(@PathVariable Integer id, Model model) {
        clienteDietaServi.eliminarPorId(id);
        return "redirect:/cliente-dieta";
    }
}
