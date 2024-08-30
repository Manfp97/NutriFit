
package com.eoi.NutriFit.Servicios;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
/*
    private final Resend resend;

    public EmailService(@Value("${resend.api.key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

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
*/
}
