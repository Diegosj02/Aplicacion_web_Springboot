package com.example.tfg.services;

import com.example.tfg.dao.IAlquilerDAO;
import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Material;
import com.example.tfg.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AlquilerServicioImpl implements IAlquilerServicio{

    @Autowired
    IAlquilerDAO alquilerDAO;

    @Override
    public Alquiler buscarPorId(int id) {
        return alquilerDAO.buscarPorId(id);
    }

    @Override
    public List<Alquiler> buscarPorResponsable(Usuario usuario) {
        return alquilerDAO.buscarPorResponsable(usuario);
    }

    @Override
    public List<Alquiler> buscarPorFechaHoraMaterial(LocalDate fecha, LocalTime hora, Material material) {
        return alquilerDAO.buscarPorFechaHoraMaterial(fecha, hora, material);
    }

    @Override
    public List<Alquiler> buscarTodos() {
        return alquilerDAO.buscarTodos();
    }

    @Override
    public List<Alquiler> buscarPorFecha(LocalDate fecha) {
        return alquilerDAO.buscarPorFecha(fecha);
    }

    @Override
    public List<Alquiler> buscarPorMaterial(Material material) {
        return alquilerDAO.buscarPorMaterial(material);
    }

    @Override
    public void actualizarAlquiler(Alquiler alquiler){
        if (alquilerDAO.buscarPorId(alquiler.getId())!=null){
            alquilerDAO.actualizarAlquiler(alquiler);
        }
    }

    @Override
    public boolean eliminarAlquiler(int id){
        if (alquilerDAO.buscarPorId(id) != null){
            alquilerDAO.eliminarAlquiler(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean annadirAlquiler(Alquiler alquiler) {
        if (alquilerDAO.buscarPorId(alquiler.getId())==null){
            alquilerDAO.annadirAlquiler(alquiler);
            return true;
        }
        return false;
    }
}
