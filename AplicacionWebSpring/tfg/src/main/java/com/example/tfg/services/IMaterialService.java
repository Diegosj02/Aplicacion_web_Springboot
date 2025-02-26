package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Material;

import java.util.List;

public interface IMaterialService {

    Material buscarMaterialPorId(int id);

    List<Material> buscarMaterialPorNombre(String nombre);

    boolean añadirMaterial(Material material);

    List<Material> buscarMateriales();

    List<Material> buscarPorDeporte(String deporte);

    List<Material> buscarPorPrecioMin(double precio);

    List<Material> buscarPorPrecioMax(double precio);

    List<Material> buscarPorPrecioRango(double min,double max);

    List<Material> ordenarPorCriterio(String criterio);

    void actualizarMaterial(Material material);

    boolean eliminarMaterial(int id);
}
