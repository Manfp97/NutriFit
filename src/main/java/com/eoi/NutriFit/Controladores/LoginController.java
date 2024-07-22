package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;
import java.util.Optional;

@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    String login(Locale locale) {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findBynombreDeUsuario(email);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (usuario.getPassword().equals(password)) {
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
                try {
                    Authentication auth = authenticationManager.authenticate(authRequest);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    model.addAttribute("usuario", usuario);
                    model.addAttribute("msg", "Usuario encontrado");
                    return "redirect:/";
                } catch (AuthenticationException ex) {
                    model.addAttribute("msg", "Usuario no encontrado");
                }
            } else {
                model.addAttribute("msg", "Usuario no encontrado");
            }
        } else {
            model.addAttribute("msg", "Usuario no encontrado");
        }

        return "redirect:/login?error=true";
    }
}