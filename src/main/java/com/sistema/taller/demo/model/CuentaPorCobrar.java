package com.sistema.taller.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUENTAS_PENDIENTES")
public class CuentaPorCobrar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CUENTA")
    private Long idCuenta;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private Clientes cliente;

    @Column(name = "MONTO_TOTAL", nullable = false)
    private Double montoTotal;

    @Column(name = "MONTO_PAGADO", nullable = false)
    private Double montoPagado = 0.0;

    @Column(name = "SALDO_PENDIENTE", nullable = false)
    private Double saldoPendiente;

    @Column(name = "ULTIMA_FECHA_PAGO")
    private LocalDate ultimaFechaPago;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "ID_SERVICIO", nullable = false)
    private Servicios servicio;


    

    // Método auxiliar para actualizar saldo y estado automáticamente
    public void registrarPago(double monto, LocalDate fechaPago) {
        this.montoPagado += monto;
        this.saldoPendiente = this.montoTotal - this.montoPagado;
        this.ultimaFechaPago = fechaPago;

        if (this.saldoPendiente <= 0) {
            this.estado = "Completado";
            this.saldoPendiente = 0.0; // evitar valores negativos
        } else if (this.montoPagado > 0) {
            this.estado = "Parcial";
        } else {
            this.estado = "Pendiente";
        }
    }
}
