package com.sistema.taller.demo.service;

import com.sistema.taller.demo.model.CuentaPorCobrar;
import com.sistema.taller.demo.model.EstadoCuenta;
import com.sistema.taller.demo.repository.CuentaPorCobrarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaPorCobrarService {

    @Autowired
    private CuentaPorCobrarRepository cuentaPorCobrarRepository;

    public List<CuentaPorCobrar> listarCuentasActivas() {
        return cuentaPorCobrarRepository.findByEstadoIn(
                Arrays.asList(EstadoCuenta.PENDIENTE, EstadoCuenta.VENCIDA));
    }

    public CuentaPorCobrar liquidarCuenta(Long id) throws Exception {
        // 1. Buscar la cuenta
        Optional<CuentaPorCobrar> cuentaOpt = cuentaPorCobrarRepository.findById(id);

        if (cuentaOpt.isEmpty()) {
            throw new Exception("Cuenta por Cobrar no encontrada con ID: " + id);
        }

        CuentaPorCobrar cuenta = cuentaOpt.get();

        // 2. Validar que la cuenta esté activa para poder liquidarla
        if (cuenta.getEstado() == EstadoCuenta.LIQUIDADA || cuenta.getEstado() == EstadoCuenta.CANCELADA) {
            throw new Exception("La cuenta ID " + id + " ya está liquidada o cancelada.");
        }

        // 3. Aplicar la liquidación
        cuenta.setEstado(EstadoCuenta.LIQUIDADA);
        cuenta.setFechaLiquidacion(LocalDate.now());
        cuenta.setMontoPendiente(0.0);

        // 4. Guardar los cambios
        return cuentaPorCobrarRepository.save(cuenta);
    }
}