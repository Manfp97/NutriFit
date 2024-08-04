package com.eoi.NutriFit.Controladores;


import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.RolesRepo;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import com.eoi.NutriFit.Servicios.RolesServi;
import com.eoi.NutriFit.Servicios.UsuarioServi;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private RolesServi rolesServi;


    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String listAllEditable(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(required = false) String rol, // Nuevo parámetro para filtrar por rol
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage;

        if (rol != null && !rol.isEmpty()) {
            Roles role = rolesRepo.findByNombreRol(rol);
            if (role != null) {
                usuariosPage = usuarioRepo.findByRol(role, pageable);
            } else {
                usuariosPage = Page.empty(); // Si no se encuentra el rol, devuelve una página vacía
            }
        } else {
            usuariosPage = usuarioRepo.findAll(pageable);
        }

        List<Integer> pageNumbers = IntStream.rangeClosed(1, usuariosPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("usuariosPage", usuariosPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("rol", rol); // Para mantener el filtro en la vista
        return "listausuarioseditable";
    }




    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id , Model model) {
        Optional<Usuario> usuario = service.encuentraPorId(id);

        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "detalleusuario";
        } else {
            return "redirect:/404";
        }
    }


    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Arrays.asList("ROLE_USER", "ROLE_EMPLEADO")); // Lista de roles
        return "crearusuario";
    }


    @PostMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String crear(@ModelAttribute("usuario") Usuario usuario,
                        @ModelAttribute("detalleUsuario") DetalleUsuario detalleUsuario,
                        Model model) {
        try {
            detalleUsuario.setUsuario(usuario);
            usuario.setDetalleUsuario(detalleUsuario);

            // Buscar el rol en la base de datos
            Roles rol = rolesRepo.findByNombreRol(usuario.getRol().getNombreRol()); // Obtener el objeto Roles
            if (rol == null) {
                model.addAttribute("mensaje", "Rol no válido");
                return "crearusuario"; // Regresa al formulario para corregir el error
            }
            usuario.setRol(rol); // Asignar el objeto Roles al campo rol

            service.guardar(usuario);
            model.addAttribute("mensaje", "Usuario y detalles creados con éxito");
            return "redirect:/usuario/nuevo";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al crear usuario y detalles: " + e.getMessage());
            return "crearusuario"; // Regresa al formulario para corregir el error
        }
    }


    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String update(@PathVariable Integer id, @ModelAttribute Usuario usuario, Model model) {
        try {
            // Busca el usuario existente en la base de datos
            Usuario existingUsuario = service.encuentraPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

            // Actualiza los datos del usuario principal
            existingUsuario.setUsername(usuario.getUsername());
            existingUsuario.setPassword(usuario.getPassword());
            existingUsuario.setActivo(usuario.isActivo());

            // Actualiza el rol del usuario basado en el valor recibido del formulario
            String rolNombre = usuario.getRol().getNombreRol();
            Roles rol = rolesRepo.findByNombreRol(rolNombre);
            if (rol == null) {
                model.addAttribute("mensaje", "Rol no válido");
                return "redirect:/list";
            }
            existingUsuario.setRol(rol);

            // Actualiza los detalles del usuario si existen
            DetalleUsuario detalleUsuario = usuario.getDetalleUsuario();
            if (detalleUsuario != null) {
                detalleUsuario.setUsuario(existingUsuario);
                existingUsuario.setDetalleUsuario(detalleUsuario);
            }

            // Guarda el usuario con los detalles actualizados
            service.guardar(existingUsuario);
            model.addAttribute("mensaje", "Usuario actualizado con éxito");
            return "redirect:/list";
        } catch (EntityNotFoundException e) {
            model.addAttribute("mensaje", "Usuario no encontrado");
            return "redirect:/list";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar usuario: " + e.getMessage());
            return "redirect:/list";
        }
    }





    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Integer id) {
        try {
            service.eliminarPorId(id);
            return "redirect:/list";
        } catch (EntityNotFoundException e) {
            return "redirect:/404";
        }
    }
}

