package com.example.tfg.services;

import com.example.tfg.dao.ITorneoDAO;
import com.example.tfg.model.Torneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TorneoServicioImpl implements ITorneoServicio{

    @Autowired
    ITorneoDAO torneoDAO;
    @Override
    public Torneo buscarPorId(int id) {
        return torneoDAO.buscarTorneoPorId(id);
    }

    @Override
    public List<Torneo> buscarTorneos() {
        return torneoDAO.buscarTorneos();
    }

    @Override
    public boolean anadirTorneo(Torneo torneo) {
        if (torneoDAO.buscarTorneoPorId(torneo.getId()) == null){
            torneoDAO.anadirTorneo(torneo);
            return true;
        }
        return false;
    }

    @Override
    public void actualizarTorneo(Torneo torneo) {
        if (torneoDAO.buscarTorneoPorId(torneo.getId()) != null){
            torneoDAO.actualizarTorneo(torneo);
        }
    }

    @Override
    public boolean eliminarTorneo(int id) {
        if (torneoDAO.buscarTorneoPorId(id) != null){
            torneoDAO.eliminarTorneo(id);
            return true;
        }
        return false;
    }
}
