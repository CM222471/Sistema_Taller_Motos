package com.sistema.taller.demo.model;


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
@Table(name = "PRODUCTOS")
public class Productos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Integer idProducto;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA" , nullable = false)
    private Categorias idCategoria;

    @Column(name = "NOMBRE_PRODUCTO", nullable = false)
    private String nombreProducto;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "CANTIDAD_ACTUAL", nullable = false)
    private Integer stockActual; 

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

}