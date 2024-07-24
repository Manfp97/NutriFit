package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Dieta;
import com.eoi.NutriFit.Repositorios.DietaRepo;
import com.eoi.NutriFit.Servicios.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/dietaUsuario")
public class DietaController {

    @Autowired
    private DietaService service;

    @Autowired
    private DietaRepo dietaRepo;

    @GetMapping
    public String getDietas(
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

//    @GetMapping
//    public String getAll(Model model) {
//        List<Dieta> listaDetalles = service.buscarEntidades();
//        model.addAttribute("dietas", listaDetalles);
//        return "dieta";
//
//    }
    
    

    @GetMapping("/{id}")
    public ResponseEntity<Dieta> getById(@PathVariable Integer id) {
        Optional<Dieta> dieta = service.encuentraPorId(id);
        if (dieta.isPresent()) {
            return ResponseEntity.ok(dieta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Dieta create(@RequestBody Dieta dieta) throws Exception {
        return service.guardar(dieta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dieta> update(@PathVariable Integer id, @RequestBody Dieta dieta) throws Exception {
        Optional<Dieta> existingDieta = service.encuentraPorId(id);
        if (existingDieta.isPresent()) {
            dieta.setId(id);
            return ResponseEntity.ok(service.guardar(dieta));
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