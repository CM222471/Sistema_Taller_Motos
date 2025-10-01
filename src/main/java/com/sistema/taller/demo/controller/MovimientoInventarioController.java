package com.sistema.taller.demo.controller;

import com.sistema.taller.demo.model.MovimientoInventario;
import com.sistema.taller.demo.service.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movimientos-inventario")
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoInventario>> listarMovimientos() {
        return new ResponseEntity<>(movimientoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventario> obtenerMovimiento(@PathVariable Long id) {
        Optional<MovimientoInventario> movimiento = movimientoService.obtenerPorId(id);
        return movimiento.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> crearMovimiento(@RequestBody MovimientoInventario movimiento) {
        try {
            MovimientoInventario nuevoMovimiento = movimientoService.crearMovimiento(movimiento);
            return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}