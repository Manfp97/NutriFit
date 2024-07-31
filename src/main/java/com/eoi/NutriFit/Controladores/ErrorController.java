package com.eoi.NutriFit.Controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/404")
public class ErrorController {

    @GetMapping
    public String notFound() {
        return "404";
    }




}
