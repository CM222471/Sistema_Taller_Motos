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
    private Categorias id_categoria;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre_producto;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "CANTIDAD_ACTUAL", nullable = false)
    private Integer cantidad;

    @Column(name = "Estado", nullable = false)
    private String estado;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

}