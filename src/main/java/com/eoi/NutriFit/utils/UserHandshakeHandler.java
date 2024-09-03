package com.eoi.NutriFit.utils;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

/**
 * Manejador de la autenticación para las conexiones WebSocket.
 * <p>
 * Esta clase extiende {@link DefaultHandshakeHandler} para proporcionar una implementación personalizada
 * que asigna un identificador único a cada usuario que se conecta a través de WebSocket. El identificador
 * se genera como un UUID y se utiliza como el nombre del principal del usuario.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);

    /**
     * Determina el principal del usuario durante el proceso de handshake para WebSocket.
     * <p>
     * Este método se invoca durante el handshake de WebSocket para determinar el usuario que se está conectando.
     * En esta implementación, se genera un UUID único para cada conexión y se utiliza como el nombre del
     * {@link Principal} del usuario.
     * </p>
     *
     * @param request La solicitud HTTP que se utiliza para el handshake de WebSocket.
     * @param wsHandler El manejador de WebSocket asociado a la conexión.
     * @param attributes Atributos asociados a la conexión de WebSocket.
     * @return Un {@link Principal} que representa al usuario conectado, con un UUID único como nombre.
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        final String randomId = UUID.randomUUID().toString();
        LOG.info("User with ID '{}' opened the page", randomId);
        System.out.println("User with ID " + randomId + " opened the page");
        return new UserPrincipal(randomId);
    }
}
