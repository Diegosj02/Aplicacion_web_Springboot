package com.example.tfg.dao;

import com.example.tfg.model.Noticia;
import com.example.tfg.model.Usuario;

import java.util.List;

public interface INoticiaDAO {

    Noticia buscarNoticiaPorId(int id);

    List<Noticia> buscaNoticiaPorTipo(String tipo);

    List<Noticia> buscarNoticias();

    void anadirNoticia(Noticia noticia);

    void eliminarNoticia(int id);

    void actualizarNoticia(Noticia noticia);

}
