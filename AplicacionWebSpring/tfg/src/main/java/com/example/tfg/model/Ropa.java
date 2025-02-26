package com.example.tfg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ropa")
public class Ropa {

    //Attributes

    @EmbeddedId
    private RopaId id;

    @MapsId("idropa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idropa", nullable = false)
    private Articulo idropa;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    //Getters & Setters

    public RopaId getIdPropio() {
        return id;
    }

    public void setIdPropio(RopaId id) {
        this.id = id;
    }

    public Articulo getIdropa() {
        return idropa;
    }

    public void setIdropa(Articulo idropa) {
        this.idropa = idropa;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}