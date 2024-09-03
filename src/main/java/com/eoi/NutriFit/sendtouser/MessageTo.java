package com.eoi.NutriFit.sendtouser;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa un mensaje que se enviará a un usuario.
 * <p>
 * Esta clase encapsula la información necesaria para la creación de un mensaje, incluyendo el remitente, el destinatario
 * y el contenido del mensaje.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Getter
@Setter
public class MessageTo {

    /**
     * El remitente del mensaje.
     * <p>
     * Este campo contiene la dirección de correo electrónico o identificador del remitente del mensaje.
     * </p>
     */
    private String from;

    /**
     * El destinatario del mensaje.
     * <p>
     * Este campo contiene la dirección de correo electrónico o identificador del destinatario del mensaje.
     * </p>
     */
    private String to;

    /**
     * El contenido del mensaje.
     * <p>
     * Este campo contiene el texto del mensaje que se enviará al destinatario.
     * </p>
     */
    private String text;
}
