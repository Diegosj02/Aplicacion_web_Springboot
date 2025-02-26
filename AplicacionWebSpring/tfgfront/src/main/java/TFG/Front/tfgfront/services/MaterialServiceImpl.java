package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MaterialServiceImpl implements IMaterialService{

    @Autowired
    RestTemplate template;

    //URL para conectarse al back-end y a la DDBB
    String url = "http://localhost:8080/material";

    //Devuelve todos los materiales organizados en paginas de tamaño especifico
    @Override
    public Page<Material> buscarTodos(Pageable pageable) {
        Material[] materiales = template.getForObject(url,Material[].class);
        List<Material> materialesList = Arrays.asList(materiales);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Material> list;

        if (materialesList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,materialesList.size());
            list = materialesList.subList(startItem,toIndex);
        }
        Page<Material> page = new PageImpl<>(list,PageRequest.of(currentPage,pageSize),materialesList.size());
        return page;
    }

    //Si encuentra el material, lo devuelve, y si no, devuelve null
    @Override
    public Material buscarMaterialPorId(Integer idMaterial) {
        return template.getForObject(url+"/id/"+idMaterial,Material.class);
    }

    //Permite guardar y actualizar materiales
    @Override
    public void guardarMaterial(Material material) {
        if (material.getId()!=null && material.getId() > 0){
            template.put(url,material);
        }else{
            material.setId(0);
            template.postForObject(url,material,String.class);
        }
    }

    //Elimina el material especificado
    @Override
    public void eliminarMaterial(Integer idMaterial) {
        template.delete(url+"/"+idMaterial);
    }

    //Devuelve todos los materiales por nombre organizados en paginas de tamaño especifico
    @Override
    public Page<Material> buscarPorNombre(String nombre, Pageable pageable) {

        Material[] materiales = template.getForObject(url+"/nombre/"+nombre,Material[].class);
        List<Material> materialesList = Arrays.asList(materiales);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Material> list;

        if (materialesList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,materialesList.size());
            list = materialesList.subList(startItem,toIndex);
        }
        Page<Material> page = new PageImpl<>(list,PageRequest.of(currentPage,pageSize),materialesList.size());
        return page;
    }

    //Devuelve todos los materiales por un precio minimo organizados en paginas de tamaño especifico
    @Override
    public Page<Material> buscarPorPrecioMin(double precio, Pageable pageable) {

        Material[] materiales = template.getForObject(url+"/precioMin/"+precio,Material[].class);
        List<Material> materialesList = Arrays.asList(materiales);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Material> list;

        if (materialesList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,materialesList.size());
            list = materialesList.subList(startItem,toIndex);
        }
        Page<Material> page = new PageImpl<>(list,PageRequest.of(currentPage,pageSize),materialesList.size());
        return page;
    }

    //Devuelve todos los materiales por un precio maximo organizados en paginas de tamaño especifico
    @Override
    public Page<Material> buscarPorPrecioMax(double precio, Pageable pageable) {
        Material[] materiales = template.getForObject(url+"/precioMax/"+precio,Material[].class);
        List<Material> materialesList = Arrays.asList(materiales);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Material> list;

        if (materialesList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,materialesList.size());
            list = materialesList.subList(startItem,toIndex);
        }
        Page<Material> page = new PageImpl<>(list,PageRequest.of(currentPage,pageSize),materialesList.size());
        return page;
    }

    //Devuelve todos los materiales por un precio en un rango determinado organizados en paginas de tamaño especifico
    @Override
    public Page<Material> buscarPorPrecioRango(double min, double max, Pageable pageable) {
        Material[] materiales = template.getForObject(url+"/precioRang/"+min+"/"+max,Material[].class);
        List<Material> materialesList = Arrays.asList(materiales);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Material> list;

        if (materialesList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,materialesList.size());
            list = materialesList.subList(startItem,toIndex);
        }
        Page<Material> page = new PageImpl<>(list,PageRequest.of(currentPage,pageSize),materialesList.size());
        return page;
    }

    //Permite ordenar una lista de materiales siguiendo un criterio
    @Override
    public Page<Material> ordenarPorCriterio(String criterio, Pageable pageable, List<Material> materialesLista) {
        Material[] materiales = template.getForObject(url+"/orden/"+criterio,Material[].class);
        List<Material> ordenado = new ArrayList<>();
        for (int i=0;i<materiales.length;i++){
            for (int j=0;j<materialesLista.size();j++){
                if (materiales[i].getId() == materialesLista.get(j).getId()){
                    ordenado.add(materiales[i]);
                }
            }
        }
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Material> list;

        if (ordenado.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,ordenado.size());
            list = ordenado.subList(startItem,toIndex);
        }
        Page<Material> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),ordenado.size());
        return page;
    }
}
