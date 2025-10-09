package com.sistema.taller.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.taller.demo.model.Servicios;
import com.sistema.taller.demo.repository.ServicioRepository;

@Service
public class ServicioService {
    
    @Autowired ServicioRepository servicioRepository;

    public List<Servicios> obtenerTodos(){
        return servicioRepository.findAll();
    }
    public Servicios guardar(Servicios servicio) {
        return servicioRepository.save(servicio);
    }

    public void eliminar(Integer id) {
        servicioRepository.deleteById(id);
    }

    public Servicios obtenerPorId(Integer id){
        return servicioRepository.findById(id).orElse(null);
    }
}
