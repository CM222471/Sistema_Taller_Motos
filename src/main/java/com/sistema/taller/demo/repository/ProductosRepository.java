package com.sistema.taller.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema.taller.demo.model.Productos;
@Repository 
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
    List<Productos> findByNombreProductoContainingIgnoreCase(String nombre_producto);

    @Query("SELECT p.idCategoria.nombre, SUM(p.stockActual) " +
       "FROM Productos p " +
       "GROUP BY p.idCategoria.nombre")
List<Object[]> obtenerStockPorCategoria();

    public List<Productos> findByIdCategoria_IdCategoria(Integer idCategoria);
}