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

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ProductoService productoService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String CARRITO_COOKIE_NAME = "carrito";

    @GetMapping
    public String mostrarCarrito(HttpServletRequest request, Model model) throws JsonProcessingException {
        List<Producto> carrito = obtenerCarritoDeCookies(request);
        model.addAttribute("carrito", carrito);
        return "carrito";
    }

    @PostMapping("/agregar/{idProducto}")
    public String agregarProductoAlCarrito(@PathVariable Integer idProducto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        Producto producto = productoService.encuentraPorId(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        List<Producto> carrito = obtenerCarritoDeCookies(request);
        carrito.add(producto);
        guardarCarritoEnCookies(carrito, response);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar/{idProducto}")
    public String eliminarProductoDelCarrito(@PathVariable Integer idProducto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        List<Producto> carrito = obtenerCarritoDeCookies(request);
        carrito.removeIf(p -> p.getId().equals(idProducto));
        guardarCarritoEnCookies(carrito, response);
        return "redirect:/carrito";
    }

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
