package com.eoi.NutriFit.Controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Controlador principal para la gestión de las vistas de la aplicación.
 * Este controlador maneja las solicitudes de las diferentes páginas estáticas del sitio web.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
public class MainController {

    /**
     * Maneja las solicitudes para la página de inicio y la página de índice.
     *
     * @return El nombre de la vista de la página de inicio.
     */
    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    /**
     * Maneja las solicitudes para la página del blog.
     *
     * @return El nombre de la vista de la página del blog.
     */
    @GetMapping({"/blog"})
    public String blog() {
        return "blog";
    }

    /**
     * Maneja las solicitudes para la página de contacto.
     *
     * @return El nombre de la vista de la página de contacto.
     */
    @GetMapping({"/contact"})
    public String contact() {
        return "contact";
    }

    /**
     * Maneja las solicitudes para la página de características.
     *
     * @return El nombre de la vista de la página de características.
     */
    @GetMapping({"/feature"})
    public String feature() {
        return "feature";
    }

    /**
     * Maneja las solicitudes para la página sobre nosotros.
     *
     * @return El nombre de la vista de la página sobre nosotros.
     */
    @GetMapping({"/about"})
    public String about() {
        return "about";
    }

    /**
     * Maneja las solicitudes para la página de testimonios.
     *
     * @return El nombre de la vista de la página de testimonios.
     */
    @GetMapping({"/testimonial"})
    public String testimonial() {
        return "testimonial";
    }
}
