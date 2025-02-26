package com.example.tfg.dao;

import com.example.tfg.model.Deportivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

@Repository
public class DeportivoDAOImpl implements IDeportivoDAO{

    @Autowired
    IDeportivoJPA deportivoJPA;

    @Override
    public Deportivo buscarPorId(int id) {
        Optional<Deportivo> deportivo = deportivoJPA.findById(id);
        if(deportivo.isPresent()){
            return deportivo.get();
        }
        return null;
    }

    @Override
    public List<Deportivo> buscarPorDeporte(String deporte) {
        return deportivoJPA.findByDeporte(deporte);
    }

    @Override
    public void actualizarDeportivo(Deportivo deportivo) {
        System.out.println(deportivo.toString());
        deportivoJPA.save(deportivo);
    }

    @Override
    public void anadirDeportivo(Deportivo deportivo) {
        System.out.println(deportivo.toString());
        deportivoJPA.save(deportivo);
    }

    @Override
    public void eliminarDeportivo(int id) {
        deportivoJPA.deleteAllById(Collections.singleton(id));
    }
}
