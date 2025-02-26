package com.example.tfg.dao;

import com.example.tfg.model.Cancha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICanchaJPA extends JpaRepository<Cancha,Integer> {

    //SELECT * FROM CANCHA WHERE ID = ?
    Optional<Cancha> findCanchaById(int id);

    //SELECT * FROM CANCHA WHERE ID = ?
    List<Cancha> findByDeporte(String deporte);
}
