package com.example.tfg.dao;

import com.example.tfg.model.Tarjeta;
import com.example.tfg.model.Usuario;

public interface ITarjetaDAO {

    Tarjeta buscarPorId(int id);

    void eliminarTarjeta(int id);

    void actualizarTarjeta(Tarjeta tarjeta);

}
