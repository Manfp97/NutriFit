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



/**
 * Controlador para manejar la autenticación y el restablecimiento de contraseña.
 *
 * Esta clase proporciona endpoints para el proceso de "Olvidé mi contraseña",
 * incluyendo la solicitud de restablecimiento, la validación del token y
 * el restablecimiento efectivo de la contraseña.
 *
 * @author Francisco José Conejo Barranco
 * @author Juan María Avecilla Parrilla
 * @author Manuel Fernández Pernía
 * @version 1.0
 * @since 2024-03-03
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Value("${app.reset-password-url}")
    private String resetPasswordBaseUrl;

    private final NotificationServiceEmail notificationServiceEmail;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param notificationServiceEmail Servicio para enviar notificaciones por email.
     * @param usuarioRepository Repositorio para operaciones de base de datos con usuarios.
     * @param passwordEncoder Codificador para encriptar contraseñas.
     * @param emailService Servicio para enviar emails.
     */
    @Autowired
    public AuthController(NotificationServiceEmail notificationServiceEmail, UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.notificationServiceEmail = notificationServiceEmail;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /**
     * Muestra el formulario de "Olvidé mi contraseña".
     *
     * @return El nombre de la vista del formulario.
     */
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password-form";
    }

    /**
     * Procesa la solicitud de restablecimiento de contraseña.
     *
     * Este método busca al usuario por email, genera un token de restablecimiento,
     * lo guarda en la base de datos y envía un email con el enlace para restablecer la contraseña.
     *
     * @param email El email del usuario que solicita el restablecimiento.
     * @param model El modelo para añadir atributos a la vista.
     * @return El nombre de la vista de confirmación.
     */
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
                notificationServiceEmail.sendPasswordResetEmail(email, resetUrl);
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

    /**
     * Muestra el formulario de restablecimiento de contraseña.
     *
     * @param token El token de restablecimiento.
     * @param model El modelo para añadir atributos a la vista.
     * @return El nombre de la vista del formulario de restablecimiento o de error.
     */
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByResetToken(token);

        if (optionalUsuario.isPresent()) {
            model.addAttribute("token", token);
            return "reset-password-form";
        } else {
            model.addAttribute("error", "Token inválido o expirado.");
            return "error";
        }
    }

    /**
     * Procesa el restablecimiento de contraseña.
     *
     * Este método verifica el token, comprueba su validez temporal y, si es correcto,
     * actualiza la contraseña del usuario.
     *
     * @param token El token de restablecimiento.
     * @param newPassword La nueva contraseña del usuario.
     * @param model El modelo para añadir atributos a la vista.
     * @return El nombre de la vista de éxito o de error.
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String newPassword,
                                Model model) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByResetToken(token);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            if (usuario.getTokenExpiration().isBefore(LocalDateTime.now())) {
                model.addAttribute("error", "El token ha expirado. Por favor, solicita un nuevo restablecimiento de contraseña.");
                return "error";
            }

            usuario.setPassword(passwordEncoder.encode(newPassword));
            usuario.setResetToken(null);
            usuario.setTokenExpiration(null);
            usuarioRepository.save(usuario);

            model.addAttribute("message", "Tu contraseña ha sido restablecida con éxito.");
            return "reset-password-success";
        } else {
            model.addAttribute("error", "Token inválido o expirado.");
            return "error";
        }
    }
}