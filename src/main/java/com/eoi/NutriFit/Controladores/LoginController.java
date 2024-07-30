package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;




@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginController(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("msg", "Usuario o contraseña incorrectos");
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findUsuarioByUsernameAndActivoTrue(email);
        if (optionalUsuario.isPresent() && bCryptPasswordEncoder.matches(password, optionalUsuario.get().getPassword())) {
            return "redirect:/";
        } else {
            model.addAttribute("msg", "Usuario o contraseña incorrectos");
            return "redirect:/login?error=true";
        }
    }
}