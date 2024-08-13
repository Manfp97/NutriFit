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

@Controller
@RequestMapping("/proveedores")
public class ProveedoresController {

    @Autowired
    private ProveedoresServi service;

    @Autowired
    private ProveedoresRepo proveedoresRepo;

    @Autowired
    public ProveedoresController(ProveedoresServi service, ProveedoresRepo proveedoresRepo) {
        this.service = service;
        this.proveedoresRepo = proveedoresRepo;
    }
    
    
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
        return "listaproveedoreseditable"; // El nombre del archivo Thymeleaf que mostraría la tabla
    }

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
                // Actualizar otros campos necesarios si es necesario

                service.guardar(updatedProveedores);
                model.addAttribute("mensaje", "Proveedores actualizada con éxito");
                return "redirect:/proveedores";
            } else {
                model.addAttribute("mensaje", "Proveedores no encontrada");
                return "redirect:/proveedores";
            }
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar proveedores: " + e.getMessage());
            return "redirect:/proveedores";
        }
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Integer id) {
        try {
            service.eliminarPorId(id);
            return "redirect:/proveedores";
        } catch (EntityNotFoundException e) {
            return "redirect:/404";
        }
    }


    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("proveedores", new Proveedores());
        return "crearproveedores"; // nombre del archivo Thymeleaf (sin .html)
    }

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