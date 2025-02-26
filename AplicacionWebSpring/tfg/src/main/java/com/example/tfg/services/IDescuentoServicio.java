package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Articulo;
import com.example.tfg.model.Descuento;
import com.example.tfg.model.Usuario;

import java.util.List;

public interface IDescuentoServicio {

    Descuento buscarPorId(int id);

    List<Descuento> buscarPorTipo(String tipo);

    List<Descuento> buscarPorArticulo(Articulo articulo);

    List<Descuento> buscarPorUsuario(Usuario usuario);

    List<Descuento> buscarPorPrecioMin(double min);

    List<Descuento> buscarPorPrecioMax(double max);

    List<Descuento> buscarPorPrecioRango(double min, double max);

    List<Descuento> buscarPorTipoYPrecioMin(String tipo, double min);

    List<Descuento> buscarPorTipoYPrecioMax(String tipo, double max);

    List<Descuento> buscarPorTipoYPrecioRang(String tipo, double min, double max);

    boolean anadirDescuento(Descuento descuento);

    List<Descuento> buscarDescuentos();

    boolean eliminarDescuento(int id);

    void actualizarDescuento(Descuento descuento);
}
