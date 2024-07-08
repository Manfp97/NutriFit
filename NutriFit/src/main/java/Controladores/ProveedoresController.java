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
@RequestMapping("/proveedores")
public class ProveedoresController {

    @Autowired
    private Proveedores service;

    @GetMapping
    public String getAll(Model model) {
        List<Proveedores> listaProveedores = service.buscarEntidades();
        model.addAttribute("proveedores", listaProveedores);
        return "proveedores";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedores> getById(@PathVariable Integer id) {
        Optional<Proveedores> proveedor = service.encuentraporId(id);
        if (proveedor.isPresent()) {
            return ResponseEntity.ok(proveedor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Proveedores create(@RequestBody Proveedores proveedor) throws Exception {
        return service.guardar(proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedores> update(@PathVariable Integer id, @RequestBody Proveedores proveedor) throws Exception {
        Optional<Proveedores> existingProveedor = service.encuentraPorId(id);
        if (existingProveedor.isPresent()) {
            proveedor.setId(id);
            return ResponseEntity.ok(service.guardar(proveedor));
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
