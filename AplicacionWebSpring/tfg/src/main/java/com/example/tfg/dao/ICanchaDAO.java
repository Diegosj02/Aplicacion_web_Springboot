package com.example.tfg.dao;

import com.example.tfg.model.Cancha;
import com.example.tfg.model.Material;

import java.util.List;

public interface ICanchaDAO {

    Cancha buscarCanchaPorId(int id);



    List<Cancha> buscarCanchas();

    List<Cancha> buscarPorDeporte(String deporte);

    void añadirCancha(Cancha cancha);

    void eliminarCancha(int id);

    void actualizarCancha(Cancha cancha);
}
