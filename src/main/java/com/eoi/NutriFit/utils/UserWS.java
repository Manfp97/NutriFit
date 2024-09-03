package com.eoi.NutriFit.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa la información de un usuario en el contexto de WebSocket.
 * <p>
 * La clase {@code UserWS} se utiliza para encapsular la información relevante de un usuario
 * que se necesita para las comunicaciones a través de WebSocket, incluyendo el nombre de usuario
 * y un identificador único (UID).
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
public class UserWS {

    /**
     * El nombre de usuario del usuario.
     * <p>
     * Este campo almacena el nombre de usuario que se utilizará para identificar al usuario
     * en el contexto de las comunicaciones WebSocket.
     * </p>
     */
    private String username;

    /**
     * El identificador único (UID) del usuario.
     * <p>
     * Este campo almacena un identificador único para el usuario, que puede ser utilizado
     * para realizar un seguimiento o autenticar al usuario en el contexto de WebSocket.
     * </p>
     */
    private String uid;
}
