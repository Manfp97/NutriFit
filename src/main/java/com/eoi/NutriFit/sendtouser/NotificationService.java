package com.eoi.NutriFit.sendtouser;

import com.eoi.NutriFit.websockets.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servicio para enviar notificaciones a los usuarios a través de WebSockets.
 * <p>
 * Este servicio proporciona métodos para enviar notificaciones globales y privadas a los usuarios.
 * Utiliza el {@link SimpMessagingTemplate} para enviar mensajes a los destinos de WebSocket.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Constructor que inyecta el {@link SimpMessagingTemplate} para enviar mensajes a través de WebSocket.
     *
     * @param messagingTemplate El template de mensajería para la comunicación WebSocket.
     */
    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Envía una notificación global a todos los suscriptores del canal de notificaciones globales.
     * <p>
     * La notificación incluye un mensaje con la hora actual.
     * </p>
     */
    public void sendGlobalNotification() {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        OutputMessage message = new OutputMessage("notifications", "Global Notification", time);

        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    /**
     * Envía una notificación privada a un usuario específico.
     * <p>
     * La notificación incluye el mensaje, el remitente y la hora actual.
     * </p>
     *
     * @param userId El identificador del usuario al que se enviará la notificación.
     * @param msg    El contenido del mensaje de notificación.
     * @param from   El remitente de la notificación.
     */
    public void sendPrivateNotification(String userId, String msg, String from) {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        OutputMessage message = new OutputMessage(from, msg, time);
        messagingTemplate.convertAndSendToUser(userId, "/topic/private-messages", message);
    }

    /**
     * Envía una notificación privada a un usuario específico utilizando una ruta diferente.
     * <p>
     * La notificación incluye el mensaje y la hora actual, con un remitente predeterminado.
     * </p>
     *
     * @param userId El identificador del usuario al que se enviará la notificación.
     * @param msg    El contenido del mensaje de notificación.
     */
    public void sendPrivateNotificationRest(String userId, String msg) {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        OutputMessage message = new OutputMessage("notification", msg, time);
        messagingTemplate.convertAndSendToUser(userId, "/topic/private-notifications", message);
    }
}
