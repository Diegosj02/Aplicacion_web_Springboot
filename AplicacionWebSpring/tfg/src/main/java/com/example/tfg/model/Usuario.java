package com.example.tfg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    //Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 45)
    private String correo;

    @Column(name = "`contraseña`", nullable = false, length = 45)
    private String contraseña;

    @Column(name = "telefono", nullable = false, length = 45)
    private String telefono;

    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    @Column(name = "puntosT", nullable = false)
    private Integer puntosT;

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getPuntosT() {
        return puntosT;
    }

    public void setPuntosT(Integer puntosT) {
        this.puntosT = puntosT;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", telefono='" + telefono + '\'' +
                ", puntos=" + puntos +
                ", puntosT=" + puntosT +
                '}';
    }
}