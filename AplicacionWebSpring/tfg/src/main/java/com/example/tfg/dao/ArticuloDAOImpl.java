package com.example.tfg.dao;

import com.example.tfg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.*;

@Repository
public class ArticuloDAOImpl implements  IArticuloDAO {

    @Autowired
    IArticuloJPA articuloJPA;

    @Override
    public Articulo buscarArticuloPorId(int id) {
        Optional<Articulo> articulo = articuloJPA.findById(id);
        if (articulo.isPresent()) {
            return articulo.get();
        }
        return null;
    }

    @Override
    public List<Articulo> buscarArticuloPorNombre(String nombre) {
        return articuloJPA.findByNombreContainingIgnoreCase(nombre);

    }

    @Override
    public List<Articulo> buscarArticuloPorPrecioRango(double min, double max) {
        return articuloJPA.findByPrecioBetween(min, max);
    }

    @Override
    public List<Articulo> buscarArticuloPorPrecioMaximo(double precio) {
        return articuloJPA.findByPrecioLessThanEqual(precio);
    }

    @Override
    public List<Articulo> buscarArticuloPorPrecioMinimo(double precio) {
        return articuloJPA.findByPrecioGreaterThanEqual(precio);
    }

    @Override
    public List<Articulo> buscarArticulos() {
        return articuloJPA.findAll();
    }

    @Override
    public List<Articulo> buscarArticuloPorTipo(String tipo) {
        return articuloJPA.findByTipo(tipo);
    }

    @Override
    public void annadirArticulo(Articulo articulo) {
        articuloJPA.save(articulo);
    }

    @Override
    public void eliminarArticulo(int id) {
        articuloJPA.deleteAllById(Collections.singleton(id));
    }

    @Override
    public void actualizarArticulo(Articulo articulo) {
        articuloJPA.save(articulo);
    }

    @Override
    public List<Articulo> BuscarPorTipoYPrecioMinimo(String tipo, double precio) {
        return articuloJPA.findByTipoAndPrecioGreaterThanEqual(tipo, precio);
    }

    @Override
    public List<Articulo> buscarPorTipoYPrecioMaximo(String tipo, double precio) {
        return articuloJPA.findByTipoAndPrecioLessThanEqual(tipo, precio);
    }

    @Override
    public List<Articulo> buscarPorTipoYPrecioRango(String tipo, double min, double max) {
        return articuloJPA.findByTipoAndPrecioBetween(tipo, min, max);
    }

    @Override
    public List<Articulo> ordenarPorCriterio(String criterio) {
        List<Articulo> articulos = articuloJPA.findAll();
        for (int i = 0; i < articulos.size(); i++) {
            if (criterio.equals("alfabetico")) {
                articulos.sort(Comparator.comparing(Articulo::getNombre));
            } else if (criterio.equals("precioAsc")) {
                articulos.sort(Comparator.comparing(Articulo::getPrecio));
            } else {
                articulos.sort(Comparator.comparing(Articulo::getPrecio));
                Collections.reverse(articulos);
            }
        }
        return articulos;
    }
}
