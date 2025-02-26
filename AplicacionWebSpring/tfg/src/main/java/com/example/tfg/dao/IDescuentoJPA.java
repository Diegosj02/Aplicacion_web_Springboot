package com.example.tfg.dao;

import com.example.tfg.model.Articulo;
import com.example.tfg.model.Descuento;
import com.example.tfg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDescuentoJPA extends JpaRepository<Descuento,Integer> {

    Optional<Descuento> findById(int id);

    List<Descuento> findByTipo(String tipo);

    List<Descuento> findByArticulo(Articulo articulo);

    List<Descuento> findByUsuario(Usuario usuario);

    List<Descuento> findByCosteGreaterThanEqual(double precio);

    List<Descuento> findByCosteLessThanEqual(double precio);

    List<Descuento> findByCosteBetween(double min,double max);

    List<Descuento> findByTipoAndCosteGreaterThanEqual(String tipo, double coste);

    List<Descuento> findByTipoAndCosteLessThanEqual(String tipo, double coste);

    List<Descuento> findByTipoAndCosteBetween(String tipo, double min, double max);

}
