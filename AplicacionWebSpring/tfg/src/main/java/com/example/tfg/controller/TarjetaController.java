package com.example.tfg.controller;

import com.example.tfg.dao.ITarjetaDAO;
import com.example.tfg.model.Tarjeta;
import com.example.tfg.model.Tarjeta;
import com.example.tfg.services.ITarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class TarjetaController  {

    @Autowired
    ITarjetaService tarjetaService;

    @GetMapping("/tarjeta/id/{id}")
    public Tarjeta buscarPorId(@PathVariable("id")int id){
        return tarjetaService.buscarPorId(id);
    }


    @PutMapping("/tarjeta")
    public void actualizarTarjeta(@RequestBody Tarjeta Tarjeta) {
        tarjetaService.actualizarTarjeta(Tarjeta);
    }

    @DeleteMapping(value = "/tarjeta/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarTarjeta(@PathVariable("id") int id) {
        return String.valueOf(tarjetaService.eliminarTarjeta(id));
    }



}
