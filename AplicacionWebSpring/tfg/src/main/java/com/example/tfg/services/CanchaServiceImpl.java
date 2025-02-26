package com.example.tfg.services;

import com.example.tfg.dao.ICanchaDAO;
import com.example.tfg.model.Cancha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanchaServiceImpl implements ICanchaService {

    @Autowired
    ICanchaDAO canchaDAO;

    @Override
    public List<Cancha> buscarCanchas(){
        return canchaDAO.buscarCanchas();
    }
    @Override
    public Cancha buscarCanchaPorId(int id) {
        return canchaDAO.buscarCanchaPorId(id);

    }

    @Override
    public boolean añadirCancha(Cancha cancha) {
        if (canchaDAO.buscarCanchaPorId(cancha.getId()) == null){
            canchaDAO.añadirCancha(cancha);
            return true;
        }
        return false;
    }

    @Override
    public List<Cancha> buscarPorDeporte(String deporte){
        return canchaDAO.buscarPorDeporte(deporte);
    }

    @Override
    public void actualizarCancha(Cancha cancha) {
        if (canchaDAO.buscarCanchaPorId(cancha.getId())!=null){
            canchaDAO.actualizarCancha(cancha);
        }
    }

    @Override
    public boolean eliminarCancha(int id) {
        if (canchaDAO.buscarCanchaPorId(id) != null){
            canchaDAO.eliminarCancha(id);
            return true;
        }
        return false;
    }
}
