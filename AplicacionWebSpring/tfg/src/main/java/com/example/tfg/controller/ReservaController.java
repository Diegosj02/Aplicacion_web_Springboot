package com.example.tfg.controller;

import com.example.tfg.model.Reserva;
import com.example.tfg.model.Reserva;
import com.example.tfg.services.IReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ReservaController {

    @Autowired
    IReservaServicio reservaServicio;

    @GetMapping("/reserva/id/{id}")
    public Reserva buscarReservaPorId(@PathVariable("id")int id){
        return reservaServicio.buscarReservaPorId(id);
    }

    @GetMapping("/reserva/usuario/{user}")
    public List<Reserva> buscarReservaPorUsuario(@PathVariable("user")int idUsuario){
        return reservaServicio.buscarReservaPorResponsable(idUsuario);
    }

    @GetMapping("/reserva/cancha/{cancha}")
    public List<Reserva> buscarReservaPorCancha(@PathVariable("cancha")int idCancha){
        return reservaServicio.buscarReservaPorCancha(idCancha);
    }

    @GetMapping("/reserva")
    public List<Reserva> buscarTodas(){
        return reservaServicio.buscarTodas();
    }

    @GetMapping("/reserva/fecha/{fecha}")
    public List<Reserva> buscarReservaPorFecha(@PathVariable("fecha")LocalDate fecha){
        return reservaServicio.buscarPorFecha(fecha);
    }

    @PostMapping(value = "/reserva", produces = MediaType.TEXT_PLAIN_VALUE)
    public String anadirReserva(@RequestBody Reserva reserva){
        return String.valueOf(reservaServicio.anadirReserva(reserva));
    }

    @PutMapping("/reserva")
    public void actualizarReserva(@RequestBody Reserva Reserva) {
        reservaServicio.actualizarReserva(Reserva);
    }

    @DeleteMapping(value = "/reserva/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarReserva(@PathVariable("id") int id) {
        return String.valueOf(reservaServicio.eliminarReserva(id));
    }
    @GetMapping("/reserva/canchaFecha/{idCancha}/{fecha}")
    public List<Reserva> buscarReservaPorCanchaYFecha(@PathVariable("idCancha")int idCancha, @PathVariable("fecha")LocalDate fecha) {
        return reservaServicio.buscarReservaPorCanchaYFecha(idCancha,fecha);
    }

}
