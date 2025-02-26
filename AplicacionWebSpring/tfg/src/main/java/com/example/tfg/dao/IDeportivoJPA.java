package com.example.tfg.dao;

import com.example.tfg.model.Deportivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDeportivoJPA extends JpaRepository<Deportivo,Integer> {

    //SELECT * FROM DEPORTIVO WHERE ID = ?
    Optional<Deportivo> findById(int id);

    //SELECT * FROM DEPORTIVO WHERE DEPORTE = ?
    List<Deportivo> findByDeporte(String deporte);

}
