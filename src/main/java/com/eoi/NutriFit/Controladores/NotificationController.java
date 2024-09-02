package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Notificacion;
import com.eoi.NutriFit.Repositorios.NotificacionRepository;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notificaciones")
public class NotificationController {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    public NotificationController(NotificacionRepository notificacionRepository, UsuarioRepository usuarioRepository) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String notificaciones (Model model, @AuthenticationPrincipal UserDetails userDetails  ) {
        model.addAttribute("notificaciones", notificacionRepository.findAllByToUser(userDetails.getUsername()));
        return "notifications";
    }

    @PostMapping("/crear")
    public ResponseEntity crearNotificacion(@RequestParam("to") String to, @RequestParam("message") String message, @AuthenticationPrincipal UserDetails userDetails)
    {
        usuarioRepository.findUsuarioByUsernameAndActivoTrue(to);
        Notificacion notificacion = new Notificacion();
        notificacion.setFromUser(userDetails.getUsername());
        notificacion.setToUser(to);
        notificacion.setMessage(message);
        notificacion.setStatus("new");
        notificacion.setId(1000);
        notificacionRepository.save(notificacion);

        return new ResponseEntity<>("Notificación creada con éxito",
                HttpStatus.OK);
    }


    @GetMapping("/contar")
    public ResponseEntity contarNotificaciones(@AuthenticationPrincipal UserDetails userDetails)
    {
        List<Notificacion> notificacionList = notificacionRepository.findAllByToUser(userDetails.getUsername());
        return new ResponseEntity<>(notificacionList.size(),
                HttpStatus.OK);
    }
}
