package com.example.tfg.dao;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Material;
import com.example.tfg.model.Usuario;
import org.hibernate.usertype.LoggableUserType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IAlquilerDAO {

    Alquiler buscarPorId(int id);

    List<Alquiler> buscarPorResponsable(Usuario usuario);

    List<Alquiler> buscarPorFechaHoraMaterial(LocalDate fecha, LocalTime hora, Material material);

    List<Alquiler> buscarTodos();

    List<Alquiler> buscarPorFecha(LocalDate fecha);
    List<Alquiler> buscarPorMaterial(Material material);

    void annadirAlquiler(Alquiler alquiler);

    void eliminarAlquiler(int id);

    void actualizarAlquiler(Alquiler alquiler);

}
