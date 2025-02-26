package com.example.tfg.dao;

import com.example.tfg.model.Material;
import com.example.tfg.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class MaterialDAOImpl implements IMaterialDAO{

    @Autowired
    IMaterialJPA materialJPA;

    @Override
    public List<Material> buscarMateriales(){
        return materialJPA.findAll();
    }

    @Override
    public List<Material> buscarPorDeporte(String deporte){
        return materialJPA.findByDeporte(deporte);
    }

    @Override
    public List<Material> buscarPorPrecioMin(double precio) {
        return materialJPA.findByPrecioGreaterThanEqual(precio);
    }

    @Override
    public List<Material> buscarPorPrecioMax(double precio) {
        return materialJPA.findByPrecioLessThanEqual(precio);
    }

    @Override
    public List<Material> buscarPorPrecioRango(double min, double max) {
        return materialJPA.findByPrecioBetween(min,max);
    }

    @Override
    public List<Material> ordenarPorCriterio(String criterio) {
        List<Material> materiales = materialJPA.findAll();
        for (int i = 0; i < materiales.size(); i++) {
            if (criterio.equals("alfabetico")) {
                materiales.sort(Comparator.comparing(Material::getNombre));
            } else if (criterio.equals("precioAsc")) {
                materiales.sort(Comparator.comparing(Material::getPrecio));
            } else {
                materiales.sort(Comparator.comparing(Material::getPrecio));
                Collections.reverse(materiales);
            }
        }
        return materiales;
    }

    @Override
    public Material buscarMaterialPorId(int id) {
       Optional<Material> material = materialJPA.findById(id);
       if (material.isPresent()){
           return material.get();
       }
       return null;
    }

    @Override
    public List<Material> buscarMaterialPorNombre(String nombre) {
        return materialJPA.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public void añadirMaterial(Material material) {
        materialJPA.save(material);
    }

    @Override
    public void eliminarMaterial(int id){
        materialJPA.deleteAllById(Collections.singleton(id));
    }

    @Override
    public void actualizarMaterial(Material material){
        materialJPA.save(material);
    }
}
