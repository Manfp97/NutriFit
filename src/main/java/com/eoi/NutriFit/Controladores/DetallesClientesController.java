
package com.eoi.NutriFit.Controladores;


import com.eoi.NutriFit.Entidades.Cliente;
import com.eoi.NutriFit.Servicios.ClienteServi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/detalles-clientes")
public class DetallesClientesController {

    @Autowired
    private ClienteServi clienteServi;

    @GetMapping("/{id}")
    public String mostrarDetallesCliente(@PathVariable Integer id, Model model) {
        Optional<Cliente> clienteOpt = clienteServi.encuentraPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            model.addAttribute("cliente", cliente);
            // Aquí podrías agregar más atributos al modelo si es necesario
            return "detalles-cliente";
        } else {
            return "redirect:/cliente";
        }
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Optional<Cliente> clienteOpt = clienteServi.encuentraPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            model.addAttribute("cliente", cliente);
            // Aquí podrías agregar más atributos al modelo si es necesario
            return "formulario-editar-cliente";
        } else {
            return "redirect:/cliente";
        }
    }

    @PostMapping("/{id}/guardar")
    public String guardarEdicionCliente(@PathVariable Integer id, @ModelAttribute Cliente cliente) throws Exception {
        cliente.setId(id); // aseguramos que el ID del cliente sea el mismo que se está editando
        clienteServi.guardar(cliente);
        return "redirect:/detalles-clientes/" + id;
    }

    @DeleteMapping("/{id}")
    public String eliminarCliente(@PathVariable Integer id, Model model) {
        clienteServi.eliminarPorId(id);
        return "redirect:/cliente";
    }
}
