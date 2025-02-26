package com.example.tfg.controller;

import com.example.tfg.model.Deportivo;
import com.example.tfg.model.Tarjeta;
import com.example.tfg.services.IDeportivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeportivoController {

    @Autowired
    IDeportivoServicio deportivoServicio;

    @GetMapping("/deportivo/id/{id}")
    public Deportivo buscarPorId(@PathVariable("id")int id){
        return deportivoServicio.buscarPorId(id);
    }

    @GetMapping("/deportivo/deporte/{deporte}")
    public List<Deportivo> buscarPorDeportivo(@PathVariable("deporte")String deporte){
        return deportivoServicio.buscarPorDeporte(deporte);
    }

    @PutMapping("/deportivo")
    public void actualizarDeportivo(@RequestBody Deportivo deportivo) {
        deportivoServicio.actualizarDeportivo(deportivo);
    }

    @PostMapping(value = "/deportivo", produces = MediaType.TEXT_PLAIN_VALUE)
    public String anadirDeportivo( @RequestBody Deportivo deportivo){
        return String.valueOf(deportivoServicio.anadirDeportivo(deportivo));

    }
    @DeleteMapping(value="/deportivo/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarDeportivo(@PathVariable("id")int id){
        return String.valueOf(deportivoServicio.eliminarDeportivo(id));
    }

}
