package com.example.tfg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
//ID de la clase Ropa
@Embeddable
public class RopaId implements Serializable {

    //Attributes

    private static final long serialVersionUID = -4537416723958473324L;
    @Column(name = "idropa", nullable = false)
    private Integer idropa;

    @Column(name = "talla", nullable = false, length = 45)
    private String talla;

    //Getters & Setters

    public Integer getIdropa() {
        return idropa;
    }

    public void setIdropa(Integer idropa) {
        this.idropa = idropa;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    //Overrides

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RopaId entity = (RopaId) o;
        return Objects.equals(this.talla, entity.talla) &&
                Objects.equals(this.idropa, entity.idropa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(talla, idropa);
    }

    @Override
    public String toString() {
        return "RopaId{" +
                "idropa=" + idropa +
                ", talla='" + talla + '\'' +
                '}';
    }
}