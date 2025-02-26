package com.example.tfg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tarjeta")
public class Tarjeta {

    //Attributes

    @Id
    @Column(name = "idtarjeta", nullable = false)
    private Integer id;

    @Column(name = "saldo", nullable = false)
    private Double saldo;

    //Getters & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}