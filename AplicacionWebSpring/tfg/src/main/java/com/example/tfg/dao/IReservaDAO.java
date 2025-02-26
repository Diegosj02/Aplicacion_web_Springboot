package com.example.tfg.dao;

import com.example.tfg.model.Cancha;
import com.example.tfg.model.Reserva;
import com.example.tfg.model.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public interface IReservaDAO {

    List<Reserva> buscarTodas();

    Reserva buscarPorId(int id);

    List<Reserva> buscarPorFecha(LocalDate fecha);

    List<Reserva> buscarPorCancha(Cancha cancha);

    List<Reserva> buscarPorResponsable(Usuario usuario);

    List<Reserva> buscarPorCanchaYFecha(Cancha cancha, LocalDate fecha);

    void anadirReserva(Reserva reserva);
    void eliminarReserva(int id);

    void actualizarReserva(Reserva reserva);
}
