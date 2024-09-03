package com.eoi.NutriFit.Servicios;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servicio para el envío de correos electrónicos utilizando la API de Resend.
 * <p>
 * Esta clase proporciona métodos para enviar correos electrónicos a través de la API de Resend.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Service
public class EmailService {

    private final Resend resend;

    /**
     * Constructor que inicializa el servicio de correo electrónico con la clave de API proporcionada.
     *
     * @param apiKey La clave de API para autenticar las solicitudes a la API de Resend.
     */
    public EmailService(@Value("${resend.api.key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    /**
     * Envía un correo electrónico utilizando la API de Resend.
     *
     * @param to El destinatario del correo electrónico.
     * @param subject El asunto del correo electrónico.
     * @param htmlContent El contenido HTML del correo electrónico.
     * @return El identificador del correo electrónico enviado proporcionado por la API de Resend.
     * @throws RuntimeException Si ocurre un error durante el envío del correo electrónico.
     */
    public String sendEmail(String to, String subject, String htmlContent) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Nutrifit <notificaciones@nutri-fit.es>")
                .to(to)
                .subject(subject)
                .html(htmlContent)
                .build();

        // Log email details for debugging
        System.out.println("Enviando correo a: " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Contenido HTML: " + htmlContent);

        try {
            CreateEmailResponse data = resend.emails().send(params);
            return data.getId();
        } catch (ResendException e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email", e);
        }
    }
}
