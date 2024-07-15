package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository <Roles, Integer> {
}
