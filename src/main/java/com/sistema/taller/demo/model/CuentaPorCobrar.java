package com.sistema.taller.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cuentas_por_cobrar")
public class CuentaPorCobrar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes cliente;    
    private Double montoTotal;    
    private String descripcion;    
    private String numFactura;
    private Double montoPendiente;    
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;    
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado;     
    private LocalDate fechaLiquidacion; 

    public Clientes getCliente() { return cliente; }
    public void setCliente(Clientes cliente) { this.cliente = cliente; }
    
    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getNumFactura() { return numFactura; }
    public void setNumFactura(String numFactura) { this.numFactura = numFactura; }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getMontoPendiente() { return montoPendiente; }
    public void setMontoPendiente(Double montoPendiente) { this.montoPendiente = montoPendiente; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public EstadoCuenta getEstado() { return estado; }
    public void setEstado(EstadoCuenta estado) { this.estado = estado; }

    public LocalDate getFechaLiquidacion() { return fechaLiquidacion; }
    public void setFechaLiquidacion(LocalDate fechaLiquidacion) { this.fechaLiquidacion = fechaLiquidacion; }
}