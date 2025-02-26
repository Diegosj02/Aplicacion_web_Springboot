package com.example.tfg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "`usuario-participa-torneo`")
public class UsuarioParticipaTorneo {

    //Attributes

    @EmbeddedId
    private UsuarioParticipaTorneoId id;

    @MapsId("idusuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario idusuario;

    @MapsId("idtorneo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtorneo", nullable = false)
    private Torneo idtorneo;

    //Getters & Setters

    public UsuarioParticipaTorneoId getId() {
        return id;
    }

    public void setId(UsuarioParticipaTorneoId id) {
        this.id = id;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public Torneo getIdtorneo() {
        return idtorneo;
    }

    public void setIdtorneo(Torneo idtorneo) {
        this.idtorneo = idtorneo;
    }

}