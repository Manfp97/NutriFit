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
class LoginController {

    UsuarioRepository usuarioRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginController(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findUsuarioByUsernameAndActivoTrue(email);
        if (optionalUsuario.isPresent() && optionalUsuario.get().getPassword().equals(bCryptPasswordEncoder.encode(password))) {
            Usuario usuario = optionalUsuario.get();
            model.addAttribute("usuario", usuario);
            model.addAttribute("msg", "Usuario encontrado");
            return "/login";
        }else{
            model.addAttribute("msg", "Usuario no encontrado");
        }
        return "redirect:/login?error=true";
    }
}