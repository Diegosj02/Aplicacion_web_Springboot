package com.example.tfg.controller;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Cancha;
import com.example.tfg.services.CanchaServiceImpl;
import com.example.tfg.services.ICanchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CanchaController {
    @Autowired
    ICanchaService canchaService;

    @GetMapping("/cancha/{id}")
    public Cancha buscarCanchaPorId(@PathVariable("id")int id){
        return canchaService.buscarCanchaPorId(id);
    }

    @GetMapping("/cancha")
    public List<Cancha> buscarCanchas(){
        return canchaService.buscarCanchas();
    }

    @GetMapping("/cancha/deporte/{deporte}")
    public List<Cancha> buscarCanchaPorDeporte(@PathVariable("deporte")String deporte){
        return canchaService.buscarPorDeporte(deporte);
    }

    @PostMapping(value="/cancha", produces = MediaType.TEXT_PLAIN_VALUE)
    public String añadirCancha(@RequestBody Cancha cancha){
        return String.valueOf(canchaService.añadirCancha(cancha));
    }

    @PutMapping("/cancha")
    public void actualizarCancha(@RequestBody Cancha cancha) {
        canchaService.actualizarCancha(cancha);
    }

    @DeleteMapping(value = "/cancha/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarCancha(@PathVariable("id") int id) {
        return String.valueOf(canchaService.eliminarCancha(id));
    }
}
