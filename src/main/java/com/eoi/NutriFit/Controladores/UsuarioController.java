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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.eoi.NutriFit.Servicios.NotificationServiceEmail;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controlador para la gestión de usuarios en la aplicación NutriFit.
 * Permite realizar operaciones CRUD y visualización de usuarios, así como gestionar perfiles y roles específicos.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
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

    @Autowired
    private NotificationServiceEmail notificationServiceEmail;

    /**
     * Muestra una lista paginada de entrenadores.
     *
     * @param page Número de página para la paginación.
     * @param size Tamaño de la página.
     * @param model Modelo para pasar datos a la vista.
     * @return Nombre de la vista para la lista de entrenadores.
     */
    @GetMapping("/entrenadores")
    public String listEntrenadores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            Model model
    ) {
        if (page < 0) {
            page = 0;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage = usuarioRepo.findByRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"), pageable);

        if (usuariosPage.isEmpty() && page > 0) {
            return "redirect:/entrenadores?page=0&size=" + size;
        }

        int totalPages = usuariosPage.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("pagina", usuariosPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("rol", "ROLE_ENTRENADOR");

        return "entrenadoresfreelance";
    }

    /**
     * Redirige al perfil del usuario autenticado.
     *
     * @param authentication Información de autenticación del usuario.
     * @return Redirección a la vista del perfil del usuario.
     * @throws EntityNotFoundException Si no se encuentra el usuario.
     */
    @GetMapping("/perfil")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String redirigirPerfil(Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioRepo.findUsuarioByUsernameAndActivoTrue(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        return "redirect:/usuario/perfil/" + usuario.getId();
    }

    /**
     * Muestra el perfil de un usuario específico por su ID.
     *
     * @param id ID del usuario.
     * @param model Modelo para pasar datos a la vista.
     * @return Nombre de la vista para mostrar el perfil del usuario.
     * @throws EntityNotFoundException Si no se encuentra el usuario.
     */
    @GetMapping("/perfil/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String mostrarPerfilPorId(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    /**
     * Actualiza el perfil de un usuario específico.
     *
     * @param id ID del usuario a actualizar.
     * @param usuario Datos del usuario a actualizar.
     * @param newPassword Nueva contraseña del usuario (opcional).
     * @param model Modelo para pasar datos a la vista.
     * @param redirectAttributes Atributos de redirección para mensajes flash.
     * @return Redirección a la vista del perfil del usuario.
     */
    @PostMapping("/perfil/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String actualizarPerfil(@PathVariable Integer id, @ModelAttribute("usuario") Usuario usuario,
                                   @RequestParam(value = "password", required = false) String newPassword,
                                   Model model, RedirectAttributes redirectAttributes) {
        try {
            Usuario existingUsuario = service.encuentraPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

            DetalleUsuario detalleUsuario = existingUsuario.getDetalleUsuario();
            if (detalleUsuario == null) {
                detalleUsuario = new DetalleUsuario();
                detalleUsuario.setUsuario(existingUsuario);
            }

            detalleUsuario.setNombre(usuario.getDetalleUsuario().getNombre());
            detalleUsuario.setApellidos(usuario.getDetalleUsuario().getApellidos());
            detalleUsuario.setDireccion(usuario.getDetalleUsuario().getDireccion());
            detalleUsuario.setDni(usuario.getDetalleUsuario().getDni());
            detalleUsuario.setEmail(usuario.getDetalleUsuario().getEmail());

            existingUsuario.setDetalleUsuario(detalleUsuario);

            if (newPassword != null && !newPassword.isEmpty()) {
                existingUsuario.setPassword(passwordEncoder.encode(newPassword));
            }

            service.guardar(existingUsuario);

            redirectAttributes.addFlashAttribute("mensaje", "Perfil actualizado con éxito");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar perfil: " + e.getMessage());
        }

        return "redirect:/usuario/perfil/" + id;
    }

    /**
     * Muestra una lista paginada de nutricionistas.
     *
     * @param page Número de página para la paginación.
     * @param size Tamaño de la página.
     * @param model Modelo para pasar datos a la vista.
     * @return Nombre de la vista para la lista de nutricionistas.
     */
    @GetMapping("/nutricionistas")
    public String listNutricionistas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            Model model
    ) {
        if (page < 0) {
            page = 0;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage = usuarioRepo.findByRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"), pageable);

        if (usuariosPage.isEmpty() && page > 0) {
            return "redirect:/nutricionistas?page=0&size=" + size;
        }

        int totalPages = usuariosPage.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("pagina", usuariosPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("rol", "ROLE_NUTRICIONISTA");

        return "nutricionistasfreelance";
    }

    /**
     * Muestra una lista paginada de usuarios editables.
     *
     * @param page Número de página para la paginación.
     * @param size Tamaño de la página.
     * @param rol Rol de los usuarios a filtrar (opcional).
     * @param model Modelo para pasar datos a la vista.
     * @return Nombre de la vista para la lista de usuarios editables.
     */
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

    /**
     * Muestra los detalles de un usuario específico por su ID.
     *
     * @param id ID del usuario.
     * @param model Modelo para pasar datos a la vista.
     * @return Nombre de la vista para mostrar los detalles del usuario o redirección a 404 si no se encuentra.
     */
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

    /**
     * Muestra el formulario para crear un nuevo usuario.
     *
     * @param model Modelo para pasar datos a la vista.
     * @return Nombre de la vista para el formulario de creación de usuario.
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolesServi.getRepo().findAll());
        return "crearusuario";
    }

    /**
     * Crea un nuevo usuario con los datos proporcionados.
     *
     * @param usuario Datos del nuevo usuario.
     * @param model Modelo para pasar datos a la vista.
     * @return Redirección al formulario de creación de usuario con un mensaje de éxito o error.
     */
    @PostMapping("/nuevo")
    public String crear(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            if (usuario.getDetalleUsuario() == null ||
                    usuario.getDetalleUsuario().getNombre() == null ||
                    usuario.getDetalleUsuario().getApellidos() == null) {
                model.addAttribute("mensaje", "Nombre y apellidos son obligatorios");
                model.addAttribute("roles", rolesServi.getRepo().findAll());
                return "crearusuario";
            }

            Roles rol = rolesRepo.findByNombreRol("ROLE_USER");
            if (rol == null) {
                model.addAttribute("mensaje", "Rol predeterminado no encontrado");
                model.addAttribute("roles", rolesServi.getRepo().findAll());
                return "crearusuario";
            }
            usuario.setRol(rol);

            String encodedPassword = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(encodedPassword);

            service.guardar(usuario);

            String userEmail = usuario.getDetalleUsuario().getEmail();
            String userName = usuario.getUsername();

            notificationServiceEmail.sendNotification(userEmail, userName);

            model.addAttribute("mensaje", "Usuario y detalles creados con éxito");
            return "redirect:/usuario/nuevo";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al crear usuario y detalles: " + e.getMessage());
            model.addAttribute("roles", rolesServi.getRepo().findAll());
            return "crearusuario";
        }
    }

    /**
     * Actualiza los datos de un usuario específico.
     *
     * @param id ID del usuario a actualizar.
     * @param usuario Datos del usuario a actualizar.
     * @param model Modelo para pasar datos a la vista.
     * @return Redirección a la lista de usuarios con un mensaje de éxito o error.
     */
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String actualizar(@PathVariable Integer id, @ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            Usuario existingUsuario = service.encuentraPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

            existingUsuario.setUsername(usuario.getUsername());

            if (!usuario.getPassword().isEmpty()) {
                existingUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            if (usuario.getDetalleUsuario() != null) {
                DetalleUsuario detalleUsuario = existingUsuario.getDetalleUsuario();
                if (detalleUsuario == null) {
                    detalleUsuario = new DetalleUsuario();
                    detalleUsuario.setUsuario(existingUsuario);
                }

                detalleUsuario.setNombre(usuario.getDetalleUsuario().getNombre());
                detalleUsuario.setApellidos(usuario.getDetalleUsuario().getApellidos());
                detalleUsuario.setDireccion(usuario.getDetalleUsuario().getDireccion());
                detalleUsuario.setDni(usuario.getDetalleUsuario().getDni());
                detalleUsuario.setEmail(usuario.getDetalleUsuario().getEmail());

                existingUsuario.setDetalleUsuario(detalleUsuario);
            }

            if (usuario.getRol() != null && usuario.getRol().getId() != null) {
                Roles rol = rolesRepo.findById(usuario.getRol().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Rol no válido"));
                existingUsuario.setRol(rol);
            }

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

    /**
     * Elimina un usuario específico por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return Redirección a la lista de usuarios o a la página 404 si el usuario no se encuentra.
     */
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Integer id) {
        try {
            service.eliminarPorId(id);
            return "redirect:/usuario/list";
        } catch (EntityNotFoundException e) {
            return "redirect:/404";
        }
    }
}