package com.sistema.taller.demo.repository;

import com.sistema.taller.demo.model.CuentaPorCobrar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CuentaPorCobrarRepository extends JpaRepository<CuentaPorCobrar, Long> {
    List<CuentaPorCobrar> findByEstadoIn(List<com.sistema.taller.demo.model.EstadoCuenta> estados);
}