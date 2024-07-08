package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Servicios.DetalleUsuarioService;
import com.eoi.NutriFit.Servicios.DetalleUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/detallesUsuario")
public class DetalleUsuarioController {

    @Autowired
    private DetalleUsuarioService service;

    @GetMapping
    public String getAll(Model model) {
        List<DetalleUsuario> listaDetalles = service.buscarEntidades();
        model.addAttribute("detalleUsuarios", listaDetalles);
        return "detalleUsuarios";

    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleUsuario> getById(@PathVariable Integer id) {
        Optional<DetalleUsuario> detalleUsuario = service.encuentraPorId(id);
        if (detalleUsuario.isPresent()) {
            return ResponseEntity.ok(detalleUsuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public DetalleUsuario create(@RequestBody DetalleUsuario detalleUsuario) throws Exception {
        return service.guardar(detalleUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleUsuario> update(@PathVariable Integer id, @RequestBody DetalleUsuario detalleUsuario) throws Exception {
        Optional<DetalleUsuario> existingDetalleUsuario = service.encuentraPorId(id);
        if (existingDetalleUsuario.isPresent()) {
            detalleUsuario.setId(id);
            return ResponseEntity.ok(service.guardar(detalleUsuario));
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