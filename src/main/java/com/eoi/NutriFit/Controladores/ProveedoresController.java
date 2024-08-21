package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Proveedores;
import com.eoi.NutriFit.Repositorios.ProveedoresRepo;
import com.eoi.NutriFit.Servicios.ProveedoresServi;
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

/**
 * Controlador para la gestión de proveedores.
 * Este controlador maneja las operaciones CRUD relacionadas con la entidad Proveedores, incluyendo la visualización, creación, actualización y eliminación de proveedores.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/proveedores")
public class ProveedoresController {

    @Autowired
    private ProveedoresServi service;

    @Autowired
    private ProveedoresRepo proveedoresRepo;

    /**
     * Constructor para inicializar el controlador con el servicio y repositorio de proveedores.
     *
     * @param service Servicio de proveedores.
     * @param proveedoresRepo Repositorio de proveedores.
     */
    @Autowired
    public ProveedoresController(ProveedoresServi service, ProveedoresRepo proveedoresRepo) {
        this.service = service;
        this.proveedoresRepo = proveedoresRepo;
    }

    /**
     * Maneja las solicitudes GET para listar todos los proveedores en una vista editable.
     *
     * @param page Número de página para la paginación (por defecto 0).
     * @param size Tamaño de la página para la paginación (por defecto 10).
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre de la vista que muestra la lista de proveedores editables.
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String listAllEditable(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Proveedores> proveedoresPage = proveedoresRepo.findAll(pageable);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, proveedoresPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("proveedoresPage", proveedoresPage);
        model.addAttribute("pageNumbers", pageNumbers);
        return "listaproveedoreseditable"; // El nombre del archivo Thymeleaf que muestra la tabla de proveedores
    }

    /**
     * Maneja las solicitudes GET para obtener un proveedor específico por su ID.
     *
     * @param id Identificador del proveedor a recuperar.
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre de la vista que muestra los detalles del proveedor, o redirige a una página de error si el proveedor no se encuentra.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id , Model model) {
        Optional<Proveedores> proveedores = service.encuentraPorId(id);

        if (proveedores.isPresent()) {
            model.addAttribute("proveedores", proveedores.get());
            return "detalleproveedor";
        } else {
            return "redirect:/404";
        }
    }

    /**
     * Maneja las solicitudes POST para actualizar un proveedor existente por su ID.
     *
     * @param id Identificador del proveedor a actualizar.
     * @param proveedores Objeto Proveedores con los datos actualizados.
     * @param model Modelo para agregar atributos a la vista.
     * @return Una respuesta redirigiendo a la lista de proveedores con un mensaje de éxito o error.
     */
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String update(@PathVariable Integer id, @ModelAttribute Proveedores proveedores, Model model) {
        try {
            Optional<Proveedores> existingProveedores= service.encuentraPorId(id);
            if (existingProveedores.isPresent()) {
                Proveedores updatedProveedores = existingProveedores.get();
                updatedProveedores.setNombre(proveedores.getNombre());
                updatedProveedores.setContacto(proveedores.getContacto());
                updatedProveedores.setDireccion(proveedores.getDireccion());
                // Actualizar otros campos si es necesario

                service.guardar(updatedProveedores);
                model.addAttribute("mensaje", "Proveedor actualizado con éxito");
                return "redirect:/proveedores/list";
            } else {
                model.addAttribute("mensaje", "Proveedor no encontrado");
                return "redirect:/proveedores";
            }
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar proveedor: " + e.getMessage());
            return "redirect:/proveedores/list";
        }
    }

    /**
     * Maneja las solicitudes POST para eliminar un proveedor por su ID.
     *
     * @param id Identificador del proveedor a eliminar.
     * @return Una respuesta redirigiendo a la lista de proveedores con un estado de éxito o error.
     */
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Integer id) {
        try {
            service.eliminarPorId(id);
            return "redirect:/proveedores/list";
        } catch (EntityNotFoundException e) {
            return "redirect:/404";
        }
    }

    /**
     * Maneja las solicitudes GET para mostrar el formulario de creación de un nuevo proveedor.
     *
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre del archivo Thymeleaf que muestra el formulario de creación de proveedores.
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("proveedores", new Proveedores());
        return "crearproveedores"; // Nombre del archivo Thymeleaf que muestra el formulario de creación
    }

    /**
     * Maneja las solicitudes POST para crear un nuevo proveedor.
     *
     * @param proveedores Objeto Proveedores con los datos del nuevo proveedor.
     * @param model Modelo para agregar atributos a la vista.
     * @return Una respuesta redirigiendo al formulario de creación con un mensaje de éxito o error.
     */
    @PostMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String crear(@ModelAttribute("proveedores") Proveedores proveedores, Model model) {
        try {
            service.guardar(proveedores);
            model.addAttribute("mensaje", "Proveedor creado con éxito");
            return "redirect:/proveedores/nuevo";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al crear proveedor");
            return "redirect:/404";
        }
    }
}
