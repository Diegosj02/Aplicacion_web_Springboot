package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Noticia;

import java.util.List;

public interface INoticiaServicio {

    Noticia buscarNoticiaPorId(int id);

    List<Noticia> buscarNoticiaPorTipo(String tipo);

    List<Noticia> buscarNoticias();

    void actualizarNoticia(Noticia noticia);

    boolean eliminarNoticia(int id);

    boolean anadirNoticia(Noticia noticia);
}
