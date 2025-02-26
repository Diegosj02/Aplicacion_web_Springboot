package com.example.tfg.dao;

import com.example.tfg.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITorneoJPA extends JpaRepository<Torneo,Integer> {


    //SELECT * FROM TORNEO WHERE TORNEO.ID = ?
    Optional<Torneo> findById(int id);
}
