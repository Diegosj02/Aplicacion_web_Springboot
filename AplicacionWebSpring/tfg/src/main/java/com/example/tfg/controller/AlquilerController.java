package com.example.tfg.controller;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Material;
import com.example.tfg.model.Usuario;
import com.example.tfg.services.IAlquilerServicio;
import com.example.tfg.services.IMaterialService;
import com.example.tfg.services.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class AlquilerController {

    @Autowired
    IAlquilerServicio alquilerServicio;
    @Autowired
    IUsuariosService usuariosService;
    @Autowired
    IMaterialService materialService;

    @GetMapping("/alquiler/id/{id}")
    public Alquiler buscarPorId(@PathVariable("id")int id){
        return alquilerServicio.buscarPorId(id);
    }

    @GetMapping("/alquiler/usuario/{user}")
    public List<Alquiler> buscarPorResponsable(@PathVariable("user")int idUser){
        Usuario usuario = usuariosService.buscarUsuarioPorId(idUser);
        return alquilerServicio.buscarPorResponsable(usuario);
    }
    @GetMapping("/alquiler/fecha/{fecha}/hora/{hora}/material/{material}")
    public List<Alquiler> buscarPorFechaHoraMaterial(@PathVariable("fecha")LocalDate fecha, @PathVariable("hora")LocalTime hora,@PathVariable("material") int idMaterial){
        Material material = materialService.buscarMaterialPorId(idMaterial);
        return alquilerServicio.buscarPorFechaHoraMaterial(fecha,hora,material);
    }
    @GetMapping("/alquiler")
    public List<Alquiler> buscarTodos(){
        return alquilerServicio.buscarTodos();
    }

    @GetMapping("/alquiler/fecha/{fecha}")
    public List<Alquiler> buscarPorFechaHoraMaterial(@PathVariable("fecha")LocalDate fecha){
        return alquilerServicio.buscarPorFecha(fecha);
    }
    @GetMapping("/alquiler/material/{material}")
    public List<Alquiler> buscarPorFechaHoraMaterial(@PathVariable("material")int id){
        Material material = materialService.buscarMaterialPorId(id);
        return alquilerServicio.buscarPorMaterial(material);
    }

    @PostMapping(value = "/alquiler", produces = MediaType.TEXT_PLAIN_VALUE)
    public String anadirAlquiler(@RequestBody Alquiler alquiler){
        return String.valueOf(alquilerServicio.annadirAlquiler(alquiler));
    }

    @PutMapping("/alquiler")
    public void actualizarAlquiler(@RequestBody Alquiler alquiler) {
        alquilerServicio.actualizarAlquiler(alquiler);
    }

    @DeleteMapping(value = "/alquiler/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarAlquiler(@PathVariable("id") int id) {
        return String.valueOf(alquilerServicio.eliminarAlquiler(id));
    }

}
