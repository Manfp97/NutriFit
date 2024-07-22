package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import com.eoi.NutriFit.Repositorios.EntrenamientoRepo;
import com.eoi.NutriFit.Servicios.EntrenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping
//    public String getAll(Model model) {
//        List<Entrenamiento> listaDetalles = service.buscarEntidades();
//        model.addAttribute("entrenamientos", listaDetalles);
//        return "entrenamiento";
//    }

    @GetMapping
    public String getEntrenamientos(
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

    @GetMapping("/{id}")
    public ResponseEntity<Entrenamiento> getById(@PathVariable Integer id) {
        Optional<Entrenamiento> entrenamiento = service.encuentraPorId(id);
        if (entrenamiento.isPresent()) {
            return ResponseEntity.ok(entrenamiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Entrenamiento create(@RequestBody Entrenamiento entrenamiento) throws Exception {
        return service.guardar(entrenamiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrenamiento> update(@PathVariable Integer id, @RequestBody Entrenamiento entrenamiento) throws Exception {
        Optional<Entrenamiento> existingEntrenamiento = service.encuentraPorId(id);
        if (existingEntrenamiento.isPresent()) {
            entrenamiento.setId(id);
            return ResponseEntity.ok(service.guardar(entrenamiento));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.encuentraPorId(id).isPresent()) {
            service.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}