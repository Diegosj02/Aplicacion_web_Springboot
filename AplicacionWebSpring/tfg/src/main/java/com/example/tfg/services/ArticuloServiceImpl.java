package com.example.tfg.services;

import com.example.tfg.dao.IArticuloDAO;
import com.example.tfg.model.Articulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloServiceImpl implements IArticuloService{

    @Autowired
    IArticuloDAO articuloDAO;


    @Override
    public Articulo buscarArticuloPorId(int id) {
        return articuloDAO.buscarArticuloPorId(id);
    }

    @Override
    public List<Articulo> buscarArticuloPorNombre(String nombre) {
        return articuloDAO.buscarArticuloPorNombre(nombre);

    }

    @Override
    public List<Articulo> buscarArticuloPorTipo(String tipo) {
        return articuloDAO.buscarArticuloPorTipo(tipo);

    }

    @Override
    public List<Articulo> buscarPorPrecioMaximo(double precio){
        return articuloDAO.buscarArticuloPorPrecioMaximo(precio);
    }

    @Override
    public List<Articulo> buscarPorPrecioRango(double min,double max){
        return articuloDAO.buscarArticuloPorPrecioRango(min,max);
    }

    @Override
    public void actualizarArticulo(Articulo articulo) {
        if (articuloDAO.buscarArticuloPorId(articulo.getId())!=null){
            articuloDAO.actualizarArticulo(articulo);
        }
    }

    @Override
    public boolean eliminarArticulo(int id) {
        if (articuloDAO.buscarArticuloPorId(id) != null){
            articuloDAO.eliminarArticulo(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Articulo> buscarPorTipoYPrecioMinimo(String tipo, double precio) {
        return articuloDAO.BuscarPorTipoYPrecioMinimo(tipo,precio);
    }

    @Override
    public List<Articulo> buscarPorTipoYPrecioMaximo(String tipo, double precio) {
        return articuloDAO.buscarPorTipoYPrecioMaximo(tipo, precio);
    }

    @Override
    public List<Articulo> buscarPorTipoYPrecioRango(String tipo, double min, double max) {
        return articuloDAO.buscarPorTipoYPrecioRango(tipo, min, max);
    }

    @Override
    public List<Articulo> ordenarPorCriterio(String criterio) {
        return articuloDAO.ordenarPorCriterio(criterio);
    }

    @Override
    public List<Articulo> buscarPorPrecioMinimo(double precio){
        return articuloDAO.buscarArticuloPorPrecioMinimo(precio);
    }

    @Override
    public List<Articulo> buscarArticulos(){
        return articuloDAO.buscarArticulos();
    }

    @Override
    public boolean annadirArticulo(Articulo articulo){
        System.out.println("Servicio: "+articulo.toString());
        if (articuloDAO.buscarArticuloPorId(articulo.getId())==null){
            articuloDAO.annadirArticulo(articulo);
            return true;
        }
        return false;
    }
}
