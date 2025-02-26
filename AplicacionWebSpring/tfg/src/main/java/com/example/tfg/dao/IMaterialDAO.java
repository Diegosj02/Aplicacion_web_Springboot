package com.example.tfg.dao;

import com.example.tfg.model.Material;
import com.example.tfg.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IMaterialDAO {

    Material buscarMaterialPorId(int id);

    List<Material> buscarMaterialPorNombre(String nombre);

    void añadirMaterial(Material material);

    List<Material> buscarMateriales();

    List<Material> buscarPorDeporte(String deporte);

    List<Material> buscarPorPrecioMin(double precio);

    List<Material> buscarPorPrecioMax(double precio);

    List<Material> buscarPorPrecioRango(double min,double max);

    List<Material> ordenarPorCriterio(String criterio);

    void eliminarMaterial(int id);

    void actualizarMaterial(Material material);
}
