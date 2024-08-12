package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Repositorios.ProductoRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CarritoController {

    @Autowired
    private ProductoRepo productoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/carrito")
    public String listarProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "listaProductos"; // Nombre de la vista de listado de productos
    }

    @GetMapping("/carrito/ver")
    public String verCarrito(@CookieValue(name = "carrito", defaultValue = "") String carritoJson, Model model) throws IOException {
        List<Producto> productosCarrito = obtenerProductosDelCarrito(carritoJson);
        model.addAttribute("productosCarrito", productosCarrito);
        return "verCarrito"; // Nombre de la vista para ver el carrito
    }

    @PostMapping("/carrito/agregar/{idProducto}")
    public String agregarProductoAlCarrito(@PathVariable Integer idProducto,
                                           @CookieValue(name = "carrito", defaultValue = "") String carritoJson,
                                           HttpServletResponse response) throws IOException {
        Producto producto = productoRepository.findById(idProducto).orElse(null);
        if (producto != null) {
            List<Producto> productosCarrito = obtenerProductosDelCarrito(carritoJson);
            productosCarrito.add(producto);
            actualizarCookieCarrito(productosCarrito, response);
        }
        return "redirect:/carrito/ver";
    }

    @PostMapping("/carrito/eliminar/{idProducto}")
    public String eliminarProductoDelCarrito(@PathVariable Integer idProducto,
                                             @CookieValue(name = "carrito", defaultValue = "") String carritoJson,
                                             HttpServletResponse response) throws IOException {
        List<Producto> productosCarrito = obtenerProductosDelCarrito(carritoJson);
        productosCarrito.removeIf(producto -> producto.getId().equals(idProducto));
        actualizarCookieCarrito(productosCarrito, response);
        return "redirect:/carrito/ver";
    }

    @PostMapping("/carrito/confirmarCompra")
    public String confirmarCompra(@CookieValue(name = "carrito", defaultValue = "") String carritoJson,
                                  HttpServletResponse response) {
        // Lógica para confirmar la compra (a implementar)
        // Vaciar el carrito después de confirmar la compra
        Cookie cookie = new Cookie("carrito", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "confirmarCompra"; // Nombre de la vista para confirmar la compra
    }

    // Método para obtener productos del carrito a partir de la cookie
    private List<Producto> obtenerProductosDelCarrito(String carritoJson) throws IOException {
        if (carritoJson == null || carritoJson.isEmpty()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(carritoJson, new TypeReference<List<Producto>>() {});
    }

    // Método para actualizar la cookie del carrito
    private void actualizarCookieCarrito(List<Producto> productosCarrito, HttpServletResponse response) throws IOException {
        String carritoJson = objectMapper.writeValueAsString(productosCarrito);
        Cookie cookie = new Cookie("carrito", carritoJson);
        cookie.setMaxAge(7 * 24 * 60 * 60); // Expira en 7 días
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
