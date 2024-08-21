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

/**
 * Controlador para la gestión de usuarios en la aplicación.
 * Este controlador maneja operaciones CRUD y otras funcionalidades relacionadas con la entidad Usuario,
 * incluyendo la visualización, creación, actualización y eliminación de usuarios.
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

    /**
     * Muestra una lista paginada de entrenadores.
     *
     * @param page Número de página a mostrar (0 por defecto).
     * @param size Tamaño de la página (9 por defecto).
     * @param model Modelo para agregar atributos a la vista.
     * @return Nombre de la vista que muestra la lista de entrenadores.
     */
    @GetMapping("/entrenadores")
    public String listEntrenadores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            Model model
    ) {
        // Ajustar el valor de 'page' si es negativo
        if (page < 0) {
            page = 0;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage = usuarioRepo.findByRol(rolesRepo.findByNombreRol("ROLE_ENTRENADOR"), pageable);

        // Si se solicita una página fuera de rango, redirigir a la primera página
        if (usuariosPage.isEmpty() && page > 0) {
            return "redirect:/entrenadores?page=0&size=" + size;
        }

        // Crear la lista de números de página
        int totalPages = usuariosPage.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());

        // Añadir atributos al modelo
        model.addAttribute("pagina", usuariosPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("rol", "ROLE_ENTRENADOR");

        return "entrenadoresfreelance";
    }

    /**
     * Muestra una lista paginada de nutricionistas.
     *
     * @param page Número de página a mostrar (0 por defecto).
     * @param size Tamaño de la página (9 por defecto).
     * @param model Modelo para agregar atributos a la vista.
     * @return Nombre de la vista que muestra la lista de nutricionistas.
     */
    @GetMapping("/nutricionistas")
    public String listNutricionistas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            Model model
    ) {
        // Ajustar el valor de 'page' si es negativo
        if (page < 0) {
            page = 0;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage = usuarioRepo.findByRol(rolesRepo.findByNombreRol("ROLE_NUTRICIONISTA"), pageable);

        // Si se solicita una página fuera de rango, redirigir a la primera página
        if (usuariosPage.isEmpty() && page > 0) {
            return "redirect:/nutricionistas?page=0&size=" + size;
        }

        // Crear la lista de números de página
        int totalPages = usuariosPage.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());

        // Añadir atributos al modelo
        model.addAttribute("pagina", usuariosPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("rol", "ROLE_NUTRICIONISTA");

        return "nutricionistasfreelance";
    }

    /**
     * Muestra una lista paginada de usuarios editables.
     *
     * @param page Número de página a mostrar (0 por defecto).
     * @param size Tamaño de la página (10 por defecto).
     * @param rol Rol de los usuarios a mostrar, puede ser null para mostrar todos los usuarios.
     * @param model Modelo para agregar atributos a la vista.
     * @return Nombre de la vista que muestra la lista de usuarios editables.
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
     * @param id Identificador del usuario a recuperar.
     * @param model Modelo para agregar atributos a la vista.
     * @return Nombre de la vista que muestra los detalles del usuario, o redirige a una página de error si no se encuentra el usuario.
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
     * @param model Modelo para agregar atributos a la vista.
     * @return Nombre de la vista que muestra el formulario de creación de usuario.
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolesServi.getRepo().findAll());
        return "crearusuario";
    }

    /**
     * Crea un nuevo usuario con la información proporcionada en el formulario.
     *
     * @param usuario Objeto Usuario con los datos del nuevo usuario.
     * @param model Modelo para agregar atributos a la vista.
     * @return Nombre de la vista a la que redirige después de la creación del usuario, o el mismo formulario si ocurre un error.
     */
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

            // Codifica la contraseña del usuario
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

    /**
     * Actualiza un usuario existente con la información proporcionada.
     *
     * @param id Identificador del usuario a actualizar.
     * @param usuario Objeto Usuario con los datos actualizados.
     * @param model Modelo para agregar atributos a la vista.
     * @return Nombre de la vista a la que redirige después de la actualización, o una página de error si ocurre un problema.
     */
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

    /**
     * Elimina un usuario específico por su ID.
     *
     * @param id Identificador del usuario a eliminar.
     * @return Nombre de la vista a la que redirige después de la eliminación, o una página de error si el usuario no se encuentra.
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
