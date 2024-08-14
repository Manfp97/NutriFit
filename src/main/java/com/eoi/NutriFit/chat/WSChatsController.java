
package com.eoi.NutriFit.chat;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WSChatsController {

    @MessageMapping("/chat")
    @SendTo("/topic/canal1")
    public Message sendMessage(Message message) {
        // Verifica que el mensaje recibido tenga el formato correcto
        System.out.println("Received message: " + message);
        return message;
    }
}