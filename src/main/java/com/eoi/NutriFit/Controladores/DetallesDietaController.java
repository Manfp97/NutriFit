package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.DetallesDieta;
import com.eoi.NutriFit.Servicios.DetallesDietaServi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * <code>DetallesDietaController</code> es el controlador encargado de gestionar las operaciones
 * relacionadas con los detalles de las dietas en la aplicación NutriFit.
 * Este controlador permite ver, editar, guardar y eliminar detalles específicos de una dieta.
 *
 * <p>Las principales funcionalidades de este controlador incluyen:</p>
 * <ul>
 *     <li>Mostrar los detalles de una dieta específica.</li>
 *     <li>Proporcionar un formulario para editar los detalles de una dieta.</li>
 *     <li>Guardar los cambios realizados en los detalles de una dieta.</li>
 *     <li>Eliminar los detalles de una dieta de la base de datos.</li>
 * </ul>
 *
 * <p>La clase está anotada con <code>@Slf4j</code> para habilitar el uso de logging,
 * y con <code>@Controller</code> para indicar que forma parte del patrón MVC en Spring.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Slf4j
@Controller
@RequestMapping("/detalles-dieta")
public class DetallesDietaController {

    @Autowired
    private DetallesDietaServi detallesDietaServi;

    /**
     * Muestra los detalles de una dieta específica identificada por su ID.
     *
     * @param id El ID de la dieta cuyos detalles se desean mostrar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>detalles-dieta</code> si los detalles de la dieta existen,
     *         o redirige a la lista de dietas si no se encuentran los detalles.
     */
    @GetMapping("/{id}")
    public String mostrarDetallesDieta(@PathVariable Integer id, Model model) {
        Optional<DetallesDieta> detallesDietaOpt = detallesDietaServi.encuentraPorId(id);
        if (detallesDietaOpt.isPresent()) {
            DetallesDieta detallesDieta = detallesDietaOpt.get();
            model.addAttribute("detallesDieta", detallesDieta);
            return "detalles-dieta";
        } else {
            return "redirect:/dieta";
        }
    }

    /**
     * Muestra el formulario para editar los detalles de una dieta específica identificada por su ID.
     *
     * @param id El ID de la dieta cuyos detalles se desean editar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista <code>formulario-editar-detalles-dieta</code> si los detalles de la dieta existen,
     *         o redirige a la lista de dietas si no se encuentran los detalles.
     */
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Optional<DetallesDieta> detallesDietaOpt = detallesDietaServi.encuentraPorId(id);
        if (detallesDietaOpt.isPresent()) {
            DetallesDieta detallesDieta = detallesDietaOpt.get();
            model.addAttribute("detallesDieta", detallesDieta);
            return "formulario-editar-detalles-dieta";
        } else {
            return "redirect:/dieta";
        }
    }

    /**
     * Guarda los cambios realizados en los detalles de una dieta específica.
     *
     * @param id El ID de la dieta cuyos detalles se están editando.
     * @param detallesDieta El objeto <code>DetallesDieta</code> que contiene los detalles actualizados.
     * @return Redirige a la vista de detalles de la dieta después de guardar los cambios.
     * @throws Exception Si ocurre algún error durante la operación de guardado.
     */
    @PostMapping("/{id}/guardar")
    public String guardarEdicionDetallesDieta(@PathVariable Integer id, @ModelAttribute DetallesDieta detallesDieta) throws Exception {
        detallesDieta.setId(id); // Aseguramos que el ID de los detalles de la dieta sea el mismo que se está editando
        detallesDietaServi.guardar(detallesDieta);
        return "redirect:/detalles-dieta/" + id;
    }

    /**
     * Elimina los detalles de una dieta específica identificada por su ID de la base de datos.
     *
     * @param id El ID de la dieta cuyos detalles se desean eliminar.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return Redirige a la lista de dietas después de eliminar los detalles.
     */
    @DeleteMapping("/{id}")
    public String eliminarDetallesDieta(@PathVariable Integer id, Model model) {
        detallesDietaServi.eliminarPorId(id);
        return "redirect:/dieta";
    }
}
