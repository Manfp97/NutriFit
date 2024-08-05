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

@Controller
@RequestMapping("/entrenamiento")
public class EntrenamientoController {

    @Autowired
    private EntrenamientoService service;
    @Autowired
    private EntrenamientoRepo entrenamientoRepo;

    @Autowired
    public EntrenamientoController(EntrenamientoService service, EntrenamientoRepo entrenamientoRepo) {
        this.service = service;
        this.entrenamientoRepo = entrenamientoRepo;
    }

    @GetMapping
    public String listAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "9") int size,
            @RequestParam(value = "dificultad", required = false) String dificultad,
            @RequestParam(required = false, defaultValue = "grupomuscular") String categoria,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Entrenamiento> entrenamientosPage;

        // Verifica si la categoria no es nula ni vacia
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

        // 
        if (entrenamientosPage.isEmpty()) {
            return "entrenamientonotfound";

        } else {
            // Crea la lista de números de página
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


    }

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
        return "listaentrenamientoseditable"; // El nombre del archivo Thymeleaf que mostraría la tabla
    }

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

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String update(@PathVariable Integer id, @ModelAttribute Entrenamiento entrenamiento, Model model) {
        try {
            Optional<Entrenamiento> existingEntrenamiento= service.encuentraPorId(id);
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

    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("entrenamiento", new Entrenamiento());
        return "crearentrenamiento"; // nombre del archivo Thymeleaf (sin .html)
    }

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