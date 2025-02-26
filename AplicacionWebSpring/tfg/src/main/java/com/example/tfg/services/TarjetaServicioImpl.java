package com.example.tfg.services;

import com.example.tfg.dao.ITarjetaDAO;
import com.example.tfg.model.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaServicioImpl implements ITarjetaService{

    @Autowired
    ITarjetaDAO tarjetaDAO;

    @Override
    public Tarjeta buscarPorId(int id) {
        return tarjetaDAO.buscarPorId(id);
    }

    @Override
    public void actualizarTarjeta(Tarjeta tarjeta) {
        if (tarjetaDAO.buscarPorId(tarjeta.getId()) != null){
            tarjetaDAO.actualizarTarjeta(tarjeta);
        }
    }

    @Override
    public boolean eliminarTarjeta(int id) {
        if (tarjetaDAO.buscarPorId(id) != null){
            tarjetaDAO.eliminarTarjeta(id);
            return true;
        }
        return false;
    }
}
