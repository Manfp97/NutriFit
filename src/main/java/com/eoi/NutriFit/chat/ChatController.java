package com.eoi.NutriFit.chat;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Servicios.UsuarioServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UsuarioServi usuarioServi;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/chat/{recipientId}")
    public String chatPage(@PathVariable String recipientId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Usuario currentUser = usuarioServi.findByUsername(currentUsername);
        String currentUserId = currentUser.getId().toString();
        String roomId = chatRoomService.createChatRoomId(currentUserId, recipientId, recipientId);

        model.addAttribute("roomId", roomId);
        model.addAttribute("currentUserId", currentUserId); // Se pasa al frontend
        model.addAttribute("recipientId", recipientId);

        // Enviar notificación al usuario destinatario
        NotificationMessage notification = new NotificationMessage(currentUserId, currentUsername, "Te ha enviado un mensaje");
        messagingTemplate.convertAndSend("/topic/notifications/" + recipientId, notification);

        return "chatroom";
    }
    @MessageMapping("/notify-user/{recipientId}")
    @SendTo("/topic/notifications/{recipientId}")
    public NotificationMessage notifyUser(NotificationMessage notificationMessage, @DestinationVariable String recipientId) {
        return notificationMessage;
    }
}
