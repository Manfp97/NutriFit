package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Notificacion;
import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

  public List<Notificacion> findAllByToUser(String to);
  public List<Notificacion> findAllByFromUser(String from);

}