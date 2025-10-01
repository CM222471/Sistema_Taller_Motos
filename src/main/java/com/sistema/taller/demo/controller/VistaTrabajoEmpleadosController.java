package com.sistema.taller.demo.controller;

import com.sistema.taller.demo.model.UsuarioActividad;
import com.sistema.taller.demo.service.VistaTrabajoEmpleadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class VistaTrabajoEmpleadosController {

    @Autowired
    private VistaTrabajoEmpleadosService trabajoEmpleadosService;

    @GetMapping("/trabajo-empleados")
    public ResponseEntity<List<UsuarioActividad>> obtenerTrabajoEmpleados() {
        List<UsuarioActividad> actividad = trabajoEmpleadosService.obtenerResumenTrabajoEmpleados();
        return new ResponseEntity<>(actividad, HttpStatus.OK);
    }
}