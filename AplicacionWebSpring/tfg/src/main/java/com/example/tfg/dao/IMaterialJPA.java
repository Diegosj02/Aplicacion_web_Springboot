package com.example.tfg.dao;

import com.example.tfg.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IMaterialJPA extends JpaRepository<Material,Integer> {

    List<Material> findByNombreContainingIgnoreCase(String nombre);

    Optional<Material> findById(int id);
    List<Material> findByDeporte(String deporte);

    List<Material> findByPrecioGreaterThanEqual(double precio);

    List<Material> findByPrecioLessThanEqual(double precio);

    List<Material> findByPrecioBetween(double min,double max);
}
