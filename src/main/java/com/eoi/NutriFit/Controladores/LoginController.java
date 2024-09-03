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

/**
 * Controlador para gestionar el proceso de inicio de sesión de usuarios.
 * Proporciona las funcionalidades de visualización del formulario de inicio de sesión y validación de credenciales.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Constructor para inicializar el controlador de inicio de sesión.
     *
     * @param usuarioRepository El repositorio de usuarios para realizar búsquedas en la base de datos.
     * @param bCryptPasswordEncoder El codificador de contraseñas para validar las credenciales del usuario.
     */
    public LoginController(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Muestra el formulario de inicio de sesión. Si hay un error en el inicio de sesión, se muestra un mensaje.
     *
     * @param error Un parámetro opcional que indica si hubo un error en el inicio de sesión.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return El nombre de la vista del formulario de inicio de sesión.
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("msg", "Usuario o contraseña incorrectos");
        }
        return "login";
    }

    /**
     * Procesa el inicio de sesión del usuario. Verifica las credenciales del usuario y redirige en función de si la autenticación es exitosa o no.
     *
     * @param email La dirección de correo electrónico del usuario para la autenticación.
     * @param password La contraseña del usuario para la autenticación.
     * @param model El modelo que se utiliza para pasar atributos a la vista.
     * @return La ruta a la que redirigir, ya sea la página principal si las credenciales son correctas o el formulario de inicio de sesión con un error.
     */
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
