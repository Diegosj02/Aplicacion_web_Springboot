package com.example.tfg.dao;


import com.example.tfg.model.Articulo;
import com.example.tfg.model.Material;

import java.util.List;
import java.util.Optional;

public interface IArticuloDAO {

    Articulo buscarArticuloPorId(int id);

    List<Articulo> buscarArticuloPorNombre(String nombre);

    List<Articulo> buscarArticuloPorTipo(String tipo);

    List<Articulo> buscarArticulos();

    List<Articulo> buscarArticuloPorPrecioMinimo(double precio);

    List<Articulo> buscarArticuloPorPrecioMaximo(double precio);

    List<Articulo> buscarArticuloPorPrecioRango(double min, double max);

    void annadirArticulo(Articulo articulo);

    void eliminarArticulo(int id);

    void actualizarArticulo(Articulo articulo);

    List<Articulo> BuscarPorTipoYPrecioMinimo(String tipo,double precio);

    List<Articulo> buscarPorTipoYPrecioMaximo(String tipo,double precio);

    List<Articulo> buscarPorTipoYPrecioRango(String tipo,double min,double max);

    List<Articulo> ordenarPorCriterio(String criterio);

}


