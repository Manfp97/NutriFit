package com.eoi.NutriFit.websockets;

/**
 * Representa un mensaje de salida utilizado en la comunicación WebSocket.
 * <p>
 * La clase {@code OutputMessage} encapsula un mensaje que se envía a los clientes conectados
 * a través del WebSocket, incluyendo información sobre el remitente, el contenido del mensaje
 * y la hora en que se envió.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
public class OutputMessage {

    /**
     * El remitente del mensaje.
     */
    private String from;

    /**
     * El contenido del mensaje.
     */
    private String text;

    /**
     * La hora en que se envió el mensaje.
     */
    private String time;

    /**
     * Constructor para la clase {@code OutputMessage}.
     * <p>
     * Este constructor inicializa un nuevo objeto {@code OutputMessage} con los parámetros proporcionados.
     * </p>
     *
     * @param from El remitente del mensaje.
     * @param text El contenido del mensaje.
     * @param time La hora en que se envió el mensaje.
     */
    public OutputMessage(final String from, final String text, final String time) {
        this.from = from;
        this.text = text;
        this.time = time;
    }

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
     * Obtiene la hora en que se envió el mensaje.
     * <p>
     * Este método devuelve la hora que se ha establecido previamente para el mensaje.
     * </p>
     *
     * @return La hora en que se envió el mensaje.
     */
    public String getTime() {
        return time;
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
