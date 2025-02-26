package com.example.tfg.dao;

import com.example.tfg.model.Material;
import com.example.tfg.model.Torneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TorneoDAOImpl implements ITorneoDAO{

    @Autowired
    ITorneoJPA torneoJPA;


    @Override
    public Torneo buscarTorneoPorId(int id) {
        Optional<Torneo> torneo = torneoJPA.findById(id);
        if (torneo.isPresent()){
            return torneo.get();
        }
        return null;
    }

    @Override
    public List<Torneo> buscarTorneos() {
        return torneoJPA.findAll();
    }

    @Override
    public void anadirTorneo(Torneo torneo) {
        System.out.println(torneo.toString());
        torneoJPA.save(torneo);
    }

    @Override
    public void eliminarTorneo(int id){
        torneoJPA.deleteAllById(Collections.singleton(id));
    }

    @Override
    public void actualizarTorneo(Torneo torneo){
        torneoJPA.save(torneo);
    }
}
