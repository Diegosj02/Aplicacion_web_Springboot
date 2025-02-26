package com.example.tfg.services;

import com.example.tfg.dao.ICanchaDAO;
import com.example.tfg.dao.IReservaDAO;
import com.example.tfg.dao.IUsuarioDAO;
import com.example.tfg.model.Cancha;
import com.example.tfg.model.Reserva;
import com.example.tfg.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaServicioImpl implements IReservaServicio{

    @Autowired
    IReservaDAO reservaDAO;
    @Autowired
    ICanchaDAO canchaDAO;
    @Autowired
    IUsuarioDAO usuariosDAO;

    @Override
    public List<Reserva> buscarTodas() {
        return reservaDAO.buscarTodas();
    }

    @Override
    public Reserva buscarReservaPorId(int id) {
        return reservaDAO.buscarPorId(id);
    }

    @Override
    public List<Reserva> buscarReservaPorCancha(int id) {
        Cancha cancha = canchaDAO.buscarCanchaPorId(id);
        return reservaDAO.buscarPorCancha(cancha);

    }

    @Override
    public List<Reserva> buscarReservaPorResponsable(int id) {
        Usuario usuario = usuariosDAO.buscarUsuarioPorId(id);
        return reservaDAO.buscarPorResponsable(usuario);
    }

    @Override
    public List<Reserva> buscarReservaPorCanchaYFecha(int idCancha, LocalDate fecha) {
        Cancha cancha = canchaDAO.buscarCanchaPorId(idCancha);
        return reservaDAO.buscarPorCanchaYFecha(cancha,fecha);
    }

    @Override
    public List<Reserva> buscarPorFecha(LocalDate fecha) {
        return reservaDAO.buscarPorFecha(fecha);
    }

    @Override
    public void actualizarReserva(Reserva reserva) {
        if (reservaDAO.buscarPorId(reserva.getId()) != null){
            reservaDAO.actualizarReserva(reserva);
        }
    }

    @Override
    public boolean eliminarReserva(int id) {
        if (reservaDAO.buscarPorId(id) != null){
            reservaDAO.eliminarReserva(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean anadirReserva(Reserva reserva) {
        if (reservaDAO.buscarPorId(reserva.getId()) == null){
            reservaDAO.anadirReserva(reserva);
            return true;
        }
        return false;
    }
}
