package com.example.tfg.dao;

import com.example.tfg.model.Cancha;
import com.example.tfg.model.Reserva;
import com.example.tfg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IReservaJPA extends JpaRepository<Reserva,Integer> {

    Optional<Reserva> findById(int id);

    List<Reserva> findByCancha(Cancha cancha);

    List<Reserva> findByFecha(LocalDate fecha);

    List<Reserva> findByResponsable(Usuario usuario);

    List<Reserva> findByCanchaAndFecha(Cancha cancha, LocalDate fecha);

}
