package com.example.tfg.services;

import com.example.tfg.dao.IRopaDAO;
import com.example.tfg.model.Articulo;
import com.example.tfg.model.Ropa;
import com.example.tfg.model.RopaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RopaServicioImpl implements IRopaServicio{

    @Autowired
    IRopaDAO ropaDAO;

    @Override
    public List<Ropa> buscarPorIdArticulo(Articulo articulo) {
        return ropaDAO.buscarPorIdArticulo(articulo);
    }

    @Override
    public List<Ropa> buscarTodos() {
        return ropaDAO.buscarTodos();
    }

    @Override
    public Ropa buscarPorIdPropio(RopaId ropaId) {
        return ropaDAO.buscarPorIdPropio(ropaId);
    }

    @Override
    public void actualizarRopa(Ropa ropa) {
        if (ropaDAO.buscarPorIdArticulo(ropa.getIdropa()) != null){
            ropaDAO.actualizarRopa(ropa);
        }
    }

    @Override
    public boolean annadirRopa(Ropa ropa) {
        System.out.println(ropa.getIdropa());
        System.out.println("COMPROBACION: "+ropaDAO.buscarPorIdArticulo(ropa.getIdropa()));
        if (ropaDAO.buscarPorIdPropio(ropa.getIdPropio())==null){
            System.out.println("Añadiendo");
            ropaDAO.annadirRopa(ropa);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarRopa(int id,String talla) {
        RopaId ropaId= new RopaId();
        ropaId.setIdropa(id);
        ropaId.setTalla(talla);
        if (ropaDAO.buscarPorIdPropio(ropaId)!=null){
            System.out.println(ropaId.getIdropa()+" - "+ropaId.getTalla());
            ropaDAO.eliminarRopa(id,talla);
            return true;
        }
        return false;
    }

}
