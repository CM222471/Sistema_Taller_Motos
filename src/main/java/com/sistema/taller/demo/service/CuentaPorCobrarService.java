package com.sistema.taller.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.taller.demo.model.CuentaPorCobrar;
import com.sistema.taller.demo.repository.CuentaPorCobrarRepository;

@Service
public class CuentaPorCobrarService {

    @Autowired
    private CuentaPorCobrarRepository cuentaPorCobrarRepository;

    public List<CuentaPorCobrar> obtenerTodos(){
        return cuentaPorCobrarRepository.findAll();
    }
    public CuentaPorCobrar guardar(CuentaPorCobrar cuenta) {
        return cuentaPorCobrarRepository.save(cuenta);
    }

    public void eliminar(Integer id) {
        cuentaPorCobrarRepository.deleteById(id);
    }

    public CuentaPorCobrar obtenerPorId(Integer id){
        return cuentaPorCobrarRepository.findById(id).orElse(null);
    }

    
}