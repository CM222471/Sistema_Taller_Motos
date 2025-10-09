package com.sistema.taller.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.taller.demo.model.Servicios;

@Repository
public interface ServicioRepository extends JpaRepository<Servicios, Integer> {
    
}
