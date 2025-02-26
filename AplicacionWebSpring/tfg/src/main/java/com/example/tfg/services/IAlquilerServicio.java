package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Material;
import com.example.tfg.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IAlquilerServicio {

    Alquiler buscarPorId(int id);

    List<Alquiler> buscarPorResponsable(Usuario usuario);

    List<Alquiler> buscarPorFechaHoraMaterial(LocalDate fecha, LocalTime hora, Material material);

    List<Alquiler> buscarTodos();

    List<Alquiler> buscarPorFecha(LocalDate fecha);

    List<Alquiler> buscarPorMaterial(Material material);

    void actualizarAlquiler(Alquiler alquiler);

    boolean eliminarAlquiler(int id);

    boolean annadirAlquiler(Alquiler alquiler);

}
