package com.sistema.taller.demo.repository;

import com.sistema.taller.demo.model.MovimientoInventario;
import com.sistema.taller.demo.model.Productos;
import com.sistema.taller.demo.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
// JpaRepository: Entidad MovimientoInventario, ID de tipo Long
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    List<MovimientoInventario> findByProducto(Productos producto);
    List<MovimientoInventario> findByTipo(TipoMovimiento tipo);
}