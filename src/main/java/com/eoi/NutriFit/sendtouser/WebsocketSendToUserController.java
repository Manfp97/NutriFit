package com.eoi.NutriFit.sendtouser;

import com.eoi.NutriFit.utils.UserWS;
import com.eoi.NutriFit.utils.WSUsers;
import com.eoi.NutriFit.websockets.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para manejar las comunicaciones de WebSocket con los usuarios.
 * <p>
 * Este controlador permite registrar usuarios y enviar mensajes privados a usuarios específicos
 * mediante WebSocket.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Controller
public class WebsocketSendToUserController {

    private static final WSUsers wsUsers = new WSUsers();
    private final SimpUserRegistry simpUserRegistry;

    @Autowired
    private NotificationService notificationService;

    /**
     * Constructor que inyecta el {@link SimpUserRegistry} para gestionar la lista de usuarios conectados.
     *
     * @param simpUserRegistry El registro de usuarios de Spring que mantiene la lista de usuarios conectados.
     */
    public WebsocketSendToUserController(SimpUserRegistry simpUserRegistry) {
        this.simpUserRegistry = simpUserRegistry;
    }

    /**
     * Registra un usuario en el sistema y actualiza la lista de usuarios conectados.
     * <p>
     * Este método recibe el nombre del usuario como parámetro de la ruta, lo registra en el sistema
     * y actualiza la lista interna de usuarios con su UUID.
     * </p>
     *
     * @param user El nombre del usuario a registrar.
     */
    @GetMapping("/registerwebsocket/{user}")
    @ResponseBody
    public void GetNumberOfUsers(@PathVariable("user") String user) {
        System.out.println("user");
        System.out.println(user);

        List<String> stringList = this.simpUserRegistry
                .getUsers()
                .stream()
                .map(SimpUser::getName)
                .collect(Collectors.toList());

        System.out.println(stringList.stream().toArray());
        System.out.println("size :");
        System.out.println(stringList.size());

        String uuid = "";
        for (String itm : stringList) {
            uuid = itm;
        }
        wsUsers.addUserWS(user, uuid);

        System.out.println(wsUsers.countUser());
    }

    /**
     * Maneja el envío de mensajes privados a un usuario específico.
     * <p>
     * Este método recibe un mensaje a través del WebSocket, actualiza la lista de usuarios y envía
     * la notificación al usuario especificado utilizando el {@link NotificationService}.
     * </p>
     *
     * @param message  El mensaje a enviar, que contiene el remitente, destinatario y el texto del mensaje.
     * @param principal El principal del usuario que envía el mensaje.
     * @return Un {@link OutputMessage} que confirma el envío del mensaje.
     * @throws InterruptedException Si ocurre una interrupción durante el proceso.
     */
    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public OutputMessage getPrivateMessage(final MessageTo message,
                                           final Principal principal) throws InterruptedException {
        // Actualizo la lista de usuarios
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        System.out.println("principal.getName()");
        System.out.println(principal.getName());

        Thread.sleep(100);
        notificationService.sendPrivateNotification(wsUsers.getUUIdforUser(message.getTo()).toString(), message.getText(), message.getFrom());

        return new OutputMessage(message.getFrom(), "Sending private message to user " + message.getTo() + ": "
                + message.getText(), time);
    }
}
