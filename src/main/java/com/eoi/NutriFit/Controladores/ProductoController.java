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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controlador para la gestión de los productos.
 * Este controlador maneja las operaciones CRUD relacionadas con los productos,
 * así como la visualización de los productos y sus detalles.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @Autowired
    private ProductoRepo productoRepo;

    /**
     * Constructor del controlador de productos.
     *
     * @param service Servicio de productos utilizado para operaciones CRUD.
     */
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /**
     * Maneja las solicitudes para listar todos los productos con paginación y filtrado por categoría.
     *
     * @param page Número de página (paginación).
     * @param size Número de productos por página (paginación).
     * @param categoria Categoría de productos para filtrar.
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre de la vista para mostrar los productos.
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

        // Verifica si la categoría no es nula ni vacía
        if (categoria != null && !categoria.isEmpty()) {
            productosPage = productoRepo.findByCategoria(categoria, pageable);
        } else {
            productosPage = productoRepo.findAll(pageable);
        }
        if (productosPage.isEmpty()) {
            return "error";
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

    /**
     * Maneja las solicitudes para listar todos los productos con paginación para usuarios con roles específicos.
     *
     * @param page Número de página (paginación).
     * @param size Número de productos por página (paginación).
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre de la vista para mostrar los productos editables.
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
     * Maneja las solicitudes para obtener los detalles de un producto específico por su ID.
     *
     * @param id Identificador del producto.
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre de la vista para mostrar los detalles del producto o una redirección en caso de no encontrarse.
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
     *
     * @param model Modelo para agregar atributos a la vista.
     * @return El nombre del archivo Thymeleaf para la creación de un nuevo producto.
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "crearproducto"; // nombre del archivo Thymeleaf (sin .html)
    }

    /**
     * Maneja las solicitudes para crear un nuevo producto.
     *
     * @param producto El producto a ser creado.
     * @param model Modelo para agregar atributos a la vista.
     * @return Redirección a la página de creación de producto con un mensaje de éxito o error.
     */
    @PostMapping("/nuevo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
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

    /**
     * Maneja las solicitudes para actualizar un producto existente.
     *
     * @param id Identificador del producto a actualizar.
     * @param producto El producto con los datos actualizados.
     * @param model Modelo para agregar atributos a la vista.
     * @return Redirección a la página de productos con un mensaje de éxito o error.
     */
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLEADO')")
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

    /**
     * Maneja las solicitudes para eliminar un producto por su ID.
     *
     * @param id Identificador del producto a eliminar.
     * @return Redirección a la página de productos o una redirección a la página de error en caso de que el producto no se encuentre.
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
