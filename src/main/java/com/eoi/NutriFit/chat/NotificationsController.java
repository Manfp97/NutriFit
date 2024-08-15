package com.eoi.NutriFit.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationsController {

    @GetMapping("/notificaciones")
    public String notificationsPage() {
        return "notifications"; // Asume que `notifications.html` está en src/main/resources/templates
    }
}