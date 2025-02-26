package com.example.tfg.controller;

import com.example.tfg.model.Noticia;
import com.example.tfg.model.Noticia;
import com.example.tfg.services.INoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class NoticiaController {

    @Autowired
    INoticiaServicio noticiaServicio;

    @GetMapping("/noticia/id/{id}")
    public Noticia buscarNoticiaPorId(@PathVariable ("id")int id){
        return noticiaServicio.buscarNoticiaPorId(id);
    }

    @GetMapping("/noticia/tipo/{tipo}")
    public List<Noticia> buscarNoticiaPorTipo(@PathVariable("tipo")String tipo){
        return noticiaServicio.buscarNoticiaPorTipo(tipo);
    }

    @GetMapping("/noticia")
    public List<Noticia> buscarNoticias(){
        List<Noticia> noticias=noticiaServicio.buscarNoticias();
        Collections.reverse(noticias);
        return noticias;
    }
    
    @PostMapping(value = "/noticia", produces = MediaType.TEXT_PLAIN_VALUE)
    public String anadirNoticia(@RequestBody Noticia Noticia){
        return String.valueOf(noticiaServicio.anadirNoticia(Noticia));
    }

    @PutMapping("/noticia")
    public void actualizarNoticia(@RequestBody Noticia Noticia) {
        noticiaServicio.actualizarNoticia(Noticia);
    }

    @DeleteMapping(value = "/noticia/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarNoticia(@PathVariable("id") int id) {
        return String.valueOf(noticiaServicio.eliminarNoticia(id));
    }
}
