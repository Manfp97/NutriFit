package com.eoi.NutriFit.websockets;

/**
 * Representa un mensaje en la comunicación WebSocket.
 * <p>
 * La clase {@code Message} encapsula la información básica de un mensaje, incluyendo el remitente
 * y el contenido del mensaje.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
public class Message {

    /**
     * El remitente del mensaje.
     */
    private String from;

    /**
     * El contenido del mensaje.
     */
    private String text;

    /**
     * Obtiene el contenido del mensaje.
     * <p>
     * Este método devuelve el texto del mensaje que se ha establecido previamente.
     * </p>
     *
     * @return El contenido del mensaje.
     */
    public String getText() {
        return text;
    }

    /**
     * Obtiene el remitente del mensaje.
     * <p>
     * Este método devuelve el nombre o identificador del remitente del mensaje.
     * </p>
     *
     * @return El remitente del mensaje.
     */
    public String getFrom() {
        return from;
    }
}
