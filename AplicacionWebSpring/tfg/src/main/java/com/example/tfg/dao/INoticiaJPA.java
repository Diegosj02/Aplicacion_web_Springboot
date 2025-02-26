package com.example.tfg.dao;

import com.example.tfg.model.Noticia;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface INoticiaJPA extends JpaRepository<Noticia,Integer> {

    Optional<Noticia> findById(int id);

    List<Noticia> findByTipo(String tipo);

}
