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

@Controller
public class MainController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping({"/blog"})
    public String blog() {
        return "blog";
    }

    @GetMapping({"/contact"})
    public String contact() {
        return "contact";
    }
    

    @GetMapping({"/feature"})
    public String feature() {
        return "feature";
    }

    @GetMapping({"/about"})
    public String about() {
        return "about";
    }


    @GetMapping({"/testimonial"})
    public String testimonial() {
        return "testimonial";
    }

}

