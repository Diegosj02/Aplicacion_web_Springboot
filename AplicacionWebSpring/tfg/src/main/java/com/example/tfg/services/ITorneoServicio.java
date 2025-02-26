package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Torneo;

import java.util.List;

public interface ITorneoServicio {

    Torneo buscarPorId(int id);

    List<Torneo> buscarTorneos();

    boolean anadirTorneo(Torneo torneo);

    void actualizarTorneo(Torneo torneo);

    boolean eliminarTorneo(int id);
}
