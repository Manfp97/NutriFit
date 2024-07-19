package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Dieta;
import com.eoi.NutriFit.Servicios.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dietaUsuario")
public class DietaController {

    @Autowired
    private DietaService service;

    @GetMapping
    public String getAll(Model model) {
        List<Dieta> listaDetalles = service.buscarEntidades();
        model.addAttribute("dietas", listaDetalles);
        return "dieta";

    }

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