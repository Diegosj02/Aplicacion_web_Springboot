package com.example.tfg.dao;

import com.example.tfg.model.Articulo;
import com.example.tfg.model.Ropa;
import com.example.tfg.model.RopaId;

import java.util.List;
import java.util.Optional;

public interface IRopaDAO {
    List<Ropa> buscarPorIdArticulo(Articulo articulo);

    List<Ropa> buscarTodos();

    void actualizarRopa(Ropa ropa);

    void annadirRopa(Ropa ropa);

    Ropa buscarPorIdPropio(RopaId idRopa);

    void eliminarRopa(int id,String talla);
}
