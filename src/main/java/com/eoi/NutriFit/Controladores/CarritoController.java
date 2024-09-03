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

/**
 * Controlador que gestiona las operaciones relacionadas con el carrito de compras en la aplicación NutriFit.
 * Permite a los usuarios agregar, eliminar y visualizar productos en su carrito, utilizando cookies para almacenar
 * la información del carrito en el cliente.
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ProductoService productoService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String CARRITO_COOKIE_NAME = "carrito";

    /**
     * Muestra el contenido del carrito de compras del usuario, calculando el precio total del mismo.
     *
     * @param request La solicitud HTTP que contiene las cookies.
     * @param model   Modelo para pasar los datos a la vista.
     * @return El nombre de la vista "carrito" que muestra el contenido del carrito.
     * @throws JsonProcessingException Si ocurre un error al procesar JSON.
     */
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

    /**
     * Agrega un producto al carrito de compras. Si el producto ya está en el carrito, incrementa su cantidad.
     *
     * @param idProducto ID del producto a agregar.
     * @param request    La solicitud HTTP que contiene las cookies.
     * @param response   La respuesta HTTP donde se añadirán las cookies actualizadas.
     * @return Redirige a la vista de productos después de agregar el producto al carrito.
     * @throws JsonProcessingException Si ocurre un error al procesar JSON.
     */
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

    /**
     * Elimina un producto del carrito de compras.
     *
     * @param idProducto ID del producto a eliminar.
     * @param request    La solicitud HTTP que contiene las cookies.
     * @param response   La respuesta HTTP donde se añadirán las cookies actualizadas.
     * @return Redirige a la vista del carrito después de eliminar el producto.
     * @throws JsonProcessingException Si ocurre un error al procesar JSON.
     */
    @PostMapping("/eliminar/{idProducto}")
    public String eliminarProductoDelCarrito(@PathVariable Integer idProducto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        Map<Integer, ProductoCarrito> carrito = obtenerCarritoDeCookies(request);
        carrito.remove(idProducto);
        guardarCarritoEnCookies(carrito, response);
        return "redirect:/carrito";
    }

    /**
     * Obtiene el carrito de compras desde las cookies del cliente.
     *
     * @param request La solicitud HTTP que contiene las cookies.
     * @return Un mapa que representa el carrito de compras, con la clave siendo el ID del producto y el valor siendo el objeto ProductoCarrito.
     * @throws JsonProcessingException Si ocurre un error al procesar JSON.
     */
    private Map<Integer, ProductoCarrito> obtenerCarritoDeCookies(HttpServletRequest request) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CARRITO_COOKIE_NAME.equals(cookie.getName())) {
                    try {
                        String carritoJson = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        try {
                            return objectMapper.readValue(carritoJson, new TypeReference<Map<Integer, ProductoCarrito>>() {});
                        } catch (JsonProcessingException e) {
                            List<Producto> listaProductos = objectMapper.readValue(carritoJson, new TypeReference<List<Producto>>() {});
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

    /**
     * Guarda el carrito de compras en una cookie, codificando el contenido como JSON.
     *
     * @param carrito  Mapa que representa el carrito de compras, con la clave siendo el ID del producto y el valor siendo el objeto ProductoCarrito.
     * @param response La respuesta HTTP donde se añadirán las cookies.
     * @throws JsonProcessingException Si ocurre un error al procesar JSON.
     */
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

    /**
     * Clase interna que representa un producto dentro del carrito de compras, incluyendo la cantidad seleccionada.
     */
    public static class ProductoCarrito {
        private Producto producto;
        private int cantidad;

        /**
         * Constructor por defecto.
         */
        public ProductoCarrito() {}

        /**
         * Constructor que inicializa el producto y la cantidad.
         *
         * @param producto El producto a añadir al carrito.
         * @param cantidad La cantidad del producto.
         */
        public ProductoCarrito(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        /**
         * Obtiene el producto asociado.
         *
         * @return El producto en el carrito.
         */
        public Producto getProducto() {
            return producto;
        }

        /**
         * Establece el producto asociado.
         *
         * @param producto El producto a establecer.
         */
        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        /**
         * Obtiene la cantidad del producto en el carrito.
         *
         * @return La cantidad del producto.
         */
        public int getCantidad() {
            return cantidad;
        }

        /**
         * Establece la cantidad del producto en el carrito.
         *
         * @param cantidad La cantidad a establecer.
         */
        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        /**
         * Incrementa en 1 la cantidad del producto en el carrito.
         */
        public void incrementarCantidad() {
            this.cantidad++;
        }
    }
}
