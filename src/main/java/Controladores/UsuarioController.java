package Controladores;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Entidades.SesionColectiva;
import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Servicios.UsuarioServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/detallesUsuario")
public class UsuarioController {

    @Autowired
    private UsuarioServi service;

    @GetMapping
    public String getAll(Model model) {
        List<Usuario> listaDetalles = service.buscarEntidades();
        model.addAttribute("detalleUsuarios", listaDetalles);
        return "detalleUsuarios";

    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
        Optional<Usuario> detalleUsuario = service.encuentraPorId(id);
        if (detalleUsuario.isPresent()) {
            return ResponseEntity.ok(detalleUsuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Usuario create(@RequestBody DetalleUsuario detalleUsuario) throws Exception {
        return service.guardar(detalleUsuario.getUsuario());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SesionColectiva> update(@PathVariable Integer id, @RequestBody DetalleUsuario detalleUsuario) throws Exception {
        Optional<Usuario> existingDetalleUsuario = service.encuentraPorId(id);
        if (existingDetalleUsuario.isPresent()) {
            detalleUsuario.setId(id);
            return ResponseEntity.ok(service.guardar((List<Usuario>) detalleUsuario));
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

