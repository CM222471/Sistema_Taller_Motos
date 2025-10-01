package com.sistema.taller.demo.service;

import com.sistema.taller.demo.model.MovimientoInventario;
import com.sistema.taller.demo.model.Productos;
import com.sistema.taller.demo.model.TipoMovimiento;
import com.sistema.taller.demo.repository.MovimientoInventarioRepository;
import com.sistema.taller.demo.repository.ProductosRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository movimientoRepository;
    
    @Autowired
    private ProductosRepository productoRepository;

    /**
     * Registra un nuevo movimiento y actualiza el stock del producto.
     */
    @Transactional // Asegura que ambas operaciones (guardar mov. y actualizar stock) sean atómicas
    public MovimientoInventario crearMovimiento(MovimientoInventario movimiento) throws Exception {
        
        // CORRECCIÓN 1: Usar getIdProducto() en lugar de getId()
        Productos producto = movimiento.getProducto();
        if (producto == null || producto.getIdProducto() == null) {
            throw new Exception("El movimiento debe estar asociado a un producto existente.");
        }
        
        // CORRECCIÓN 2: El findById usa el Integer idProducto
        Integer productoId = producto.getIdProducto();
        Optional<Productos> productoOpt = productoRepository.findById(productoId);
        
        if (productoOpt.isEmpty()) {
            throw new Exception("Producto no encontrado con ID: " + productoId);
        }
        
        Productos productoActual = productoOpt.get();
        Integer cantidad = movimiento.getCantidad();
        
        // CORRECCIÓN 3: Usar getStockActual() (gracias a Lombok)
        Integer nuevoStock = productoActual.getStockActual() != null ? productoActual.getStockActual() : 0;

        if (movimiento.getTipo() == TipoMovimiento.ENTRADA) {
            nuevoStock += cantidad;
        } else if (movimiento.getTipo() == TipoMovimiento.SALIDA) {
            if (nuevoStock < cantidad) {
                throw new Exception("Stock insuficiente para la salida. Stock actual: " + nuevoStock);
            }
            nuevoStock -= cantidad;
        }

        // 1. Actualizar el stock (usando setStockActual() de Lombok)
        productoActual.setStockActual(nuevoStock);
        productoRepository.save(productoActual);

        // 2. Guardar el movimiento
        return movimientoRepository.save(movimiento);
    }
    
    // Métodos CRUD básicos
    
    public List<MovimientoInventario> listarTodos() {
        return movimientoRepository.findAll();
    }
    
    public Optional<MovimientoInventario> obtenerPorId(Long id) {
        return movimientoRepository.findById(id);
    }
}