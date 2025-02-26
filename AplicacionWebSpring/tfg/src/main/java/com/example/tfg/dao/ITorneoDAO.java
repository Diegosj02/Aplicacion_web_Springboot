package com.example.tfg.dao;

import com.example.tfg.model.Torneo;
import com.example.tfg.model.Usuario;

import java.util.List;

public interface ITorneoDAO {

    Torneo buscarTorneoPorId(int id);

    List<Torneo> buscarTorneos();

    void anadirTorneo(Torneo torneo);

    void eliminarTorneo(int id);

    void actualizarTorneo(Torneo torneo);

}
