package com.example.tfg.dao;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Articulo;
import com.example.tfg.model.Ropa;
import com.example.tfg.model.RopaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
public class RopaDAOImpl implements IRopaDAO{

    @Autowired
    IRopaJPA ropaJPA;


    @Override
    public List<Ropa> buscarPorIdArticulo(Articulo articulo) {
        return ropaJPA.findByIdropa(articulo);
    }

    @Override
    public List<Ropa> buscarTodos() {
        return ropaJPA.findAll();
    }

    @Override
    public void actualizarRopa(Ropa ropa) {
        ropaJPA.save(ropa);
    }

    @Override
    public void annadirRopa(Ropa ropa) {
        ropaJPA.save(ropa);
    }

    @Override
    public Ropa buscarPorIdPropio(RopaId idRopa) {
        Optional<Ropa> ropa = ropaJPA.findById(idRopa);
        if (ropa.isPresent()){
            return ropa.get();
        }
        return null;
    }

    @Override
    public void eliminarRopa(int id,String talla) {
        ropaJPA.deleteById_IdropaAndAndId_Talla(id,talla);
    }

}
