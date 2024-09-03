package com.eoi.NutriFit.Security;

import com.eoi.NutriFit.Entidades.Usuario;

/**
 * Define el contrato para los servicios relacionados con la gestión de usuarios,
 * específicamente para el manejo de contraseñas.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
public interface IUsuarioServicio {

    /**
     * Obtiene la contraseña de un usuario codificada en un formato seguro.
     *
     * Este método se utiliza típicamente para almacenar la contraseña de forma segura
     * en la base de datos, evitando el almacenamiento de contraseñas en texto plano.
     *
     * @param usuario El usuario cuya contraseña se desea codificar.
     * @return La contraseña del usuario codificada en un formato seguro.
     */
    public String getEncodedPassword(Usuario usuario);
}