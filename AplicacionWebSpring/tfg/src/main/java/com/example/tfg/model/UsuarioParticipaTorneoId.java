package com.example.tfg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

//ID de la clase UsuarioParticipaTorneo
@Embeddable
public class UsuarioParticipaTorneoId implements Serializable {

    //Attributes

    private static final long serialVersionUID = 8864753793175940811L;
    @Column(name = "idusuario", nullable = false)
    private Integer idusuario;

    @Column(name = "idtorneo", nullable = false)
    private Integer idtorneo;

    //Getters & Setters

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdtorneo() {
        return idtorneo;
    }

    public void setIdtorneo(Integer idtorneo) {
        this.idtorneo = idtorneo;
    }

    //Overrides

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsuarioParticipaTorneoId entity = (UsuarioParticipaTorneoId) o;
        return Objects.equals(this.idtorneo, entity.idtorneo) &&
                Objects.equals(this.idusuario, entity.idusuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtorneo, idusuario);
    }

}