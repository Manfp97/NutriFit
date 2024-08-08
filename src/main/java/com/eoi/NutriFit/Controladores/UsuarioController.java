package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Entidades.Roles;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private UsuarioRepository usuarioRepo;

    @Autowired
    private UsuarioServi service;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private RolesServi rolesServi;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/entrenadores")
    public String listEntrenadores(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "9") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage = usuarioRepo.findByRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"), pageable);

        if (usuariosPage.isEmpty()) {
            return "error"; // Página de error o vacía
        } else {
            // Crea la lista de números de página
            List<Integer> pageNumbers = IntStream.rangeClosed(1, usuariosPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            // Añade los atributos al modelo
            model.addAttribute("pagina", usuariosPage);
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("usuarios", usuariosPage.getContent());
            model.addAttribute("rol", "ROLE_ENTRENADOR");
            return "entrenadoresfreelance";
        }
    }

    // Controlador para nutricionistas
    @GetMapping("/nutricionistas")
    public String listNutricionistas(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "9") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage = usuarioRepo.findByRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"), pageable);

        if (usuariosPage.isEmpty()) {
            return "error"; // Página de error o vacía
        } else {
            // Crea la lista de números de página
            List<Integer> pageNumbers = IntStream.rangeClosed(1, usuariosPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            // Añade los atributos al modelo
            model.addAttribute("pagina", usuariosPage);
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("usuarios", usuariosPage.getContent());
            model.addAttribute("rol", "ROLE_NUTRICIONISTA");
            return "nutricionistasfreelance";
        }
    }





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

            //  Encoder de la password del usuario
            String encodedPassword = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(encodedPassword);


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
    public String actualizar(@PathVariable Integer id, @ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            // Buscar el usuario existente
            Usuario existingUsuario = service.encuentraPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

            // Actualizar los campos básicos del usuario
            existingUsuario.setUsername(usuario.getUsername());

            // Verificar si se proporcionó una nueva contraseña, si no, mantener la existente
            if (!usuario.getPassword().isEmpty()) {
                existingUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            // Actualizar el detalle del usuario
            if (usuario.getDetalleUsuario() != null) {
                DetalleUsuario detalleUsuario = existingUsuario.getDetalleUsuario();
                if (detalleUsuario == null) {
                    detalleUsuario = new DetalleUsuario();
                    detalleUsuario.setUsuario(existingUsuario);
                }

                // Actualizar campos de detalleUsuario
                detalleUsuario.setNombre(usuario.getDetalleUsuario().getNombre());
                detalleUsuario.setApellidos(usuario.getDetalleUsuario().getApellidos());
                detalleUsuario.setDireccion(usuario.getDetalleUsuario().getDireccion());
                detalleUsuario.setDni(usuario.getDetalleUsuario().getDni());
                detalleUsuario.setEmail(usuario.getDetalleUsuario().getEmail());

                existingUsuario.setDetalleUsuario(detalleUsuario);
            }

            // Actualizar el rol del usuario si es necesario
            if (usuario.getRol() != null && usuario.getRol().getId() != null) {
                Roles rol = rolesRepo.findById(usuario.getRol().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Rol no válido"));
                existingUsuario.setRol(rol);
            }

            // Guardar el usuario actualizado
            service.guardar(existingUsuario);
            model.addAttribute("mensaje", "Usuario actualizado con éxito");
            return "redirect:/usuario/list";
        } catch (EntityNotFoundException e) {
            model.addAttribute("mensaje", "Usuario no encontrado");
            return "redirect:/usuario/list";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar usuario: " + e.getMessage());
            return "redirect:/usuario/list";
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
