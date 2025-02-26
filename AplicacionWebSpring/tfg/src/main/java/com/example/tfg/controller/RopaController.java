package com.example.tfg.controller;

import com.example.tfg.model.*;
import com.example.tfg.services.IArticuloService;
import com.example.tfg.services.IRopaServicio;
import com.example.tfg.services.RopaServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RopaController {

    @Autowired
    IRopaServicio ropaServicio;
    @Autowired
    IArticuloService articuloService;

    @GetMapping("/ropa/id/{id}")
    public List<Ropa> buscarPorId(@PathVariable("id")int id){
        Articulo articulo = articuloService.buscarArticuloPorId(id);
        return ropaServicio.buscarPorIdArticulo(articulo);
    }

    @GetMapping("/ropa")
    public List<Ropa> buscarTodos(){
        return ropaServicio.buscarTodos();
    }

    @PutMapping("/ropa")
    public void actualizarRopa(@RequestBody Ropa ropa) {
        ropaServicio.actualizarRopa(ropa);
    }

    @PostMapping(value = "/ropa", produces = MediaType.TEXT_PLAIN_VALUE)
    public String anadirRopa(@RequestBody Ropa ropa){
        ropa.setIdropa(articuloService.buscarArticuloPorId(ropa.getIdPropio().getIdropa()));
        return String.valueOf(ropaServicio.annadirRopa(ropa));
    }
    @DeleteMapping(value = "/ropa/{id}/{talla}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarRopa(@PathVariable("id") int id, @PathVariable("talla") String talla) {
        return String.valueOf(ropaServicio.eliminarRopa(id,talla));
    }
}
