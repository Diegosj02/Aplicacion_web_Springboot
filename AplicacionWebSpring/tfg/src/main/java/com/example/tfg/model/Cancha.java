package com.example.tfg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cancha")
public class Cancha {

    //Attributes

    @Id
    @Column(name = "idcancha", nullable = false)
    private Integer id;

    @Column(name = "deporte", nullable = false, length = 45)
    private String deporte;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name="imagen", nullable = true)
    private String imagen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen(){return imagen;}
    public void setImagen(String imagen){ this.imagen=imagen;}
}