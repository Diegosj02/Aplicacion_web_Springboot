package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Cancha;
import com.example.tfg.model.Reserva;
import com.example.tfg.model.Usuario;

import java.time.LocalDate;
import java.util.List;

public interface IReservaServicio {

    List<Reserva> buscarTodas();
    Reserva buscarReservaPorId(int id);

    List<Reserva> buscarReservaPorCancha(int idCancha);

    List<Reserva> buscarReservaPorResponsable(int idUsuario);

    List<Reserva> buscarReservaPorCanchaYFecha(int idCancha, LocalDate fecha);

    List<Reserva> buscarPorFecha(LocalDate fecha);

    void actualizarReserva(Reserva reserva);

    boolean eliminarReserva(int id);

    boolean anadirReserva(Reserva reserva);
}
