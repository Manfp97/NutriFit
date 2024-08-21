package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Dieta;
import com.eoi.NutriFit.Repositorios.DietaRepo;
import com.eoi.NutriFit.Servicios.DietaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controlador para manejar las operaciones relacionadas con las dietas.
 * Proporciona endpoints para listar, crear, actualizar y eliminar dietas.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/dietaUsuario")
public class DietaController {

    private final DietaService service;
    private final DietaRepo dietaRepo;

    @Autowired
    public DietaController(DietaService service, DietaRepo dietaRepo) {
        this.service = service;
        this.dietaRepo = dietaRepo;
    }

    /**
     * Lista todas las dietas con paginación y filtrado por categoría.
     *
     * @param page       Número de página para la paginación.
     * @param size       Tamaño de la página para la paginación.
     * @param categoria  Categoría para filtrar las dietas.
     * @param model      Modelo para pasar datos a la vista.
     * @return Vista con la lista de dietas.
     */
    @GetMapping
    public String listAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "9") int size,
            @RequestParam(required = false, defaultValue = "aumentodemasamuscular") String categoria,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Dieta> dietasPage;

        // Verifica si la categoría no es nula ni vacía
        if (categoria != null && !categoria.isEmpty()) {
            dietasPage = dietaRepo.findByCategoria(categoria, pageable);
        } else {
            dietasPage = dietaRepo.findAll(pageable);
        }
        if (dietasPage.isEmpty()) {
            return "dietanotfound";
        } else {
            // Crea la lista de números de página
            List<Integer> pageNumbers = IntStream.rangeClosed(1, dietasPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            // Añade los atributos al modelo
            model.addAttribute("pagina", dietasPage);
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("dietas", dietasPage.getContent());
            model.addAttribute("categoria", categoria);

            return "dieta";
        }
    }

    /**
     * Lista todas las dietas con paginación, accesible solo para roles ADMIN y EMPLEADO.
     *
     * @param page Número de página para la paginación.
     * @param size Tamaño de la página para la paginación.
     * @param model Modelo para pasar datos a la vista.
     * @return Vista con la lista de dietas editables.
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String listAllEditable(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Dieta> dietaPage = dietaRepo.findAll(pageable);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, dietaPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("dietaPage", dietaPage);
        model.addAttribute("pageNumbers", pageNumbers);
        return "listadietaseditable"; // El nombre del archivo Thymeleaf que mostraría la tabla
    }

    /**
     * Obtiene los detalles de una dieta específica por ID.
     *
     * @param id    ID de la dieta a obtener.
     * @param model Modelo para pasar datos a la vista.
     * @return Vista con los detalles de la dieta o redirige a un error 404 si no se encuentra.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id , Model model) {
        Optional<Dieta> dieta = service.encuentraPorId(id);

        if (dieta.isPresent()) {
            model.addAttribute("dieta", dieta.get());
            return "detalledieta";
        } else {
            return "redirect:/404";
        }
    }

    /**
     * Actualiza los detalles de una dieta existente por ID.
     *
     * @param id       ID de la dieta a actualizar.
     * @param dieta    Objeto con los datos actualizados.
     * @param model    Modelo para pasar mensajes a la vista.
     * @return Redirige a la lista de dietas con un mensaje de éxito o error.
     */
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String update(@PathVariable Integer id, @ModelAttribute Dieta dieta, Model model) {
        try {
            Optional<Dieta> existingDieta= service.encuentraPorId(id);
            if (existingDieta.isPresent()) {
                Dieta updatedDieta = existingDieta.get();
                updatedDieta.setNombre(dieta.getNombre());
                updatedDieta.setObjetivos(dieta.getObjetivos());
                updatedDieta.setCategoria(dieta.getCategoria());
                updatedDieta.setDescripcion(dieta.getDescripcion());
                // Actualizar otros campos necesarios si es necesario

                service.guardar(updatedDieta);
                model.addAttribute("mensaje", "Dieta actualizada con éxito");
                return "redirect:/dietaUsuario";
            } else {
                model.addAttribute("mensaje", "Dieta no encontrada");
                return "redirect:/dietaUsuario";
            }
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar dieta: " + e.getMessage());
            return "redirect:/dietaUsuario";
        }
    }

    /**
     * Elimina una dieta existente por ID.
     *
     * @param id ID de la dieta a eliminar.
     * @return Redirige a la lista de dietas o a un error 404 si no se encuentra.
     */
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Integer id) {
        try {
            service.eliminarPorId(id);
            return "redirect:/dietaUsuario";
        } catch (EntityNotFoundException e) {
            return "redirect:/404";
        }
    }

    /**
     * Muestra el formulario para crear una nueva dieta.
     *
     * @param model Modelo para pasar datos a la vista.
     * @return Vista con el formulario de creación de dieta.
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("dieta", new Dieta());
        return "creardieta"; // nombre del archivo Thymeleaf (sin .html)
    }

    /**
     * Crea una nueva dieta con los datos proporcionados.
     *
     * @param dieta Objeto con los datos de la nueva dieta.
     * @param model Modelo para pasar mensajes a la vista.
     * @return Redirige al formulario de creación con un mensaje de éxito o error.
     */
    @PostMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String crear(@ModelAttribute("dieta") Dieta dieta, Model model) {
        try {
            service.guardar(dieta);
            model.addAttribute("mensaje", "Dieta creada con éxito");
            return "redirect:/dietaUsuario/nuevo";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al crear dieta");
            return "redirect:/404";
        }
    }
}
