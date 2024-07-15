package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.SesionColectiva;
import com.eoi.NutriFit.Servicios.SesionColectivaServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sesionColectiva")
public class SesionColectivaController {

    @Autowired
    private SesionColectivaServi service;

    @GetMapping
    public String getAll(Model model) {
        List<SesionColectiva> listaDetalles = service.buscarEntidades();
        model.addAttribute("detalleSesionColectivas", listaDetalles);
        return "detalleSesionColectivas"; // Assuming this is a Thymeleaf template name
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionColectiva> getById(@PathVariable Integer id) {
        Optional<SesionColectiva> detalleSesionColectiva = service.encuentraPorId(id);
        return detalleSesionColectiva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SesionColectiva> create(@RequestBody SesionColectiva detalleSesionColectiva) {
        try {
            SesionColectiva createdSesionColectiva = service.guardar(detalleSesionColectiva);
            return ResponseEntity.ok(createdSesionColectiva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SesionColectiva> update(@PathVariable Integer id, @RequestBody SesionColectiva detalleSesionColectiva) {
        try {
            Optional<SesionColectiva> existingDetalleSesionColectiva = service.encuentraPorId(id);
            if (existingDetalleSesionColectiva.isPresent()) {
                detalleSesionColectiva.setId(id);
                SesionColectiva updatedSesionColectiva = service.guardar(detalleSesionColectiva);
                return ResponseEntity.ok(updatedSesionColectiva);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            if (service.encuentraPorId(id).isPresent()) {
                service.eliminarPorId(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
