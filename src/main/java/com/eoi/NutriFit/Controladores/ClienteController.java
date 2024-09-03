package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Cliente;
import com.eoi.NutriFit.Servicios.ClienteServi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <code>ClienteController</code> es el controlador que maneja todas las operaciones relacionadas con la entidad <code>Cliente</code>.
 * Este controlador proporciona métodos para listar, mostrar, crear y eliminar clientes.
 * Utiliza un modelo para pasar los datos a las vistas correspondientes.
 *
 * <p>Las operaciones principales incluyen:</p>
 * <ul>
 *     <li>Listar todos los clientes.</li>
 *     <li>Mostrar los detalles de un cliente específico.</li>
 *     <li>Crear un nuevo cliente.</li>
 *     <li>Eliminar un cliente existente.</li>
 * </ul>
 *
 * <p>Este controlador está anotado con <code>@Slf4j</code> para permitir el uso de logging,
 * y <code>@Controller</code> para indicarle a Spring que esta clase debe tratarse como un controlador MVC.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Slf4j
@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteServi clienteServi;

    /**
     * Muestra una lista de todos los clientes en la base de datos.
     *
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>clientes</code>, que mostrará la lista de clientes.
     */
    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> listaClientes = clienteServi.buscarEntidades();
        model.addAttribute("clientes", listaClientes);
        return "clientes";
    }

    /**
     * Muestra los detalles de un cliente específico identificado por su ID.
     *
     * @param id El ID del cliente a mostrar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>cliente-detalles</code> si el cliente existe,
     *         o redirige a la lista de clientes si no se encuentra el cliente.
     */
    @GetMapping("/{id}")
    public String mostrarCliente(@PathVariable Integer id, Model model) {
        Optional<Cliente> clienteOpt = clienteServi.encuentraPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            model.addAttribute("cliente", cliente);
            // Aquí podrías agregar más atributos al modelo si es necesario
            return "cliente-detalles";
        } else {
            return "redirect:/cliente";
        }
    }

    /**
     * Crea un nuevo cliente en la base de datos.
     *
     * @param cliente El objeto <code>Cliente</code> que se va a crear.
     * @return Redirige a la lista de clientes después de crear el nuevo cliente.
     * @throws Exception Si ocurre algún error durante la creación del cliente.
     */
    @PostMapping
    public String crearCliente(@RequestBody Cliente cliente) throws Exception {
        clienteServi.guardar(cliente);
        return "redirect:/cliente";
    }

    /**
     * Elimina un cliente específico identificado por su ID.
     *
     * @param id El ID del cliente a eliminar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return Redirige a la lista de clientes después de eliminar el cliente.
     */
    @DeleteMapping("/{id}")
    public String eliminarCliente(@PathVariable Integer id, Model model) {
        clienteServi.eliminarPorId(id);
        return "redirect:/cliente";
    }
}
