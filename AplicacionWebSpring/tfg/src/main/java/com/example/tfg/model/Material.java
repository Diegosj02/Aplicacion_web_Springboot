package com.example.tfg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "material")
public class Material {

    //Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "deporte", nullable = false)
    private String deporte;

    @Column(name="imagen",nullable = true)
    private String imagen;

    @Column(name="cantidad",nullable = false)
    private Integer cantidad;

    //Getters & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getImagen(){return imagen;}

    public void setImagen(String imagen){this.imagen=imagen;}

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}