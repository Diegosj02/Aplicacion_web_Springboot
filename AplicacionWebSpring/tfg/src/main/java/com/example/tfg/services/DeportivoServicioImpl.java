package com.example.tfg.services;

import com.example.tfg.dao.IDeportivoDAO;
import com.example.tfg.model.Deportivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeportivoServicioImpl implements IDeportivoServicio{

    @Autowired
    IDeportivoDAO deportivoDAO;


    @Override
    public Deportivo buscarPorId(int id) {
        return deportivoDAO.buscarPorId(id);
    }

    @Override
    public List<Deportivo> buscarPorDeporte(String deporte) {
        return deportivoDAO.buscarPorDeporte(deporte);
    }

    @Override
    public void actualizarDeportivo(Deportivo deportivo) {
        if (deportivoDAO.buscarPorId(deportivo.getId()) != null){
            deportivoDAO.actualizarDeportivo(deportivo);
        }
    }

    @Override
    public boolean anadirDeportivo(Deportivo deportivo) {
        if (deportivoDAO.buscarPorId(deportivo.getId())==null){
            deportivoDAO.anadirDeportivo(deportivo);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarDeportivo(int id) {
        if (deportivoDAO.buscarPorId(id)!=null){
            deportivoDAO.eliminarDeportivo(id);
            return true;
        }
        return false;
    }
}
