package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Repositorios.ProductoRepo;
import com.eoi.NutriFit.Servicios.ProductoService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService service;
    @Autowired
    private ProductoRepo productoRepo;

    public ProductoController(ProductoRepo productoRepo, ProductoService service) {
        this.productoRepo = productoRepo;
        this.service = service;
    }

    @GetMapping
    public String listAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "9") int size,
            @RequestParam(required = false, defaultValue = "proteina") String categoria,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Producto> productosPage;

        if (categoria != null && !categoria.isEmpty()) {
            productosPage = productoRepo.findByCategoria(categoria, pageable);
        } else {
            productosPage = productoRepo.findAll(pageable);
        }

        if (productosPage.isEmpty()) {
            return "error";
        } else {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, productosPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pagina", productosPage);
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("productos", productosPage.getContent());
            model.addAttribute("categoria", categoria);
            return "product";
        }
    }


    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String listAllEditable(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Producto> productosPage = productoRepo.findAll(pageable);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, productosPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("productosPage", productosPage);
        model.addAttribute("pageNumbers", pageNumbers);
        return "listaproductoseditable"; // El nombre del archivo Thymeleaf que mostraría la tabla
    }


    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id , Model model) {
        Optional<Producto> producto = service.encuentraPorId(id);

        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "detalleproducto";
        } else {
            return "redirect:/404";
        }
    }


    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "crearproducto"; // nombre del archivo Thymeleaf (sin .html)
    }

    @PostMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String crear(@ModelAttribute("producto") Producto producto, RedirectAttributes redirectAttributes) {
        try {
            service.guardar(producto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto creado con éxito");
            return "redirect:/producto/nuevo";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al crear producto");
            return "redirect:/producto/nuevo";
        }
    }


    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public String update(@PathVariable Integer id, @ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
        try {
            Optional<Producto> existingProducto = service.encuentraPorId(id);
            if (existingProducto.isPresent()) {
                Producto updatedProducto = existingProducto.get();
                updatedProducto.setNombre(producto.getNombre());
                updatedProducto.setPrecio(producto.getPrecio());
                updatedProducto.setCategoria(producto.getCategoria());
                updatedProducto.setDescripcion(producto.getDescripcion());

                service.guardar(updatedProducto);
                redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado con éxito");
                return "redirect:/producto";
            } else {
                redirectAttributes.addFlashAttribute("mensaje", "Producto no encontrado");
                return "redirect:/producto";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar producto: " + e.getMessage());
            return "redirect:/producto";
        }
    }



    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Integer id) {
        try {
            service.eliminarPorId(id);
            return "redirect:/producto";
        } catch (EntityNotFoundException e) {
            return "redirect:/404";
        }
    }
}