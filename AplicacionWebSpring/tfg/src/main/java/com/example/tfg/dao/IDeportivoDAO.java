package com.example.tfg.dao;

import com.example.tfg.model.Deportivo;

import java.util.List;

public interface IDeportivoDAO {

    Deportivo buscarPorId(int id);

    List<Deportivo> buscarPorDeporte(String deporte);

    void actualizarDeportivo(Deportivo deportivo);

    void anadirDeportivo(Deportivo deportivo);
    void eliminarDeportivo(int id);

}
