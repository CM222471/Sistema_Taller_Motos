package com.sistema.taller.demo.repository;

import com.sistema.taller.demo.model.OrdenDeServicio;
import com.sistema.taller.demo.model.UsuarioActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenDeServicioRepository extends JpaRepository<OrdenDeServicio, Long> {
    @Query(value = "SELECT " +
            "CONCAT(u.nombres, ' ', u.apellidos) AS nombre, " +
            "COUNT(o.idOrden) AS cantidad, " +
            "MAX(o.fechaFinalizacion) AS fechaMovimiento, " +
            "CASE WHEN COUNT(o.idOrden) > 0 THEN 'FINALIZADOS' ELSE 'SIN TRABAJO' END AS tipoMovimiento " +
            "FROM OrdenDeServicio o JOIN o.mecanicoAsignado u " +
            "WHERE o.estado = 'FINALIZADA' " +
            "GROUP BY u.idUsuario, u.nombres, u.apellidos")
    List<UsuarioActividad> contarTrabajoPorUsuario();
}