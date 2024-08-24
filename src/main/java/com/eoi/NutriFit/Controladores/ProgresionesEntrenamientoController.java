package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.ProgresionesEntrenamiento;
import com.eoi.NutriFit.Servicios.ProgresionesEntrenamientoServi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/progresionesEntrenamientos")
public class ProgresionesEntrenamientoController {

    private final ProgresionesEntrenamientoServi service;

    public ProgresionesEntrenamientoController(ProgresionesEntrenamientoServi service) {
        this.service = service;
    }


    @GetMapping("/listar")
    @ResponseBody
    public List<ProgresionesEntrenamiento> listarProgresiones() {
        return service.buscarEntidades();
    }

    @GetMapping
    public String mostrarProgresiones(Model model) {
        List<ProgresionesEntrenamiento> progresiones = service.buscarEntidades();
        model.addAttribute("progresiones", progresiones);
        return "progresiones"; // Make sure this matches your HTML file name
    }

    @PostMapping("/guardar")
    public String guardarProgresion(@ModelAttribute("progresion") ProgresionesEntrenamiento progresion, Model model) {
        try {
            service.guardar(progresion);
            return "redirect:/progresionesEntrenamientos"; // Redirige a la lista de progresiones
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la progresión");
            return "progresiones"; // Devuelve la misma vista con el mensaje de error
        }
    }

    @GetMapping("/editar/{id}")
    public String editarProgresion(@PathVariable Integer id, Model model) {
        Optional<ProgresionesEntrenamiento> progresion = service.encuentraPorId(id);
        if (progresion.isPresent()) {
            model.addAttribute("progresion", progresion.get());
            return "editarProgresion"; // Nombre de la vista para editar
        } else {
            return "redirect:/progresionesEntrenamientos";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProgresion(@PathVariable Integer id, @ModelAttribute("progresion") ProgresionesEntrenamiento progresion) {
        try {
            progresion.setId(id);
            service.guardar(progresion);
            return "redirect:/progresionesEntrenamientos";
        } catch (Exception e) {
            // Manejo de la excepción - puedes registrar el error y devolver un mensaje al usuario
            e.printStackTrace();
            return "redirect:/error"; // o redirigir a una página de error
        }
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarProgresion(@PathVariable Integer id) {
        service.eliminarPorId(id);
        return "redirect:/progresionesEntrenamientos";
    }
}
