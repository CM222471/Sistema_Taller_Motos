package com.sistema.taller.demo.controller;

import com.sistema.taller.demo.model.CuentaPorCobrar;
import com.sistema.taller.demo.service.CuentaPorCobrarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas-por-cobrar")
public class CuentasPorCobrarController {

    @Autowired
    private CuentaPorCobrarService cuentaPorCobrarService;

    @GetMapping
    public ResponseEntity<List<CuentaPorCobrar>> listarCuentas() {
        List<CuentaPorCobrar> cuentas = cuentaPorCobrarService.listarCuentasActivas();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @PutMapping("/liquidar/{id}")
    public ResponseEntity<?> liquidarCuenta(@PathVariable Long id) {
        try {
            CuentaPorCobrar cuentaLiquidada = cuentaPorCobrarService.liquidarCuenta(id);

            return new ResponseEntity<>(cuentaLiquidada, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}