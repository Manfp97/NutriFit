package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.ProgresionDieta;
import com.eoi.NutriFit.Entidades.ProgresionDieta;
import com.eoi.NutriFit.Servicios.ProgresionDietaService;
import com.eoi.NutriFit.Servicios.ProgresionDietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/progresionDieta")
public class ProgresionDietaController {

    @Autowired
    private ProgresionDietaService service;

    @GetMapping
    public String getAll(Model model) {
        List<ProgresionDieta> listaDetalles = service.buscarEntidades();
        model.addAttribute("progresionDietas", listaDetalles);
        return "progresionDietas";

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgresionDieta> getById(@PathVariable Integer id) {
        Optional<ProgresionDieta> progresionDieta = service.encuentraPorId(id);
        if (progresionDieta.isPresent()) {
            return ResponseEntity.ok(progresionDieta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ProgresionDieta create(@RequestBody ProgresionDieta progresionDieta) throws Exception {
        return service.guardar(progresionDieta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgresionDieta> update(@PathVariable Integer id, @RequestBody ProgresionDieta progresionDieta) throws Exception {
        Optional<ProgresionDieta> existingProgresionDieta = service.encuentraPorId(id);
        if (existingProgresionDieta.isPresent()) {
            progresionDieta.setId(id);
            return ResponseEntity.ok(service.guardar(progresionDieta));
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