package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Servicios.ProductoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ProductoService productoService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String CARRITO_COOKIE_NAME = "carrito";

    @GetMapping
    public String mostrarCarrito(HttpServletRequest request, Model model) throws JsonProcessingException {
        Map<Integer, ProductoCarrito> carrito = obtenerCarritoDeCookies(request);

        // Calcula el precio total del carrito
        double carritoTotal = carrito.values().stream()
                .mapToDouble(pc -> pc.getProducto().getPrecio() * pc.getCantidad())
                .sum();

        model.addAttribute("carrito", carrito.values());
        model.addAttribute("carritoTotal", carritoTotal);

        return "carrito";
    }

    @PostMapping("/agregar/{idProducto}")
    public String agregarProductoAlCarrito(@PathVariable Integer idProducto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        Producto producto = productoService.encuentraPorId(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        Map<Integer, ProductoCarrito> carrito = obtenerCarritoDeCookies(request);

        if (carrito.containsKey(idProducto)) {
            carrito.get(idProducto).incrementarCantidad();
        } else {
            carrito.put(idProducto, new ProductoCarrito(producto, 1));
        }

        guardarCarritoEnCookies(carrito, response);
        return "redirect:/producto";
    }

    @PostMapping("/eliminar/{idProducto}")
    public String eliminarProductoDelCarrito(@PathVariable Integer idProducto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        Map<Integer, ProductoCarrito> carrito = obtenerCarritoDeCookies(request);
        carrito.remove(idProducto);
        guardarCarritoEnCookies(carrito, response);
        return "redirect:/carrito";
    }

    private Map<Integer, ProductoCarrito> obtenerCarritoDeCookies(HttpServletRequest request) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CARRITO_COOKIE_NAME.equals(cookie.getName())) {
                    try {
                        String carritoJson = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        // Intentar deserializar como mapa
                        try {
                            return objectMapper.readValue(carritoJson, new TypeReference<Map<Integer, ProductoCarrito>>() {});
                        } catch (JsonProcessingException e) {
                            // Si falla, intentar deserializar como lista (formato antiguo)
                            List<Producto> listaProductos = objectMapper.readValue(carritoJson, new TypeReference<List<Producto>>() {});
                            // Convertir la lista a mapa
                            Map<Integer, ProductoCarrito> mapaCarrito = new HashMap<>();
                            for (Producto producto : listaProductos) {
                                mapaCarrito.put(producto.getId(), new ProductoCarrito(producto, 1));
                            }
                            return mapaCarrito;
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException("Error al decodificar el valor de la cookie", e);
                    }
                }
            }
        }
        return new HashMap<>();
    }

    private void guardarCarritoEnCookies(Map<Integer, ProductoCarrito> carrito, HttpServletResponse response) throws JsonProcessingException {
        try {
            String carritoJson = objectMapper.writeValueAsString(carrito);
            String carritoEncoded = URLEncoder.encode(carritoJson, "UTF-8");
            Cookie cookie = new Cookie(CARRITO_COOKIE_NAME, carritoEncoded);
            cookie.setMaxAge(7 * 24 * 60 * 60); // La cookie dura 7 días
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error al codificar el valor de la cookie", e);
        }
    }

    // Clase interna para representar un producto en el carrito
    public static class ProductoCarrito {
        private Producto producto;
        private int cantidad;

        public ProductoCarrito() {}

        public ProductoCarrito(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public void incrementarCantidad() {
            this.cantidad++;
        }
    }
}