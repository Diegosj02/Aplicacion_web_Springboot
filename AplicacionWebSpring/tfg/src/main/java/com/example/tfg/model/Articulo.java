package com.example.tfg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "articulo")
public class Articulo {

    //Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 45)
    private String tipo;

    @Column(name = "imagen", length = 100)
    private String imagen;

    //Getters & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", precio=" + precio +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}