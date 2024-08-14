package com.eoi.NutriFit.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    @GetMapping("/chats")
    public String chatPage() {
        return "chat";  // Esto buscará un template llamado chat.html
    }
}
