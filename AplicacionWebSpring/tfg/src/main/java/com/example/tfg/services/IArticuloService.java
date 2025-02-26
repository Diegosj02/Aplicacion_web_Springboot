package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Articulo;

import java.util.List;

public interface IArticuloService {

    Articulo buscarArticuloPorId(int id);

    List<Articulo> buscarArticuloPorNombre(String nombre);

    List<Articulo> buscarArticuloPorTipo(String tipo);

    List<Articulo> buscarArticulos();

    boolean annadirArticulo(Articulo articulo);

    List<Articulo> buscarPorPrecioMinimo(double precio);

    List<Articulo> buscarPorPrecioMaximo(double precio);

    List<Articulo> buscarPorPrecioRango(double min,double max);

    void actualizarArticulo(Articulo articulo);

    boolean eliminarArticulo(int id);

    List<Articulo> buscarPorTipoYPrecioMinimo(String tipo,double precio);

    List<Articulo> buscarPorTipoYPrecioMaximo(String tipo,double precio);

    List<Articulo> buscarPorTipoYPrecioRango(String tipo,double min,double max);

    List<Articulo> ordenarPorCriterio(String criterio);

}
