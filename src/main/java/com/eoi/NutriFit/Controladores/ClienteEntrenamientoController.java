
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

@Slf4j
@Controller
@RequestMapping("/cliente-entrenamiento")
public class ClienteEntrenamientoController {

    @Autowired
    private ClienteEntrenamientoServi clienteEntrenamientoServi;

    @GetMapping
    public String listarClientesEntrenamiento(Model model) {
        List<ClienteEntrenamiento> listaClientesEntrenamiento = clienteEntrenamientoServi.buscarEntidades();
        model.addAttribute("clientesEntrenamiento", listaClientesEntrenamiento);
        return "clientes-entrenamiento";
    }

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

    @PostMapping
    public String crearClienteEntrenamiento(@RequestBody ClienteEntrenamiento clienteEntrenamiento) throws Exception {
        clienteEntrenamientoServi.guardar(clienteEntrenamiento);
        return "redirect:/cliente-entrenamiento";
    }

    @DeleteMapping("/{id}")
    public String eliminarClienteEntrenamiento(@PathVariable Integer id, Model model) {
        clienteEntrenamientoServi.eliminarPorId(id);
        return "redirect:/cliente-entrenamiento";
    }
}
