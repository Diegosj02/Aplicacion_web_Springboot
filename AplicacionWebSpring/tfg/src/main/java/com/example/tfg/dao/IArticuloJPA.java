package com.example.tfg.dao;

import com.example.tfg.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IArticuloJPA extends JpaRepository<Articulo,Integer> {

    //SELECT * FROM ARTICULO WHERE ARTICULO.ID = ?
    Optional<Articulo> findById(int id);

    //SELECT * FROM ARTICULO WHERE LOWER(ARTICULO.NOMBRE) LIKE LOWER(?)
    List<Articulo> findByNombreContainingIgnoreCase(String nombre);

    //SELECT * FROM ARTICULO WHERE ARTICULO.TIPO = ?
    List<Articulo> findByTipo(String tipo);

    //SELECT * FROM ARTICULO WHERE ARTICULO.precio >= ?
    List<Articulo> findByPrecioGreaterThanEqual(double precio);

    //SELECT * FROM ARTICULO WHERE ARTICULO.precio <= ?
    List<Articulo> findByPrecioLessThanEqual(double precio);

    //SELECT * FROM ARTICULO WHERE ARTICULO.precio BETWEEN ? AND ?
    List<Articulo> findByPrecioBetween(double minimo,double maximo);

    //SELECT * FROM ARTICULO WHERE tipo = 'tipo' AND precio >= precio;
    List<Articulo> findByTipoAndPrecioGreaterThanEqual(String tipo,double precio);

    //SELECT * FROM ARTICULO WHERE tipo = 'tipo' AND ARTICULO.precio <= ?
    List<Articulo> findByTipoAndPrecioLessThanEqual(String tipo,double precio);

    //SELECT * FROM ARTICULO WHERE tipo = 'tipo' AND precio BETWEEN ? AND ?;
    List<Articulo> findByTipoAndPrecioBetween(String tipo,double min,double max);


}
