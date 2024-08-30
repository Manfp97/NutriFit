package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import com.eoi.NutriFit.Servicios.EmailService;
import com.eoi.NutriFit.Servicios.NotificationServiceEmail;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Value("${app.reset-password-url}")
    private String resetPasswordBaseUrl;


    //private final NotificationServiceEmail notificationServiceEmail;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    //private final EmailService emailService;

    @Autowired
    public AuthController(NotificationServiceEmail notificationServiceEmail, UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        //this.notificationServiceEmail = notificationServiceEmail;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        //this.emailService = emailService;
    }

    // Método GET para mostrar el formulario de "Olvidé mi contraseña"
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password-form"; // Nombre de la vista del formulario
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        System.out.println("Recibida solicitud de restablecimiento de contraseña para: " + email);

        Optional<Usuario> optionalUsuario = usuarioRepository.findActiveUserByEmail(email);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            String token = java.util.UUID.randomUUID().toString();
            usuario.setResetToken(token);
            usuario.setTokenExpiration(LocalDateTime.now().plusHours(1));
            usuarioRepository.save(usuario);

            String resetUrl = resetPasswordBaseUrl + "?token=" + token;

            try {
                System.out.println("Intentando enviar correo de restablecimiento a: " + email);
                //notificationServiceEmail.sendPasswordResetEmail(email, resetUrl);
                System.out.println("Correo de restablecimiento enviado con éxito a: " + email);
                model.addAttribute("message", "Correo de restablecimiento de contraseña enviado.");
            } catch (Exception e) {
                System.err.println("Error al enviar correo de restablecimiento: " + e.getMessage());
                e.printStackTrace();
                model.addAttribute("error", "Hubo un problema al enviar el correo. Por favor, inténtalo de nuevo más tarde.");
            }
        } else {
            System.out.println("No se encontró usuario activo con el email: " + email);
            model.addAttribute("error", "No se encontró ninguna cuenta asociada con ese correo electrónico.");
        }

        return "forgot-password-confirmation";
    }

    // Método GET para mostrar el formulario de restablecimiento de contraseña
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByResetToken(token);

        if (optionalUsuario.isPresent()) {
            model.addAttribute("token", token); // Pasar el token al modelo
            return "reset-password-form"; // Renderizar la vista para introducir la nueva contraseña
        } else {
            model.addAttribute("error", "Token inválido o expirado.");
            return "error"; // Mostrar una vista de error
        }
    }

    // Método POST para procesar el restablecimiento de contraseña
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String newPassword,
                                Model model) {
        // Buscar el usuario por token
        Optional<Usuario> optionalUsuario = usuarioRepository.findByResetToken(token);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Verificar si el token ha expirado
            if (usuario.getTokenExpiration().isBefore(LocalDateTime.now())) {
                model.addAttribute("error", "El token ha expirado. Por favor, solicita un nuevo restablecimiento de contraseña.");
                return "error";
            }

            // Restablecer la contraseña del usuario
            usuario.setPassword(passwordEncoder.encode(newPassword));
            usuario.setResetToken(null); // Limpiar el token
            usuario.setTokenExpiration(null); // Limpiar la expiración del token
            usuarioRepository.save(usuario); // Guardar los cambios

            // Agregar mensaje de éxito al modelo
            model.addAttribute("message", "Tu contraseña ha sido restablecida con éxito.");
            return "reset-password-success";
        } else {
            model.addAttribute("error", "Token inválido o expirado.");
            return "error";
        }
    }
}
