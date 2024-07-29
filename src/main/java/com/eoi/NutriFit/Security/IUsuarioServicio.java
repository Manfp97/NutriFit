package com.eoi.NutriFit.Security;


import com.eoi.NutriFit.Entidades.Usuario;

public interface IUsuarioServicio {
    public String getEncodedPassword(Usuario usuario);
}
