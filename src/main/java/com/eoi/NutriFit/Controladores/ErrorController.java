package com.eoi.NutriFit.Controladores;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador para manejar las solicitudes de errores en la aplicación.
 * Proporciona el manejo de errores y redirige a las vistas de error apropiadas
 * según el código de estado HTTP.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    /**
     * Maneja las solicitudes de error y redirige a las vistas de error correspondientes
     * según el código de estado HTTP.
     *
     * @param request El objeto {@link HttpServletRequest} que contiene la información de la solicitud.
     * @return La vista correspondiente para el error, como "404", "500" o una vista de error general.
     */
    @GetMapping
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "/error/404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "/error/500";
            }
        }
        return "/error/error";
    }
}
