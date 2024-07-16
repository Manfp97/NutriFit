package com.eoi.NutriFit.Controladores;


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
@RequestMapping("/usuarioController")
public class UsuarioController {

    @Autowired
    private UsuarioServi service;

    @GetMapping
    public String getAll(Model model) {
        List<Usuario> listaUsuario = service.buscarEntidades();
        model.addAttribute("usuarioController", listaUsuario);
        return "usuario";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
        Optional<Usuario> usuario = service.encuentraPorId(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Usuario create(@RequestBody UsuarioServi usuario) throws Exception {
        return service.guardar(new Usuario());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuario) throws Exception {
        Optional<Usuario> existingUsuario = service.encuentraPorId(id);
        if (existingUsuario.isPresent()) {
            usuario.setId(id);
            return ResponseEntity.ok(service.guardar(usuario));
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

