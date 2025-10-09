package com.sistema.taller.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.taller.demo.model.DetalleServicio;
import com.sistema.taller.demo.repository.DetalleServicioRepository;

@Service
public class DetalleServicioService {
    
    @Autowired
    private DetalleServicioRepository DSRepository;

    public DetalleServicio guardar(DetalleServicio detalleServicio) {
        return DSRepository.save(detalleServicio);
}
}
