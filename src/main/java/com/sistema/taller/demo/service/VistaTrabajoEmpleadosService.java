package com.sistema.taller.demo.service;

import com.sistema.taller.demo.model.UsuarioActividad;
import com.sistema.taller.demo.repository.OrdenDeServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VistaTrabajoEmpleadosService {

    @Autowired
    private OrdenDeServicioRepository ordenDeServicioRepository;
    public List<UsuarioActividad> obtenerResumenTrabajoEmpleados() {
        return ordenDeServicioRepository.contarTrabajoPorUsuario();
    }
}