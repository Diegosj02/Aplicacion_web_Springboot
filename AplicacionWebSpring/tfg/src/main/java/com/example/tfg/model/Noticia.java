package com.example.tfg.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "noticia")
public class Noticia {

    //Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnoticia", nullable = false)
    private Integer id;

    @Column(name = "cabecera", nullable = false, length = 45)
    private String cabecera;

    @Column(name = "tipo", nullable = false, length = 45)
    private String tipo;

    @Column(name = "contenido", nullable = false, length = 45)
    private String contenido;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Noticia{" +
                "id=" + id +
                ", cabecera='" + cabecera + '\'' +
                ", tipo='" + tipo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}