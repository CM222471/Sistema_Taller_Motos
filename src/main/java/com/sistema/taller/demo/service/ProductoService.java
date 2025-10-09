package com.sistema.taller.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.taller.demo.model.Productos;
import com.sistema.taller.demo.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Productos> obtenerTodos(){
        return productoRepository.findAll();
    }

    public Productos guardar(Productos producto) {
    return productoRepository.save(producto);
    }

    public Productos obtenerPorId(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

     public List<Productos> obtenerPorCategoria(Integer idCategoria) {
        return productoRepository.findByIdCategoria_IdCategoria(idCategoria);
    }

    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }


}