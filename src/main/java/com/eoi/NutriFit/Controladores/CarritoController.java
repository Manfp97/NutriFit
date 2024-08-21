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
import java.util.List;

/**
 * <code>CarritoController</code> gestiona las operaciones relacionadas con el carrito de compras,
 * incluyendo la visualización del carrito, la adición y eliminación de productos, y la persistencia del carrito en cookies.
 *
 * <p>Este controlador utiliza cookies para almacenar el carrito de compras, permitiendo a los usuarios
 * continuar su sesión de compras aunque cierren el navegador. El carrito se serializa en formato JSON
 * y se almacena como una cookie codificada.</p>
 *
 * <p>Las operaciones principales incluyen:</p>
 * <ul>
 *     <li>Visualización del contenido del carrito.</li>
 *     <li>Adición de productos al carrito.</li>
 *     <li>Eliminación de productos del carrito.</li>
 * </ul>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ProductoService productoService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String CARRITO_COOKIE_NAME = "carrito";

    /**
     * Muestra el contenido del carrito de compras.
     *
     * @param request La solicitud HTTP actual, utilizada para recuperar las cookies.
     * @param model   Modelo de datos utilizado para pasar atributos a la vista.
     * @return El nombre de la vista <code>carrito</code>.
     * @throws JsonProcessingException Si ocurre un error al procesar el JSON del carrito.
     */
    @GetMapping
    public String mostrarCarrito(HttpServletRequest request, Model model) throws JsonProcessingException {
        List<Producto> carrito = obtenerCarritoDeCookies(request);

        // Calcula el precio total del carrito
        double carritoTotal = carrito.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();

        model.addAttribute("carrito", carrito);
        model.addAttribute("carritoTotal", carritoTotal);

        return "carrito";
    }

    /**
     * Agrega un producto al carrito de compras.
     *
     * @param idProducto El ID del producto que se va a agregar.
     * @param request    La solicitud HTTP actual, utilizada para recuperar las cookies.
     * @param response   La respuesta HTTP actual, utilizada para almacenar las cookies.
     * @return Redirige al usuario a la página del carrito de compras.
     * @throws JsonProcessingException Si ocurre un error al procesar el JSON del carrito.
     */
    @PostMapping("/agregar/{idProducto}")
    public String agregarProductoAlCarrito(@PathVariable Integer idProducto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        Producto producto = productoService.encuentraPorId(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        List<Producto> carrito = obtenerCarritoDeCookies(request);
        carrito.add(producto);
        guardarCarritoEnCookies(carrito, response);
        return "redirect:/carrito";
    }

    /**
     * Elimina un producto del carrito de compras.
     *
     * @param idProducto El ID del producto que se va a eliminar.
     * @param request    La solicitud HTTP actual, utilizada para recuperar las cookies.
     * @param response   La respuesta HTTP actual, utilizada para almacenar las cookies.
     * @return Redirige al usuario a la página del carrito de compras.
     * @throws JsonProcessingException Si ocurre un error al procesar el JSON del carrito.
     */
    @PostMapping("/eliminar/{idProducto}")
    public String eliminarProductoDelCarrito(@PathVariable Integer idProducto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        List<Producto> carrito = obtenerCarritoDeCookies(request);
        carrito.removeIf(p -> p.getId().equals(idProducto));
        guardarCarritoEnCookies(carrito, response);
        return "redirect:/carrito";
    }

    /**
     * Recupera el carrito de compras desde las cookies de la solicitud HTTP.
     *
     * @param request La solicitud HTTP actual, utilizada para recuperar las cookies.
     * @return Una lista de productos que están en el carrito.
     * @throws JsonProcessingException Si ocurre un error al procesar el JSON del carrito.
     */
    private List<Producto> obtenerCarritoDeCookies(HttpServletRequest request) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CARRITO_COOKIE_NAME.equals(cookie.getName())) {
                    try {
                        String carritoJson = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        return objectMapper.readValue(carritoJson, new TypeReference<List<Producto>>() {});
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException("Error al decodificar el valor de la cookie", e);
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * Guarda el carrito de compras en las cookies de la respuesta HTTP.
     *
     * @param carrito  Una lista de productos que se van a almacenar en el carrito.
     * @param response La respuesta HTTP actual, utilizada para almacenar las cookies.
     * @throws JsonProcessingException Si ocurre un error al procesar el JSON del carrito.
     */
    private void guardarCarritoEnCookies(List<Producto> carrito, HttpServletResponse response) throws JsonProcessingException {
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
}
