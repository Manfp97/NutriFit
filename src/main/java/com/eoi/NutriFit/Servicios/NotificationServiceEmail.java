package com.eoi.NutriFit.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceEmail {

    private final EmailService emailService;

    @Autowired
    public NotificationServiceEmail(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendNotification(String userEmail, String userName) {
        String subject = "¡Bienvenido a Nutrifit, " + userName + "!";

        String body = "<html>" +
                "<body style='font-family: Arial, sans-serif; color: #333; line-height: 1.6;'>" +
                "<h2 style='color: #4CAF50;'>¡Hola, " + userName + "!</h2>" +
                "<p>Estamos encantados de darte la bienvenida a <strong>Nutrifit</strong>, tu compañero ideal para alcanzar tus metas de salud y bienestar.</p>" +
                "<p>Tu cuenta ha sido creada exitosamente y ahora puedes acceder a todas las funcionalidades que ofrecemos, desde planes personalizados de nutrición hasta entrenamientos exclusivos diseñados para ti.</p>" +
                "<h3>¿Qué puedes hacer a continuación?</h3>" +
                "<ul>" +
                "<li><strong>Explora:</strong> Navega por nuestra plataforma y descubre todas las herramientas disponibles para ti.</li>" +
                "<li><strong>Personaliza:</strong> Completa tu perfil y empieza a recibir recomendaciones adaptadas a tus necesidades.</li>" +
                "<li><strong>Inicia tu viaje:</strong> Comienza a seguir nuestros programas de nutrición y entrenamiento.</li>" +
                "</ul>" +
                "<p>Si tienes alguna pregunta o necesitas ayuda, no dudes en ponerte en contacto con nosotros. Estamos aquí para ayudarte en cada paso de tu camino hacia una vida más saludable.</p>" +
                "<p style='margin-bottom: 30px;'>¡Esperamos que disfrutes de todo lo que Nutrifit tiene para ofrecerte!</p>" +
                "<p style='font-size: 14px; color: #777;'>Atentamente,<br/>" +
                "El equipo de Nutrifit</p>" +
                "<hr style='border: 0; height: 1px; background: #ddd; margin-top: 30px;'/>" +
                "<p style='font-size: 12px; color: #777;'>Este correo fue enviado a " + userEmail + ". Si no reconoces esta actividad, por favor contáctanos inmediatamente.</p>" +
                "</body>" +
                "</html>";

        emailService.sendEmail(userEmail, subject, body);
    }
}
