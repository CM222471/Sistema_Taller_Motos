package com.sistema.taller.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.taller.demo.model.Productos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.taller.demo.model.Productos;
public interface ProductoRepository extends JpaRepository<Productos, Integer>{
    List<Productos> findByIdCategoria_IdCategoria(Integer idCategoria);
}