package com.example.tfg.dao;

import com.example.tfg.model.Articulo;
import com.example.tfg.model.Descuento;
import com.example.tfg.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class DescuentoDAOImpl implements IDescuentoDAO{

    @Autowired
    IDescuentoJPA descuentoJPA;


    @Override
    public Descuento buscarPorId(int id) {
        Optional<Descuento> descuento = descuentoJPA.findById(id);
        if (descuento.isPresent()){
            return descuento.get();
        }
        return null;
    }

    @Override
    public List<Descuento> buscarPorTipo(String tipo) {
        return descuentoJPA.findByTipo(tipo);
    }

    @Override
    public List<Descuento> buscarPorArticulo(Articulo articulo) {
        return descuentoJPA.findByArticulo(articulo);
    }

    @Override
    public List<Descuento> buscarPorUsuario(Usuario usuario) {
        return descuentoJPA.findByUsuario(usuario);
    }

    @Override
    public List<Descuento> buscarPorPrecioMin(double precio) {
        return descuentoJPA.findByCosteGreaterThanEqual(precio);
    }

    @Override
    public List<Descuento> buscarPorPrecioMax(double precio) {
        return descuentoJPA.findByCosteLessThanEqual(precio);
    }

    @Override
    public List<Descuento> buscarPorPrecioRango(double min, double max) {
        return descuentoJPA.findByCosteBetween(min,max);
    }

    @Override
    public List<Descuento> buscarPorTipoYPrecioMax(String tipo, double precio) {
        return descuentoJPA.findByTipoAndCosteLessThanEqual(tipo,precio);
    }

    @Override
    public List<Descuento> buscarPorTipoYPrecioMin(String tipo, double precio) {
        return descuentoJPA.findByTipoAndCosteGreaterThanEqual(tipo,precio);
    }

    @Override
    public List<Descuento> buscarPorTipoYPrecioRang(String tipo, double min, double max) {
        return descuentoJPA.findByTipoAndCosteBetween(tipo,min,max);
    }

    @Override
    public void anadirDescuento(Descuento descuento) {
        System.out.println(descuento.toString());
        descuentoJPA.save(descuento);
    }

    @Override
    public void actualizarDescuento(Descuento descuento) {
        descuentoJPA.save(descuento);
    }

    @Override
    public List<Descuento> buscarDescuentos(){
        return descuentoJPA.findAll();
    }

    @Override
    public void eliminarDescuento(int idDescuento){
        descuentoJPA.deleteAllById(Collections.singleton(idDescuento));
    }


}
