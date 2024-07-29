package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Repositorios.ProductoRepo;
import com.eoi.NutriFit.Servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    public ProductoController(ProductoService service) {
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

        // Verifica si la categoría no es nula ni vacía
        if (categoria != null && !categoria.isEmpty()) {
            productosPage = productoRepo.findByCategoria(categoria, pageable);
        } else {
            productosPage = productoRepo.findAll(pageable);
        }
        if (productosPage.isEmpty()){
            return "productnotfound";
        } else {
            // Crea la lista de números de página
            List<Integer> pageNumbers = IntStream.rangeClosed(1, productosPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            // Añade los atributos al modelo
            model.addAttribute("pagina", productosPage);
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("productos", productosPage.getContent());
            model.addAttribute("categoria", categoria);
            return "product";

        }

    }


    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id , Model model) {
        Optional<Producto> producto = service.encuentraPorId(id);

        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "detalleproducto";
        } else {
            return "redirect:/producto";
        }
    }


    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "crearproducto"; // nombre del archivo Thymeleaf (sin .html)
    }

    @PostMapping("/nuevo")
    public String crear(@ModelAttribute("producto") Producto producto, Model model) {
        try {
            service.guardar(producto);
            model.addAttribute("mensaje", "Producto creado con éxito");
            return "redirect:/producto/nuevo";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al crear producto");
            return "redirect:/producto/nuevo";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Producto producto, Model model) {
        try {
            Optional<Producto> existingProducto = service.encuentraPorId(id);
            if (existingProducto.isPresent()) {
                Producto updatedProducto = existingProducto.get();
                updatedProducto.setNombre(producto.getNombre());
                updatedProducto.setPrecio(producto.getPrecio());
                updatedProducto.setCategoria(producto.getCategoria());
                updatedProducto.setDescripcion(producto.getDescripcion());
                // Actualizar otros campos necesarios si es necesario

                service.guardar(updatedProducto);
                model.addAttribute("mensaje", "Producto actualizado con éxito");
                return "redirect:/producto";
            } else {
                model.addAttribute("mensaje", "Producto no encontrado");
                return "redirect:/producto";
            }
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar producto: " + e.getMessage());
            return "redirect:/producto";
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