package com.sistema.taller.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.taller.demo.model.DetalleServicio;

@Repository
public interface DetalleServicioRepository extends JpaRepository<DetalleServicio, Integer> {
    
}

    

    