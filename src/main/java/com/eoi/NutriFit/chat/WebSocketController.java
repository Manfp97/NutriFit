package com.eoi.NutriFit.chat;



import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class WebSocketController {
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(ChatRoomService chatRoomService, SimpMessagingTemplate messagingTemplate) {
        this.chatRoomService = chatRoomService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/message/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage chat(ChatMessage message, @DestinationVariable String roomId) {
        return new ChatMessage(message.getMessage(), message.getUser());
    }

    @MessageMapping("/chat/notify/{recipientId}")
    public void sendMessageToUser(@DestinationVariable String recipientId, NotificationMessage message) {
        messagingTemplate.convertAndSend("/topic/notifications/" + recipientId, message);
    }
}