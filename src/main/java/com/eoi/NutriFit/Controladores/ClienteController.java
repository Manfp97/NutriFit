<<<<<<<< HEAD:src/main/java/com/eoi/NutriFit/Controladores/ClienteController.java
package com.eoi.NutriFit.Controladores;
========
package com.eoi.NutriFit.Controller;
>>>>>>>> origin/francisco:NutriFit/src/main/java/com/eoi/NutriFit/Controller/ClienteController.java

import com.eoi.NutriFit.Entidades.Cliente;
import com.eoi.NutriFit.Servicios.ClienteServi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteServi clienteServi;

    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> listaClientes = clienteServi.buscarEntidades();
        model.addAttribute("clientes", listaClientes);
        return "clientes";
    }

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

    @PostMapping
    public String crearCliente(@RequestBody Cliente cliente) throws Exception {
        clienteServi.guardar(cliente);
        return "redirect:/cliente";
    }

    @DeleteMapping("/{id}")
    public String eliminarCliente(@PathVariable Integer id, Model model) {
        clienteServi.eliminarPorId(id);
        return "redirect:/cliente";
    }
}
