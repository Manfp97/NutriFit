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

@Slf4j
@Controller
@RequestMapping("/cliente-dieta")
public class ClienteDietaController {

    @Autowired
    private ClienteDietaServi clienteDietaServi;

    @GetMapping
    public String listarClientesDieta(Model model) {
        List<ClienteDieta> listaClientesDieta = clienteDietaServi.buscarEntidades();
        model.addAttribute("clientesDieta", listaClientesDieta);
        return "clientes-dieta";
    }

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

    @PostMapping
    public String crearClienteDieta(@RequestBody ClienteDieta clienteDieta) throws Exception {
        clienteDietaServi.guardar(clienteDieta);
        return "redirect:/cliente-dieta";
    }

    @DeleteMapping("/{id}")
    public String eliminarClienteDieta(@PathVariable Integer id, Model model) {
        clienteDietaServi.eliminarPorId(id);
        return "redirect:/cliente-dieta";
    }
}