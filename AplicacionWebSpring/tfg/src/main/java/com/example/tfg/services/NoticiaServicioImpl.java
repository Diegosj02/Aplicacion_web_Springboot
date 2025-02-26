package com.example.tfg.services;

import com.example.tfg.dao.INoticiaDAO;
import com.example.tfg.model.Noticia;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticiaServicioImpl implements INoticiaServicio{

    @Autowired
    INoticiaDAO noticiaDao;


    @Override
    public Noticia buscarNoticiaPorId(int id) {
        return noticiaDao.buscarNoticiaPorId(id);
    }

    @Override
    public List<Noticia> buscarNoticiaPorTipo(String tipo) {
        return noticiaDao.buscaNoticiaPorTipo(tipo);
    }

    @Observed
    public List<Noticia> buscarNoticias(){return noticiaDao.buscarNoticias();}

    @Override
    public void actualizarNoticia(Noticia noticia) {
        if (noticiaDao.buscarNoticiaPorId(noticia.getId()) != null){
            noticiaDao.actualizarNoticia(noticia);
        }
    }

    @Override
    public boolean eliminarNoticia(int id) {
        if (noticiaDao.buscarNoticiaPorId(id) != null){
            noticiaDao.eliminarNoticia(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean anadirNoticia(Noticia noticia) {
        if (noticiaDao.buscarNoticiaPorId(noticia.getId()) == null){
            noticiaDao.anadirNoticia(noticia);
            return true;
        }
        return false;
    }
}
