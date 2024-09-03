package com.eoi.NutriFit.websockets;

/**
 * Representa un mensaje de respuesta utilizado en la comunicación WebSocket.
 * <p>
 * La clase {@code ResponseMessage} encapsula un mensaje que se envía como respuesta a los clientes
 * a través del WebSocket. Este mensaje puede contener cualquier contenido relevante que deba ser
 * transmitido a los clientes conectados.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
public class ResponseMessage {

    /**
     * El contenido del mensaje de respuesta.
     */
    private String content;

    /**
     * Constructor por defecto para la clase {@code ResponseMessage}.
     * <p>
     * Este constructor inicializa un nuevo objeto {@code ResponseMessage} sin contenido.
     * </p>
     */
    public ResponseMessage() {
    }

    /**
     * Constructor para la clase {@code ResponseMessage} con contenido específico.
     * <p>
     * Este constructor inicializa un nuevo objeto {@code ResponseMessage} con el contenido proporcionado.
     * </p>
     *
     * @param content El contenido del mensaje de respuesta.
     */
    public ResponseMessage(String content) {
        this.content = content;
    }

    /**
     * Obtiene el contenido del mensaje de respuesta.
     * <p>
     * Este método devuelve el contenido del mensaje que se ha establecido previamente.
     * </p>
     *
     * @return El contenido del mensaje de respuesta.
     */
    public String getContent() {
        return content;
    }

    /**
     * Establece el contenido del mensaje de respuesta.
     * <p>
     * Este método permite establecer el contenido del mensaje que será enviado a los clientes.
     * </p>
     *
     * @param content El nuevo contenido del mensaje de respuesta.
     */
    public void setContent(String content) {
        this.content = content;
    }
}
