package com.example.tfg.services;

import com.example.tfg.dao.IDescuentoDAO;
import com.example.tfg.model.Articulo;
import com.example.tfg.model.Descuento;
import com.example.tfg.model.Usuario;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescuentoServicioImpl implements IDescuentoServicio{

    @Autowired
    IDescuentoDAO descuentoDAO;

    @Override
    public Descuento buscarPorId(int id) {
        return descuentoDAO.buscarPorId(id);
    }

    @Override
    public List<Descuento> buscarPorTipo(String tipo) {
        return descuentoDAO.buscarPorTipo(tipo);
    }

    @Override
    public List<Descuento> buscarPorArticulo(Articulo articulo) {
        return descuentoDAO.buscarPorArticulo(articulo);
    }

    @Override
    public List<Descuento> buscarPorUsuario(Usuario usuario) {
        return descuentoDAO.buscarPorUsuario(usuario);
    }

    @Override
    public List<Descuento> buscarPorPrecioMin(double min) {
        return descuentoDAO.buscarPorPrecioMin(min);
    }

    @Override
    public List<Descuento> buscarPorPrecioMax(double max) {
        return descuentoDAO.buscarPorPrecioMax(max);
    }

    @Override
    public List<Descuento> buscarPorPrecioRango(double min, double max) {
        return descuentoDAO.buscarPorPrecioRango(min,max);
    }

    @Override
    public List<Descuento> buscarPorTipoYPrecioMin(String tipo, double min) {
        return descuentoDAO.buscarPorTipoYPrecioMin(tipo, min);
    }

    @Override
    public List<Descuento> buscarPorTipoYPrecioMax(String tipo, double max) {
        return descuentoDAO.buscarPorTipoYPrecioMax(tipo, max);
    }

    @Override
    public List<Descuento> buscarPorTipoYPrecioRang(String tipo, double min, double max) {
        return descuentoDAO.buscarPorTipoYPrecioRang(tipo, min, max);

    }

    @Override
    public boolean anadirDescuento(Descuento descuento){
        if (descuentoDAO.buscarPorId(descuento.getId())==null){
            descuentoDAO.anadirDescuento(descuento);
            return true;
        }
        return false;
    }

    @Override
    public List<Descuento> buscarDescuentos(){
        return descuentoDAO.buscarDescuentos();
    }

    @Override
    public boolean eliminarDescuento(int id) {
        if (descuentoDAO.buscarPorId(id) != null){
            descuentoDAO.eliminarDescuento(id);
            return true;
        }
        return false;
    }

    @Override
    public void actualizarDescuento(Descuento descuento) {
        if (descuentoDAO.buscarPorId(descuento.getId())!=null){
            descuentoDAO.actualizarDescuento(descuento);
        }
    }
}
