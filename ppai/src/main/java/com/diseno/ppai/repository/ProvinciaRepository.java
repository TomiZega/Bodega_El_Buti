package com.diseno.ppai.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diseno.ppai.model.Provincia;
import com.diseno.ppai.model.RegionVitivinicola;


public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
    Optional<Provincia> findByRegionesContaining(RegionVitivinicola regionVitivinicola);
}