package com.example.tfg.dao;

import com.example.tfg.model.Material;
import com.example.tfg.model.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class NoticiaDAOImpl implements INoticiaDAO{

    @Autowired
    INoticiaJPA noticiaJPA;

    @Override
    public Noticia buscarNoticiaPorId(int id) {
        Optional<Noticia> noticia = noticiaJPA.findById(id);
        if (noticia.isPresent()){
            return noticia.get();
        }
        return null;
    }

    @Override
    public List<Noticia> buscaNoticiaPorTipo(String tipo) {
        return noticiaJPA.findByTipo(tipo);
    }

    @Override
    public List<Noticia> buscarNoticias(){
        return noticiaJPA.findAll();
    }

    @Override
    public void eliminarNoticia(int id){
        noticiaJPA.deleteAllById(Collections.singleton(id));
    }

    @Override
    public void actualizarNoticia(Noticia noticia){
        noticiaJPA.save(noticia);
    }
    @Override
    public void anadirNoticia(Noticia noticia){
        System.out.println(noticia.toString());
        noticiaJPA.save(noticia);
    }
}
