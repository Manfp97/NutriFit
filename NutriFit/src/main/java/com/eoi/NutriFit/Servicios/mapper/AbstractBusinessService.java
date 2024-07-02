package com.eoi.NutriFit.Servicios.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractBusinessService <E, ID, DTO, REPO extends JpaRepository<E, ID>,
        MAPPER extends AbstractServiceMapper<E,DTO>> {
}
