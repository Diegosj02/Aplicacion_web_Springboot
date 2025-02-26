package com.example.tfg.services;

import com.example.tfg.dao.IMaterialDAO;
import com.example.tfg.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements IMaterialService{

    @Autowired
    IMaterialDAO materialDAO;

    @Override
    public List<Material> buscarMateriales(){
        return materialDAO.buscarMateriales();
    }

    @Override
    public Material buscarMaterialPorId(int id) {
        return materialDAO.buscarMaterialPorId(id);
    }

    @Override
    public List<Material> buscarMaterialPorNombre(String nombre) {
        return materialDAO.buscarMaterialPorNombre(nombre);
    }

    @Override
    public boolean añadirMaterial(Material material) {
        if (materialDAO.buscarMaterialPorId(material.getId())==null){
            materialDAO.añadirMaterial(material);
            return true;
        }
        return false;
    }

    @Override
    public List<Material> buscarPorDeporte(String deporte){
        return materialDAO.buscarPorDeporte(deporte);
    }

    @Override
    public List<Material> buscarPorPrecioMin(double precio) {
        return materialDAO.buscarPorPrecioMin(precio);
    }

    @Override
    public List<Material> buscarPorPrecioMax(double precio) {
        return materialDAO.buscarPorPrecioMax(precio);
    }

    @Override
    public List<Material> buscarPorPrecioRango(double min, double max) {
        return materialDAO.buscarPorPrecioRango(min,max);
    }

    @Override
    public List<Material> ordenarPorCriterio(String criterio) {
        return materialDAO.ordenarPorCriterio(criterio);
    }

    @Override
    public void actualizarMaterial(Material material) {
        if (materialDAO.buscarMaterialPorId(material.getId()) != null){
            materialDAO.actualizarMaterial(material);
        }
    }

    @Override
    public boolean eliminarMaterial(int id) {
        if (materialDAO.buscarMaterialPorId(id) != null){
            materialDAO.eliminarMaterial(id);
            return true;
        }
        return false;
    }
}
