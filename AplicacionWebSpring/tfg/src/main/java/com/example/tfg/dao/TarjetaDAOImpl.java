package com.example.tfg.dao;

import com.example.tfg.model.Material;
import com.example.tfg.model.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

@Repository
public class TarjetaDAOImpl implements ITarjetaDAO{

    @Autowired
    ITarjetaJPA tarjetaJPA;

    @Override
    public Tarjeta buscarPorId(int id) {
        Optional<Tarjeta> tarjeta= tarjetaJPA.findById(id);
        if (tarjeta.isPresent()){
            return tarjeta.get();
        }
        return null;
    }

    @Override
    public void eliminarTarjeta(int id){
        tarjetaJPA.deleteAllById(Collections.singleton(id));
    }

    @Override
    public void actualizarTarjeta(Tarjeta tarjeta){
        tarjetaJPA.save(tarjeta);
    }
}
