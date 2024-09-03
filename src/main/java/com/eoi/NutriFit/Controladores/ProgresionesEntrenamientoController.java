package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.ProgresionesEntrenamiento;
import com.eoi.NutriFit.Servicios.ProgresionesEntrenamientoServi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para gestionar las progresiones de entrenamiento en la aplicación NutriFit.
 * Proporciona métodos para listar, guardar, editar, actualizar y eliminar progresiones de entrenamiento.
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/progresionesEntrenamientos")
public class ProgresionesEntrenamientoController {

    private final ProgresionesEntrenamientoServi service;

    /**
     * Constructor que inyecta el servicio de ProgresionesEntrenamiento.
     *
     * @param service El servicio de ProgresionesEntrenamiento utilizado para interactuar con la capa de negocio.
     */
    public ProgresionesEntrenamientoController(ProgresionesEntrenamientoServi service) {
        this.service = service;
    }

    /**
     * Devuelve una lista de todas las progresiones de entrenamiento.
     *
     * @return Una lista de objetos ProgresionesEntrenamiento.
     */
    @GetMapping("/listar")
    @ResponseBody
    public List<ProgresionesEntrenamiento> listarProgresiones() {
        return service.buscarEntidades();
    }

    /**
     * Muestra la página con la lista de progresiones de entrenamiento.
     *
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista que muestra las progresiones.
     */
    @GetMapping
    public String mostrarProgresiones(Model model) {
        List<ProgresionesEntrenamiento> progresiones = service.buscarEntidades();
        model.addAttribute("progresiones", progresiones);
        return "progresiones"; // Asegúrate de que coincide con el nombre de tu archivo HTML
    }

    /**
     * Guarda una nueva progresión de entrenamiento en la base de datos.
     * En caso de error, devuelve la vista con un mensaje de error.
     *
     * @param progresion Objeto ProgresionesEntrenamiento que se va a guardar.
     * @param model      Modelo para pasar datos a la vista.
     * @return Redirige a la lista de progresiones de entrenamiento o muestra la misma vista en caso de error.
     */
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

    /**
     * Muestra la vista para editar una progresión de entrenamiento específica.
     *
     * @param id    ID de la progresión que se va a editar.
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la vista de edición si la progresión existe, de lo contrario redirige a la lista de progresiones.
     */
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

    /**
     * Actualiza una progresión de entrenamiento existente en la base de datos.
     *
     * @param id        ID de la progresión a actualizar.
     * @param progresion Objeto ProgresionesEntrenamiento con los datos actualizados.
     * @return Redirige a la lista de progresiones de entrenamiento o a una página de error en caso de excepción.
     */
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

    /**
     * Elimina una progresión de entrenamiento específica de la base de datos.
     *
     * @param id ID de la progresión que se va a eliminar.
     * @return Redirige a la lista de progresiones de entrenamiento después de la eliminación.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarProgresion(@PathVariable Integer id) {
        service.eliminarPorId(id);
        return "redirect:/progresionesEntrenamientos";
    }
}
