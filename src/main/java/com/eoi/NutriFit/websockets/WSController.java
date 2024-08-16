package com.eoi.NutriFit.websockets;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WSController {

    @Autowired
    private WSService service;

    @GetMapping({"/chat"})
    public String chat(Model model) {
        return "chat";
    }

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody final Message message) {
        service.notifyFrontend(message.getText());
    }

    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final String id,
                                   @RequestBody final Message message) {
        service.notifyUser(id, message.getText());
    }
}