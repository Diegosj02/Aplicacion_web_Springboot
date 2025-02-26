package com.example.tfg.dao;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Material;
import com.example.tfg.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class AlquilerDAOImpl implements IAlquilerDAO{

    @Autowired
    IAlquilerJPA alquilerJPA;


    @Override
    public Alquiler buscarPorId(int id) {
        Optional<Alquiler> alquiler = alquilerJPA.findById(id);
        return alquiler.orElse(null);
    }

    @Override
    public List<Alquiler> buscarPorResponsable(Usuario usuario) {
        return alquilerJPA.findByResponsable(usuario);
    }

    @Override
    public List<Alquiler> buscarPorFechaHoraMaterial(LocalDate fecha, LocalTime hora, Material material) {
        return alquilerJPA.findByFechaAndHoraAndMaterial(fecha,hora,material);
    }

    @Override
    public List<Alquiler> buscarTodos() {
        return alquilerJPA.findAll();
    }

    @Override
    public List<Alquiler> buscarPorFecha(LocalDate fecha) {
        return alquilerJPA.findByFecha(fecha);
    }

    @Override
    public List<Alquiler> buscarPorMaterial(Material material) {
        return alquilerJPA.findByMaterial(material);
    }

    @Override
    public void annadirAlquiler(Alquiler alquiler) {
        alquilerJPA.save(alquiler);
    }

    @Override
    public void eliminarAlquiler(int id){
        alquilerJPA.deleteAllById(Collections.singleton(id));
    }

    @Override
    public void actualizarAlquiler(Alquiler alquiler){
        alquilerJPA.save(alquiler);
    }
}
