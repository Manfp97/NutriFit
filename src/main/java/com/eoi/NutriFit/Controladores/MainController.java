package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Cliente;
import com.eoi.NutriFit.Entidades.Usuario;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Controlador principal para manejar las rutas básicas de la aplicación NutriFit.
 * Este controlador gestiona la navegación a las páginas principales como index, blog, contacto, entre otras.
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
public class MainController {

    /**
     * Maneja las solicitudes GET para las rutas raíz y "/index".
     *
     * @return El nombre de la vista "index", que muestra la página principal.
     */
    @GetMapping({"/", "/index"})
    public String index() {

        return "index";
    }

    /**
     * Maneja las solicitudes GET para la ruta "/blog".
     *
     * @return El nombre de la vista "blog", que muestra la página del blog.
     */
    @GetMapping({"/blog"})
    public String blog() {
        return "blog";
    }

    /**
     * Maneja las solicitudes GET para la ruta "/contact".
     *
     * @return El nombre de la vista "contact", que muestra la página de contacto.
     */
    @GetMapping({"/contact"})
    public String contact() {
        return "contact";
    }

    /**
     * Maneja las solicitudes GET para la ruta "/feature".
     *
     * @return El nombre de la vista "feature", que muestra la página de características.
     */
    @GetMapping({"/feature"})
    public String feature() {
        return "feature";
    }

    /**
     * Maneja las solicitudes GET para la ruta "/descubremas".
     *
     * @return El nombre de la vista "descubremas", que muestra la página de descubre más.
     */
    @GetMapping({"/descubremas"})
    public String descubremas() {
        return "descubremas";
    }

    /**
     * Maneja las solicitudes GET para la ruta "/testimonial".
     *
     * @return El nombre de la vista "testimonial", que muestra la página de testimonios.
     */
    @GetMapping({"/testimonial"})
    public String testimonial() {
        return "testimonial";
    }
}
