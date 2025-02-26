package com.example.tfg.services;

import com.example.tfg.model.Deportivo;

import java.util.List;

public interface IDeportivoServicio {

    Deportivo buscarPorId(int id);

    List<Deportivo> buscarPorDeporte(String deporte);

    void actualizarDeportivo(Deportivo deportivo);

    boolean anadirDeportivo(Deportivo deportivo);

    boolean eliminarDeportivo(int id);
}
