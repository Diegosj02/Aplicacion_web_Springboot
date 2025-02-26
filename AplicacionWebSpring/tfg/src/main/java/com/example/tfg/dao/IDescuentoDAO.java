package com.example.tfg.dao;

import com.example.tfg.model.Articulo;
import com.example.tfg.model.Descuento;
import com.example.tfg.model.Usuario;

import java.util.List;

public interface IDescuentoDAO {

    Descuento buscarPorId(int id);

    List<Descuento> buscarPorTipo(String tipo);

    List<Descuento> buscarPorArticulo(Articulo articulo);

    List<Descuento> buscarPorUsuario(Usuario usuario);

    List<Descuento> buscarPorPrecioMin(double precio);

    List<Descuento> buscarPorPrecioMax(double precio);

    List<Descuento> buscarPorPrecioRango(double min,double max);

    List<Descuento> buscarPorTipoYPrecioMin(String tipo, double precio);
    List<Descuento> buscarPorTipoYPrecioMax(String tipo, double precio);
    List<Descuento> buscarPorTipoYPrecioRang(String tipo,double min,double max);
    void anadirDescuento(Descuento descuento);

    void actualizarDescuento(Descuento descuento);

    List<Descuento> buscarDescuentos();

    void eliminarDescuento(int id);


}
