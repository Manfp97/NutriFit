package com.eoi.NutriFit.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controlador que maneja la comunicación de chat a través de WebSocket.
 * <p>
 * La clase {@code ChatController} gestiona los mensajes de chat recibidos y los envía a los suscriptores
 * del canal de mensajes. Utiliza el protocolo STOMP sobre WebSocket para la comunicación en tiempo real.
 * </p>
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Controller
public class ChatController {

    /**
     * Maneja los mensajes de chat recibidos y los envía a todos los suscriptores del canal de mensajes.
     * <p>
     * Este método es llamado cuando un mensaje es enviado al endpoint {@code /chat}. El mensaje es procesado
     * para incluir la hora actual y luego se envía a los suscriptores del canal {@code /topic/messages}.
     * </p>
     *
     * @param message El mensaje recibido del cliente, que contiene el remitente y el contenido del mensaje.
     * @return Un {@code OutputMessage} que incluye el remitente, el contenido del mensaje y la hora en que
     *         se envió el mensaje.
     * @throws Exception Si ocurre un error al procesar el mensaje.
     */
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(final Message message) throws Exception {

        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

}
