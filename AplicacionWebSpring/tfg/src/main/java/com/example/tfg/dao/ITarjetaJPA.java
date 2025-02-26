package com.example.tfg.dao;

import com.example.tfg.model.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITarjetaJPA extends JpaRepository<Tarjeta,Integer> {

    //SELECT * FROM TARJTA WHERE TARJETA.ID = ?
    Optional<Tarjeta> findById(int id);
}
