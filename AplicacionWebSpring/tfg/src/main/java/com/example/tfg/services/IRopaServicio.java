package com.example.tfg.services;

import com.example.tfg.model.Articulo;
import com.example.tfg.model.Ropa;
import com.example.tfg.model.RopaId;

import java.util.List;

public interface IRopaServicio {
    List<Ropa> buscarPorIdArticulo(Articulo articulo);

    List<Ropa> buscarTodos();

    Ropa buscarPorIdPropio(RopaId ropaId);

    void actualizarRopa(Ropa ropa);

    boolean annadirRopa(Ropa ropa);

    boolean eliminarRopa(int id, String talla);
}
