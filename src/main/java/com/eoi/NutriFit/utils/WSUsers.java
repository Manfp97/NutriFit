package com.eoi.NutriFit.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar la lista de usuarios en el contexto de WebSocket.
 * <p>
 * La clase {@code WSUsers} maneja una lista de usuarios representados por objetos de tipo
 * {@link UserWS}. Proporciona métodos para añadir usuarios a la lista, obtener el identificador
 * único de un usuario basado en su nombre y contar el número de usuarios en la lista.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
public class WSUsers {

    /**
     * Lista que almacena objetos {@link UserWS}.
     * <p>
     * Esta lista mantiene un registro de los usuarios conectados a través de WebSocket,
     * donde cada usuario se representa con un nombre de usuario y un identificador único.
     * </p>
     */
    private List<UserWS> userWSList;

    /**
     * Constructor de la clase {@code WSUsers}.
     * <p>
     * Inicializa la lista {@code userWSList} como una nueva instancia de {@link ArrayList}
     * si es {@code null}.
     * </p>
     */
    public WSUsers() {
        if (this.userWSList == null)
            this.userWSList = new ArrayList<>();
    }

    /**
     * Añade un usuario a la lista de usuarios.
     * <p>
     * Crea un nuevo objeto {@link UserWS} con el nombre de usuario y el identificador único proporcionados
     * y lo agrega a la lista {@code userWSList}.
     * </p>
     *
     * @param username El nombre de usuario del usuario que se va a añadir.
     * @param uuid El identificador único del usuario que se va a añadir.
     */
    public void addUserWS(String username, String uuid) {
        System.out.println("addUserWS username:" + username);
        System.out.println("addUserWS uuid:" + uuid);
        userWSList.add(new UserWS(username, uuid));
    }

    /**
     * Obtiene el identificador único de un usuario basado en su nombre de usuario.
     * <p>
     * Recorre la lista {@code userWSList} para encontrar el usuario con el nombre proporcionado
     * y devuelve su identificador único.
     * </p>
     *
     * @param username El nombre de usuario del que se desea obtener el identificador único.
     * @return El identificador único del usuario, o una cadena vacía si el usuario no se encuentra en la lista.
     */
    public String getUUIdforUser(String username) {
        String uuid = "";
        System.out.println("getUUIdforUser username:" + username);
        for (UserWS itm : userWSList) {
            if (itm.getUsername().equals(username)) {
                uuid = itm.getUid();
            }
        }
        System.out.println("getUUIdforUser uuid:" + uuid);
        return uuid;
    }

    /**
     * Cuenta el número de usuarios en la lista.
     * <p>
     * Devuelve la cantidad de usuarios almacenados en {@code userWSList}.
     * </p>
     *
     * @return El número de usuarios en la lista {@code userWSList}.
     */
    public Integer countUser() {
        return userWSList.size();
    }

    /**
     * Verifica si la lista de usuarios está inicializada.
     * <p>
     * Devuelve {@code 0} si la lista {@code userWSList} es {@code null}, o {@code 1}
     * si la lista está inicializada.
     * </p>
     *
     * @return {@code 0} si {@code userWSList} es {@code null}, de lo contrario {@code 1}.
     */
    public Integer check() {
        if (this.userWSList == null)
            return 0;
        else
            return 1;
    }
}
