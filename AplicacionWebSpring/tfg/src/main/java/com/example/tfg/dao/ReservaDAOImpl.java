package com.example.tfg.dao;

import com.example.tfg.model.Cancha;
import com.example.tfg.model.Material;
import com.example.tfg.model.Reserva;
import com.example.tfg.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservaDAOImpl implements IReservaDAO{

    @Autowired
    IReservaJPA reservaJPA;

    @Override
    public List<Reserva> buscarTodas() {
        return reservaJPA.findAll();
    }

    @Override
    public Reserva buscarPorId(int id) {
        Optional<Reserva> reserva = reservaJPA.findById(id);
        if(reserva.isPresent()){
            return reserva.get();
        }
        return null;
    }

    @Override
    public List<Reserva> buscarPorFecha(LocalDate fecha) {
        return reservaJPA.findByFecha(fecha);
    }

    @Override
    public List<Reserva> buscarPorCancha(Cancha cancha) {
        return reservaJPA.findByCancha(cancha);
    }

    @Override
    public List<Reserva> buscarPorResponsable(Usuario usuario) {
        return reservaJPA.findByResponsable(usuario);
    }

    @Override
    public List<Reserva> buscarPorCanchaYFecha(Cancha cancha, LocalDate fecha) {
        return reservaJPA.findByCanchaAndFecha(cancha,fecha);
    }

    @Override
    public void anadirReserva(Reserva reserva) {
        reservaJPA.save(reserva);
    }

    @Override
    public void eliminarReserva(int id){
        reservaJPA.deleteAllById(Collections.singleton(id));
    }

    @Override
    public void actualizarReserva(Reserva reserva){
        reservaJPA.save(reserva);
    }
}
