package com.eoi.NutriFit.websockets;

import com.eoi.NutriFit.sendtouser.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Servicio para la gestión de notificaciones WebSocket.
 * <p>
 * La clase {@code WSService} se encarga de enviar notificaciones a los clientes a través de WebSocket.
 * Utiliza {@link SimpMessagingTemplate} para enviar mensajes a los clientes conectados y
 * {@link NotificationService} para enviar notificaciones globales y privadas.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Service
public class WSService {

    /**
     * Template para el envío de mensajes a través de WebSocket.
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Servicio de notificación para el envío de correos electrónicos y otras notificaciones.
     */
    private final NotificationService notificationService;

    /**
     * Constructor de la clase {@code WSService}.
     * <p>
     * Inicializa el {@link SimpMessagingTemplate} y {@link NotificationService} necesarios
     * para el envío de notificaciones.
     * </p>
     *
     * @param messagingTemplate Template para el envío de mensajes a través de WebSocket.
     * @param notificationService Servicio de notificación para el envío de correos electrónicos y otras notificaciones.
     */
    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    /**
     * Envía una notificación global a todos los clientes conectados.
     * <p>
     * Crea un objeto {@link ResponseMessage} con el mensaje proporcionado y lo envía a través del canal
     * WebSocket especificado para mensajes globales. También envía una notificación global a través del
     * {@link NotificationService}.
     * </p>
     *
     * @param message El mensaje que se enviará a todos los clientes conectados.
     */
    public void notifyFrontend(final String message) {
        ResponseMessage response = new ResponseMessage(message);
        notificationService.sendGlobalNotification();
        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    /**
     * Envía una notificación privada a un usuario específico.
     * <p>
     * Crea un objeto {@link ResponseMessage} con el mensaje proporcionado y lo envía al usuario
     * específico a través del canal WebSocket privado. También envía una notificación privada a través del
     * {@link NotificationService}.
     * </p>
     *
     * @param id El identificador del usuario al que se enviará el mensaje.
     * @param message El mensaje que se enviará al usuario especificado.
     */
    public void notifyUser(final String id, final String message) {
        ResponseMessage response = new ResponseMessage(message);
        notificationService.sendPrivateNotificationRest(id, message);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);
    }
}
