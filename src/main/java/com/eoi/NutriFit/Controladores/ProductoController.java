package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Repositorios.ProductoRepo;
import com.eoi.NutriFit.Servicios.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controlador para gestionar los productos en la aplicación NutriFit.
 * Proporciona métodos para listar, crear, editar, actualizar y eliminar productos.
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService service;
    @Autowired
    private ProductoRepo productoRepo;

    /**
     * Constructor del controlador de productos que inyecta el repositorio y el servicio de productos.
     *
     * @param productoRepo El repositorio de productos utilizado para interactuar con la base de datos.
     * @param service      El servicio de productos utilizado para la lógica de negocio.
     */
    public ProductoController(ProductoRepo productoRepo, ProductoService service) {
        this.productoRepo = productoRepo;
        this.service = service;
    }

    /**
     * Muestra una lista paginada de productos, filtrada opcionalmente por categoría.
     *
     * @param page     Número de página a mostrar (por defecto es 0).
     * @param size     Tamaño de la página (por defecto es 9).
     * @param categoria Categoría de productos a filtrar (por defecto es "proteina").
     * @param model    Modelo para pasar los datos a la vista.
     * @return El nombre de la vista que muestra los productos.
     */
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

    /**
     * Muestra una lista paginada de productos con capacidad de edición, solo accesible por usuarios con rol de ADMIN o EMPLEADO.
     *
     * @param page  Número de página a mostrar (por defecto es 0).
     * @param size  Tamaño de la página (por defecto es 10).
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista que muestra la lista editable de productos.
     */
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

    /**
     * Muestra el detalle de un producto específico.
     *
     * @param id    ID del producto a mostrar.
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista que muestra el detalle del producto o redirige a una página 404 si no se encuentra.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        Optional<Producto> producto = service.encuentraPorId(id);

        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "detalleproducto";
        } else {
            return "redirect:/404";
        }
    }

    /**
     * Muestra el formulario para crear un nuevo producto.
     * Solo accesible por usuarios con rol de ADMIN o EMPLEADO.
     *
     * @param model Modelo para pasar los datos a la vista.
     * @return El nombre de la vista que contiene el formulario para crear un nuevo producto.
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "crearproducto"; // nombre del archivo Thymeleaf (sin .html)
    }

    /**
     * Crea un nuevo producto y lo guarda en la base de datos.
     * Solo accesible por usuarios con rol de ADMIN o EMPLEADO.
     *
     * @param producto            El objeto Producto que se va a crear.
     * @param redirectAttributes  Atributos para redirigir mensajes a la vista.
     * @return Redirige al formulario de creación de producto con un mensaje de éxito o error.
     */
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

    /**
     * Actualiza un producto existente en la base de datos.
     * Solo accesible por usuarios con rol de ADMIN o EMPLEADO.
     *
     * @param id                 ID del producto a actualizar.
     * @param producto           El objeto Producto con los datos actualizados.
     * @param redirectAttributes Atributos para redirigir mensajes a la vista.
     * @return Redirige a la lista de productos con un mensaje de éxito o error.
     */
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

    /**
     * Elimina un producto específico de la base de datos.
     * Solo accesible por usuarios con rol de ADMIN.
     *
     * @param id ID del producto que se va a eliminar.
     * @return Redirige a la lista de productos después de la eliminación o a una página 404 si no se encuentra el producto.
     */
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
