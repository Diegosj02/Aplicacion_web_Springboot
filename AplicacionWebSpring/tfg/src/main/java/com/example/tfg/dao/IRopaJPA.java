package com.example.tfg.dao;

import com.example.tfg.model.Articulo;
import com.example.tfg.model.Ropa;
import com.example.tfg.model.RopaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IRopaJPA extends JpaRepository<Ropa,Integer> {

    //SELECT * FROM ROPA WHERE ROPA.ARTICULO = ?
    List<Ropa> findByIdropa(Articulo articulo);

    //SELECT * FROM ROPA WHERE ROPA.ID = ? AND ROPA.TALLA = ?
    Optional<Ropa> findById(RopaId ropaId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Ropa r WHERE r.id.idropa = :id AND r.id.talla = :talla")
    void deleteById_IdropaAndAndId_Talla(int id,String talla);
}

