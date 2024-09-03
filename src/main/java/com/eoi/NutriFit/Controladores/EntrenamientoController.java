package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import com.eoi.NutriFit.Repositorios.EntrenamientoRepo;
import com.eoi.NutriFit.Servicios.EntrenamientoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controlador para manejar las operaciones relacionadas con los entrenamientos en la aplicación NutriFit.
 * Este controlador permite listar, crear, actualizar y eliminar entrenamientos, así como ver los detalles de cada entrenamiento.
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/entrenamiento")
public class EntrenamientoController {

    @Autowired
    private EntrenamientoService service;
    @Autowired
    private EntrenamientoRepo entrenamientoRepo;

    /**
     * Constructor para inyectar las dependencias necesarias.
     *
     * @param service           Servicio de manejo de entrenamientos.
     * @param entrenamientoRepo Repositorio de acceso a los datos de entrenamientos.
     */
    @Autowired
    public EntrenamientoController(EntrenamientoService service, EntrenamientoRepo entrenamientoRepo) {
        this.service = service;
        this.entrenamientoRepo = entrenamientoRepo;
    }

    /**
     * Maneja las solicitudes GET para listar todos los entrenamientos con paginación y filtrado opcional por dificultad y categoría.
     *
     * @param page      Número de la página actual.
     * @param size      Tamaño de la página.
     * @param dificultad Nivel de dificultad para filtrar los entrenamientos.
     * @param categoria Categoría para filtrar los entrenamientos.
     * @param model     Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "entrenamiento" que muestra la lista de entrenamientos.
     */
    @GetMapping
    public String listAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "9") int size,
            @RequestParam(value = "dificultad", required = false) String dificultad,
            @RequestParam(required = false, defaultValue = "cardio") String categoria,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Entrenamiento> entrenamientosPage;

        if (dificultad != null && !dificultad.isEmpty()) {
            if (categoria != null && !categoria.isEmpty()) {
                entrenamientosPage = entrenamientoRepo.findByCategoriaAndDificultad(categoria, dificultad, pageable);
            } else {
                entrenamientosPage = entrenamientoRepo.findByDificultad(dificultad, pageable);
            }
        } else {
            if (categoria != null && !categoria.isEmpty()) {
                entrenamientosPage = entrenamientoRepo.findByCategoria(categoria, pageable);
            } else {
                entrenamientosPage = entrenamientoRepo.findAll(pageable);
            }
        }

        List<Integer> pageNumbers = IntStream.rangeClosed(1, entrenamientosPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("pagina", entrenamientosPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("entrenamientos", entrenamientosPage.getContent());
        model.addAttribute("categoria", categoria);
        model.addAttribute("dificultad", dificultad);

        return "entrenamiento";
    }

    /**
     * Maneja las solicitudes GET para listar todos los entrenamientos con edición habilitada, solo para usuarios con roles específicos.
     *
     * @param page  Número de la página actual.
     * @param size  Tamaño de la página.
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "listaentrenamientoseditable" que muestra la lista editable de entrenamientos.
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String listAllEditable(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Entrenamiento> entrenamientoPage = entrenamientoRepo.findAll(pageable);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, entrenamientoPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("entrenamientoPage", entrenamientoPage);
        model.addAttribute("pageNumbers", pageNumbers);
        return "listaentrenamientoseditable";
    }

    /**
     * Maneja las solicitudes GET para obtener los detalles de un entrenamiento específico por su ID.
     *
     * @param id    ID del entrenamiento.
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "detalleentrenamiento" si el entrenamiento es encontrado; de lo contrario, redirige a la página 404.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        Optional<Entrenamiento> entrenamiento = service.encuentraPorId(id);

        if (entrenamiento.isPresent()) {
            model.addAttribute("entrenamiento", entrenamiento.get());
            return "detalleentrenamiento";
        } else {
            return "redirect:/404";
        }
    }

    /**
     * Maneja las solicitudes GET para ver más detalles de un entrenamiento específico por su ID.
     *
     * @param id    ID del entrenamiento.
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "vermasentrenamientos" si el entrenamiento es encontrado; de lo contrario, redirige a la página 404.
     */
    @GetMapping("/vermas/{id}")
    public String getByIdVerMas(@PathVariable Integer id, Model model) {
        Optional<Entrenamiento> entrenamiento = service.encuentraPorId(id);

        if (entrenamiento.isPresent()) {
            model.addAttribute("entrenamiento", entrenamiento.get());
            return "vermasentrenamientos";
        } else {
            return "redirect:/404";
        }
    }

    /**
     * Maneja las solicitudes POST para actualizar un entrenamiento existente por su ID.
     * Solo accesible para usuarios con roles específicos.
     *
     * @param id           ID del entrenamiento a actualizar.
     * @param entrenamiento Objeto Entrenamiento con los nuevos datos.
     * @param model        Modelo para pasar los datos a la vista.
     * @return Redirige a la lista de entrenamientos si la actualización es exitosa, de lo contrario, muestra un mensaje de error.
     */
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String update(@PathVariable Integer id, @ModelAttribute Entrenamiento entrenamiento, Model model) {
        try {
            Optional<Entrenamiento> existingEntrenamiento = service.encuentraPorId(id);
            if (existingEntrenamiento.isPresent()) {
                Entrenamiento updatedEntrenamiento = existingEntrenamiento.get();
                updatedEntrenamiento.setNombre(entrenamiento.getNombre());
                updatedEntrenamiento.setObjetivos(entrenamiento.getObjetivos());
                updatedEntrenamiento.setCategoria(entrenamiento.getCategoria());
                // Actualizar otros campos necesarios si es necesario

                service.guardar(updatedEntrenamiento);
                model.addAttribute("mensaje", "Entrenamiento actualizado con éxito");
                return "redirect:/entrenamiento";
            } else {
                model.addAttribute("mensaje", "Entrenamiento no encontrado");
                return "redirect:/entrenamiento";
            }
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar entrenamiento: " + e.getMessage());
            return "redirect:/entrenamiento";
        }
    }

    /**
     * Maneja las solicitudes POST para eliminar un entrenamiento por su ID.
     * Solo accesible para usuarios con el rol de administrador.
     *
     * @param id ID del entrenamiento a eliminar.
     * @return Redirige a la lista de entrenamientos si la eliminación es exitosa; de lo contrario, redirige a la página 404.
     */
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Integer id) {
        try {
            service.eliminarPorId(id);
            return "redirect:/entrenamiento";
        } catch (EntityNotFoundException e) {
            return "redirect:/404";
        }
    }

    /**
     * Maneja las solicitudes GET para mostrar el formulario de creación de un nuevo entrenamiento.
     * Solo accesible para usuarios con roles específicos.
     *
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "crearentrenamiento" que muestra el formulario de creación.
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("entrenamiento", new Entrenamiento());
        return "crearentrenamiento";
    }

    /**
     * Maneja las solicitudes POST para crear un nuevo entrenamiento.
     * Solo accesible para usuarios con roles específicos.
     *
     * @param entrenamiento Objeto Entrenamiento con los datos a guardar.
     * @param model         Modelo para pasar los datos a la vista.
     * @return Redirige a la página de creación si el entrenamiento se crea con éxito; de lo contrario, redirige a la página 404.
     */
    @PostMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String crear(@ModelAttribute("entrenamiento") Entrenamiento entrenamiento, Model model) {
        try {
            service.guardar(entrenamiento);
            model.addAttribute("mensaje", "Entrenamiento creado con éxito");
            return "redirect:/entrenamiento/nuevo";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al crear entrenamiento");
            return "redirect:/404";
        }
    }
}
