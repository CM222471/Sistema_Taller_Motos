package com.sistema.taller.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVICIOS")

public class Servicios {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID_SERVICIO")
    private Integer idServicio;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE", nullable = false)
    private Clientes idCliente;

    @Column(name ="NOMBRE_SERVICIO", nullable = false)
    private String nombreServicio;

    @Column(name = "DESCRIPCION", nullable = false )
    private String descripcion;

    @Column(name = "PRECIO_BASE", nullable = false)
    private Double precioBase;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "ESTADO_PAGO", nullable = false )
    private String estadoPago;

    @CreationTimestamp
    @Column( name = "FECHA_SERVICIO", nullable = false)
    private LocalDateTime fechaServicio;

    @Transient // No se guarda en la base de datos
    private List<Long> productosUsados;


}
