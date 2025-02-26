package com.example.tfg.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "deportivo")
public class Deportivo {

    //Attributes

    @Id
    @Column(name = "iddeportivo", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "iddeportivo", nullable = false)
    private Articulo articulo;

    @Column(name = "deporte", nullable = false, length = 45)
    private String deporte;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Deportivo{" +
                "id=" + id +
                ", articulo=" + articulo +
                ", deporte='" + deporte + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}