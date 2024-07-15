package Controladores;


import com.eoi.NutriFit.Entidades.Proveedores;
import com.eoi.NutriFit.Servicios.ProveedoresServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/proveedoresController")
public class ProveedoresController {

    @Autowired
    private ProveedoresServi service;

    @GetMapping
    public String getAll(Model model) {
        List<Proveedores> listaProveedores = service.buscarEntidades();
        model.addAttribute("proveedoresController", listaProveedores);
        return "proveedores";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedores> getById(@PathVariable Integer id) {
        Optional<Proveedores> proveedores = service.encuentraPorId(id);
        if (proveedores.isPresent()) {
            return ResponseEntity.ok(proveedores.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Proveedores create(@RequestBody ProveedoresServi proveedores) throws Exception {
        return service.guardar(new Proveedores());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedores> update(@PathVariable Integer id, @RequestBody Proveedores proveedores) throws Exception {
        Optional<Proveedores> existingProveedores = service.encuentraPorId(id);
        if (existingProveedores.isPresent()) {
            proveedores.setId(id);
            return ResponseEntity.ok(service.guardar(proveedores));
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

