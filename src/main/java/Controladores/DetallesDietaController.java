package Controladores;

import com.eoi.NutriFit.Entidades.DetallesDieta;
import com.eoi.NutriFit.Servicios.DetallesDietaServi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/detalles-dieta")
public class DetallesDietaController {

    @Autowired
    private DetallesDietaServi detallesDietaServi;

    @GetMapping("/{id}")
    public String mostrarDetallesDieta(@PathVariable Integer id, Model model) {
        Optional<DetallesDieta> detallesDietaOpt = detallesDietaServi.encuentraPorId(id);
        if (detallesDietaOpt.isPresent()) {
            DetallesDieta detallesDieta = detallesDietaOpt.get();
            model.addAttribute("detallesDieta", detallesDieta);
            // Aquí podrías agregar más atributos al modelo si es necesario
            return "detalles-dieta";
        } else {
            return "redirect:/dieta";
        }
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Optional<DetallesDieta> detallesDietaOpt = detallesDietaServi.encuentraPorId(id);
        if (detallesDietaOpt.isPresent()) {
            DetallesDieta detallesDieta = detallesDietaOpt.get();
            model.addAttribute("detallesDieta", detallesDieta);
            // Aquí podrías agregar más atributos al modelo si es necesario
            return "formulario-editar-detalles-dieta";
        } else {
            return "redirect:/dieta";
        }
    }

    @PostMapping("/{id}/guardar")
    public String guardarEdicionDetallesDieta(@PathVariable Integer id, @ModelAttribute DetallesDieta detallesDieta) throws Exception {
        detallesDieta.setId(id); // aseguramos que el ID de los detalles de la dieta sea el mismo que se está editando
        detallesDietaServi.guardar(detallesDieta);
        return "redirect:/detalles-dieta/" + id;
    }

    @DeleteMapping("/{id}")
    public String eliminarDetallesDieta(@PathVariable Integer id, Model model) {
        detallesDietaServi.eliminarPorId(id);
        return "redirect:/dieta";
    }
}
