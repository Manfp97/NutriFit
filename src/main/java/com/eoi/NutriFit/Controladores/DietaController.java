package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Dieta;
import com.eoi.NutriFit.Repositorios.DietaRepo;
import com.eoi.NutriFit.Servicios.DietaService;
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
 * Controlador que gestiona las operaciones relacionadas con las dietas de los usuarios en la aplicación NutriFit.
 * Ofrece funcionalidades para listar, ver, crear, actualizar y eliminar dietas, con soporte para paginación y filtrado por categoría.
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Controller
@RequestMapping("/dietaUsuario")
public class DietaController {

    @Autowired
    private DietaService service;

    @Autowired
    private DietaRepo dietaRepo;

    /**
     * Constructor que permite la inyección de dependencias necesarias para el funcionamiento del controlador.
     *
     * @param service   Servicio que maneja la lógica de negocio relacionada con las dietas.
     * @param dietaRepo Repositorio para acceder a los datos de las dietas.
     */
    @Autowired
    public DietaController(DietaService service, DietaRepo dietaRepo) {
        this.service = service;
        this.dietaRepo = dietaRepo;
    }

    /**
     * Maneja las solicitudes GET para listar todas las dietas con soporte para paginación y filtrado por categoría.
     *
     * @param page     Número de la página actual, por defecto es 0.
     * @param size     Tamaño de la página, por defecto es 9.
     * @param categoria Categoría de la dieta para filtrar los resultados, por defecto es "aumentodemasamuscular".
     * @param model    Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "dieta" que muestra la lista de dietas, o "dietanotfound" si no se encuentran dietas.
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

        if (categoria != null && !categoria.isEmpty()) {
            dietasPage = dietaRepo.findByCategoria(categoria, pageable);
        } else {
            dietasPage = dietaRepo.findAll(pageable);
        }

        if (dietasPage.isEmpty()) {
            return "dietanotfound";
        } else {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, dietasPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pagina", dietasPage);
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("dietas", dietasPage.getContent());
            model.addAttribute("categoria", categoria);

            return "dieta";
        }
    }

    /**
     * Maneja las solicitudes GET para listar todas las dietas en una vista editable, accesible solo para usuarios autorizados.
     *
     * @param page  Número de la página actual, por defecto es 0.
     * @param size  Tamaño de la página, por defecto es 10.
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "listadietaseditable" que muestra la lista editable de dietas.
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
        return "listadietaseditable";
    }

    /**
     * Maneja las solicitudes GET para obtener los detalles de una dieta específica por su ID.
     *
     * @param id    ID de la dieta a consultar.
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "detalledieta" si la dieta es encontrada; de lo contrario, redirige a la página 404.
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
     * Maneja las solicitudes GET para ver más detalles de una dieta específica por su ID.
     *
     * @param id    ID de la dieta a consultar.
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "vermasdietas" si la dieta es encontrada; de lo contrario, redirige a la página 404.
     */
    @GetMapping("/vermas/{id}")
    public String getByIdVerMas(@PathVariable Integer id, Model model) {
        Optional<Dieta> dieta = service.encuentraPorId(id);

        if (dieta.isPresent()) {
            model.addAttribute("dieta", dieta.get());
            return "vermasdietas";
        } else {
            return "redirect:/404";
        }
    }

    /**
     * Maneja las solicitudes POST para actualizar una dieta existente por su ID.
     * Solo accesible para usuarios con roles específicos.
     *
     * @param id     ID de la dieta a actualizar.
     * @param dieta  Objeto Dieta con los nuevos datos a actualizar.
     * @param model  Modelo para pasar los datos a la vista.
     * @return Redirige a la lista de dietas si la actualización es exitosa, de lo contrario, muestra un mensaje de error.
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
     * Maneja las solicitudes POST para eliminar una dieta por su ID.
     * Solo accesible para usuarios con el rol de administrador.
     *
     * @param id ID de la dieta a eliminar.
     * @return Redirige a la lista de dietas si la eliminación es exitosa; de lo contrario, redirige a la página 404.
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
     * Maneja las solicitudes GET para mostrar el formulario de creación de una nueva dieta.
     * Solo accesible para usuarios con roles específicos.
     *
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "creardieta" que muestra el formulario de creación de dietas.
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("dieta", new Dieta());
        return "creardieta";
    }

    /**
     * Maneja las solicitudes POST para crear una nueva dieta.
     * Solo accesible para usuarios con roles específicos.
     *
     * @param dieta  Objeto Dieta con los datos a guardar.
     * @param model  Modelo para pasar los datos a la vista.
     * @return Redirige a la página de creación si la dieta se crea con éxito; de lo contrario, redirige a la página 404.
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
