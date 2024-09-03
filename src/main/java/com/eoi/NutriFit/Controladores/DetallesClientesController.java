package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Cliente;
import com.eoi.NutriFit.Servicios.ClienteServi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * <code>DetallesClientesController</code> es el controlador responsable de gestionar las operaciones
 * relacionadas con los detalles de los clientes en la aplicación NutriFit. Este controlador permite
 * ver, editar, guardar y eliminar detalles de un cliente específico.
 *
 * <p>Las principales funcionalidades incluyen:</p>
 * <ul>
 *     <li>Mostrar los detalles de un cliente.</li>
 *     <li>Proporcionar un formulario para editar los detalles de un cliente.</li>
 *     <li>Guardar los cambios realizados en los detalles de un cliente.</li>
 *     <li>Eliminar un cliente de la base de datos.</li>
 * </ul>
 *
 * <p>Esta clase está anotada con <code>@Slf4j</code> para permitir el uso de logging,
 * y con <code>@Controller</code> para indicar que es un controlador dentro del patrón MVC de Spring.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Slf4j
@Controller
@RequestMapping("/detalles-clientes")
public class DetallesClientesController {

    @Autowired
    private ClienteServi clienteServi;

    /**
     * Muestra los detalles de un cliente específico identificado por su ID.
     *
     * @param id El ID del cliente cuyos detalles se desean mostrar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>detalles-cliente</code> si el cliente existe,
     *         o redirige a la lista de clientes si no se encuentra el cliente.
     */
    @GetMapping("/{id}")
    public String mostrarDetallesCliente(@PathVariable Integer id, Model model) {
        Optional<Cliente> clienteOpt = clienteServi.encuentraPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            model.addAttribute("cliente", cliente);
            return "detalles-cliente";
        } else {
            return "redirect:/cliente";
        }
    }

    /**
     * Muestra el formulario para editar los detalles de un cliente específico identificado por su ID.
     *
     * @param id El ID del cliente cuyos detalles se desean editar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>formulario-editar-cliente</code> si el cliente existe,
     *         o redirige a la lista de clientes si no se encuentra el cliente.
     */
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Optional<Cliente> clienteOpt = clienteServi.encuentraPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            model.addAttribute("cliente", cliente);
            return "formulario-editar-cliente";
        } else {
            return "redirect:/cliente";
        }
    }

    /**
     * Guarda los cambios realizados en los detalles de un cliente específico.
     *
     * @param id El ID del cliente que se está editando.
     * @param cliente El objeto <code>Cliente</code> que contiene los detalles actualizados.
     * @return Redirige a la vista de detalles del cliente después de guardar los cambios.
     * @throws Exception Si ocurre algún error durante la operación de guardado.
     */
    @PostMapping("/{id}/guardar")
    public String guardarEdicionCliente(@PathVariable Integer id, @ModelAttribute Cliente cliente) throws Exception {
        cliente.setId(id); // Aseguramos que el ID del cliente sea el mismo que se está editando
        clienteServi.guardar(cliente);
        return "redirect:/detalles-clientes/" + id;
    }

    /**
     * Elimina un cliente específico identificado por su ID de la base de datos.
     *
     * @param id El ID del cliente que se desea eliminar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return Redirige a la lista de clientes después de eliminar el cliente.
     */
    @DeleteMapping("/{id}")
    public String eliminarCliente(@PathVariable Integer id, Model model) {
        clienteServi.eliminarPorId(id);
        return "redirect:/cliente";
    }
}
