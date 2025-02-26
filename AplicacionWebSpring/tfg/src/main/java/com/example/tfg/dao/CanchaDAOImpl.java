package com.example.tfg.dao;

import com.example.tfg.model.Cancha;
import com.example.tfg.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CanchaDAOImpl implements ICanchaDAO {

    @Autowired
    ICanchaJPA canchaJPA;

    @Override
    public List<Cancha> buscarCanchas(){
        return canchaJPA.findAll();
    }

    @Override
    public Cancha buscarCanchaPorId(int id) {
        Optional<Cancha> cancha = canchaJPA.findCanchaById(id);
        if (cancha.isPresent()){
            return cancha.get();
        }
        return null;
    }

    @Override
    public void añadirCancha(Cancha cancha) {
        canchaJPA.save(cancha);
    }

    @Override
    public List<Cancha> buscarPorDeporte(String deporte){
        return canchaJPA.findByDeporte(deporte);
    }

    @Override
    public void eliminarCancha(int id){
        canchaJPA.deleteAllById(Collections.singleton(id));
    }

    @Override
    public void actualizarCancha(Cancha cancha){
        canchaJPA.save(cancha);
    }
}
