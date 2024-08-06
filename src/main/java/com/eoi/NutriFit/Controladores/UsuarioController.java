package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.RolesRepo;
import com.eoi.NutriFit.Servicios.RolesServi;
import com.eoi.NutriFit.Servicios.UsuarioServi;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServi service;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private RolesServi rolesServi;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String listAllEditable(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(required = false) String rol,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage;

        if (rol != null && !rol.isEmpty()) {
            Roles role = rolesRepo.findByNombreRol(rol);
            if (role != null) {
                usuariosPage = service.getRepo().findByRol(role, pageable);
            } else {
                usuariosPage = Page.empty();
            }
        } else {
            usuariosPage = service.getRepo().findAll(pageable);
        }

        List<Integer> pageNumbers = IntStream.rangeClosed(1, usuariosPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("usuariosPage", usuariosPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("rol", rol);
        return "listausuarioseditable";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        Optional<Usuario> usuario = service.encuentraPorId(id);

        if (usuario.isPresent()) {
            List<Roles> roles = rolesServi.getRepo().findAll();
            model.addAttribute("usuario", usuario.get());
            model.addAttribute("roles", roles);
            return "detalleusuario";
        } else {
            return "redirect:/404";
        }
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolesServi.getRepo().findAll());
        return "crearusuario";
    }


    @PostMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String crear(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            // Verifica que el detalleUsuario no sea null
            if (usuario.getDetalleUsuario() == null ||
                    usuario.getDetalleUsuario().getNombre() == null ||
                    usuario.getDetalleUsuario().getApellidos() == null) {
                model.addAttribute("mensaje", "Nombre y apellidos son obligatorios");
                model.addAttribute("roles", rolesServi.getRepo().findAll());
                return "crearusuario";
            }

            // Asigna el rol predeterminado
            Roles rol = rolesRepo.findByNombreRol("ROLE_USER");
            if (rol == null) {
                model.addAttribute("mensaje", "Rol predeterminado no encontrado");
                model.addAttribute("roles", rolesServi.getRepo().findAll());
                return "crearusuario";
            }
            usuario.setRol(rol);

            // Guarda el usuario
            service.guardar(usuario);
            model.addAttribute("mensaje", "Usuario y detalles creados con éxito");
            return "redirect:/usuario/nuevo";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al crear usuario y detalles: " + e.getMessage());
            model.addAttribute("roles", rolesServi.getRepo().findAll());
            return "crearusuario";
        }
    }


    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String update(@PathVariable Integer id, @ModelAttribute Usuario usuario, Model model) {
        try {
            Usuario existingUsuario = service.encuentraPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

            existingUsuario.setUsername(usuario.getUsername());
            existingUsuario.setPassword(usuario.getPassword());
            existingUsuario.setActivo(usuario.isActivo());

            Roles rol = rolesRepo.findByNombreRol(usuario.getRol().getNombreRol());
            if (rol == null) {
                model.addAttribute("mensaje", "Rol no válido");
                return "redirect:/usuario/list";
            }
            existingUsuario.setRol(rol);

            DetalleUsuario detalleUsuario = usuario.getDetalleUsuario();
            if (detalleUsuario != null) {
                detalleUsuario.setUsuario(existingUsuario);
                existingUsuario.setDetalleUsuario(detalleUsuario);
            }

            service.guardar(existingUsuario);
            model.addAttribute("mensaje", "Usuario actualizado con éxito");
            return  "redirect:/usuario/list";
        } catch (EntityNotFoundException e) {
            model.addAttribute("mensaje", "Usuario no encontrado");
            return  "redirect:/usuario/list";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar usuario: " + e.getMessage());
            return  "redirect:/usuario/list";
        }
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Integer id) {
        try {
            service.eliminarPorId(id);
            return  "redirect:/usuario/list";
        } catch (EntityNotFoundException e) {
            return "redirect:/404";
        }
    }
}
