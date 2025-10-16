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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAGOS")
public class Pagos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAGO")
    private Integer idPago;

    @ManyToOne
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO" , nullable = false)
    private Servicios idServicio;

    @Column(name = "MONTO_PAGADO", nullable = false)
    private Double montoPagado;

    @Column(name = "FECHA_PAGO", nullable = false)
    private LocalDate ultimaFechaPago;

    @Column(name = "METODO_PAGO", nullable = false)
    private String metodoPago; 

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario idUsuario;

}