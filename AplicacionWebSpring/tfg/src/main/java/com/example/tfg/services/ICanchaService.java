package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Cancha;

import java.util.List;

public interface ICanchaService {

    Cancha buscarCanchaPorId(int id);

    boolean añadirCancha(Cancha cancha);

    List<Cancha> buscarCanchas();

    List<Cancha> buscarPorDeporte(String deporte);

    void actualizarCancha(Cancha cancha);

    boolean eliminarCancha(int id);

}
