package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Tarjeta;

public interface ITarjetaService {

    Tarjeta buscarPorId(int id);

    void actualizarTarjeta(Tarjeta tarjeta);

    boolean eliminarTarjeta(int id);

}
