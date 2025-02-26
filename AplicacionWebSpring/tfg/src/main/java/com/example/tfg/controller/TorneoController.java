package com.example.tfg.controller;

import com.example.tfg.model.Torneo;
import com.example.tfg.model.Torneo;
import com.example.tfg.services.IArticuloService;
import com.example.tfg.services.ITorneoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TorneoController {

    @Autowired
    ITorneoServicio torneoServicio;

    @GetMapping("/torneo/id/{id}")
    public Torneo buscarTorneoPorId(@PathVariable("id")int id){
        return torneoServicio.buscarPorId(id);
    }

    @GetMapping("/torneo/all")
    public List<Torneo> buscarTorneos(){
        return torneoServicio.buscarTorneos();
    }

    @PostMapping(value = "/torneo", produces = MediaType.TEXT_PLAIN_VALUE)
    public String anadirTorneo(@RequestBody Torneo torneo){
        return String.valueOf(torneoServicio.anadirTorneo(torneo));
    }

    @PutMapping("/torneo")
    public void actualizarTorneo(@RequestBody Torneo Torneo) {
        torneoServicio.actualizarTorneo(Torneo);
    }

    @DeleteMapping(value = "/torneo/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarTorneo(@PathVariable("id") int id) {
        return String.valueOf(torneoServicio.eliminarTorneo(id));
    }
}
