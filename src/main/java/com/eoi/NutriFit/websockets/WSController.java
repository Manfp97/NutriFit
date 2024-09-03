package com.eoi.NutriFit.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar las peticiones relacionadas con el chat y la mensajería.
 * <p>
 * La clase {@code WSController} expone endpoints HTTP para interactuar con el sistema de mensajería
 * y gestionar la comunicación en tiempo real a través de WebSocket.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Controller
public class WSController {

    /**
     * Servicio para la gestión de notificaciones WebSocket.
     */
    @Autowired
    private WSService service;

    /**
     * Muestra la vista de chat.
     * <p>
     * Este método maneja las peticiones GET a la ruta "/chat" y devuelve la vista del chat.
     * </p>
     *
     * @param model El objeto {@link Model} utilizado para pasar atributos a la vista.
     * @return El nombre de la vista que se renderizará, en este caso "chat".
     */
    @GetMapping({"/chat"})
    public String chat(Model model) {
        return "chat";
    }

    /**
     * Envía un mensaje a todos los clientes conectados.
     * <p>
     * Este método maneja las peticiones POST a la ruta "/send-message" y utiliza el servicio de WebSocket
     * para notificar a todos los clientes con el mensaje proporcionado.
     * </p>
     *
     * @param message El mensaje que se enviará a todos los clientes conectados. Este objeto es deserializado
     *                desde el cuerpo de la solicitud HTTP.
     */
    @PostMapping("/send-message")
    public void sendMessage(@RequestBody final Message message) {
        service.notifyFrontend(message.getText());
    }

    /**
     * Envía un mensaje privado a un usuario específico.
     * <p>
     * Este método maneja las peticiones POST a la ruta "/send-private-message/{id}" y utiliza el servicio de WebSocket
     * para notificar a un usuario específico con el mensaje proporcionado. El identificador del usuario se obtiene
     * de la ruta de la solicitud, mientras que el mensaje se deserializa desde el cuerpo de la solicitud HTTP.
     * </p>
     *
     * @param id El identificador del usuario al que se enviará el mensaje.
     * @param message El mensaje que se enviará al usuario especificado. Este objeto es deserializado
     *                desde el cuerpo de la solicitud HTTP.
     */
    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final String id,
                                   @RequestBody final Message message) {
        service.notifyUser(id, message.getText());
    }
}
