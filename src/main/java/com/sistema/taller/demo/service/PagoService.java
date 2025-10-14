package com.sistema.taller.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.taller.demo.model.Pagos;
import com.sistema.taller.demo.repository.PagoRepository;

@Service
public class PagoService {
    
    @Autowired PagoRepository pagoRepository;
    public List<Pagos> obtenerTodos(){
        return pagoRepository.findAll();
    }
    public Pagos guardar(Pagos pago) {
        return pagoRepository.save(pago);
    }

    public void eliminar(Integer id) {
        pagoRepository.deleteById(id);
    }

    public Pagos obtenerPorId(Integer id){
        return pagoRepository.findById(id).orElse(null);
    }
}
