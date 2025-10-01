package com.sistema.taller.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ORDENES_SERVICIO")
public class OrdenDeServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MECANICO", nullable = false)
    private Usuario mecanicoAsignado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE")
    private Clientes cliente; 

    @Column(name = "DESCRIPCION_TRABAJO", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "ESTADO_ORDEN", nullable = false)
    private String estado;

    @Column(name = "FECHA_INICIO")
    private LocalDateTime fechaInicio;

    @Column(name = "FECHA_FINALIZACION")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "COSTO_TOTAL")
    private Double costoTotal;
}