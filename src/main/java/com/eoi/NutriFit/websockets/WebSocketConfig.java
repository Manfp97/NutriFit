package com.eoi.NutriFit.websockets;

import com.eoi.NutriFit.utils.UserHandshakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuración para el manejo de WebSocket y el broker de mensajes en la aplicación.
 * <p>
 * La clase {@code WebSocketConfig} implementa la interfaz {@link WebSocketMessageBrokerConfigurer}
 * para configurar los aspectos relacionados con la comunicación WebSocket y el broker de mensajes
 * utilizando el protocolo STOMP.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configura el broker de mensajes y los prefijos de destino de la aplicación.
     * <p>
     * Este método habilita un broker de mensajes simple que manejará los destinos que comienzan con
     * "/topic" y establece el prefijo de destino de la aplicación en "/app".
     * </p>
     *
     * @param config El objeto {@link MessageBrokerRegistry} utilizado para configurar el broker de mensajes.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registra los puntos finales de STOMP para la conexión WebSocket.
     * <p>
     * Este método agrega el punto final "/chat" para las conexiones WebSocket, permite orígenes
     * específicos para las solicitudes de conexión y configura el manejo de la mano de obra utilizando
     * {@link UserHandshakeHandler}. Además, habilita el soporte para SockJS para proporcionar
     * soporte adicional de compatibilidad de transporte.
     * </p>
     *
     * @param registry El objeto {@link StompEndpointRegistry} utilizado para registrar los puntos finales de STOMP.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOrigins(
                        "http://localhost:8091",
                        "http://localhost:3000",
                        "https://a5a6-46-6-26-62.ngrok-free.app"
                )
                .setHandshakeHandler(new UserHandshakeHandler())
                .withSockJS();
    }
}
