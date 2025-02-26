package com.example.tfg.dao;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Material;
import com.example.tfg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IAlquilerJPA extends JpaRepository<Alquiler,Integer> {

    //SELECT * FROM ALQUILER WHERE ALQUILER.ID = ?

    Optional<Alquiler> findById(int id);

    //SELECT * FROM ALQUILER WHERE ALQUILER.RESPONSABLE = ?
    List<Alquiler> findByResponsable(Usuario usuario);

    //SELECT * FROM ALQUILER WHERE ALQUILER.FECHA = ?

    List<Alquiler> findByFecha(LocalDate fecha);

    //SELECT * FROM ALQUILER WHERE ALQUILER.MATERIAL = ?

    List<Alquiler> findByMaterial(Material material);

    //SELECT * FROM ALQUILER WHERE ALQUILER.FECHA = ? AND ALQUILER.HORA = ? AND ALQUILER.MATERIAL = ?
    List<Alquiler> findByFechaAndHoraAndMaterial(LocalDate fecha, LocalTime hora, Material material);

}
