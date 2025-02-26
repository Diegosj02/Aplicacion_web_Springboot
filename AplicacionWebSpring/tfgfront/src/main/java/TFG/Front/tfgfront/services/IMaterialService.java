package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMaterialService {

    //Busca todos los materiales
    Page<Material> buscarTodos(Pageable pageable);

    //Busca un material por ID
    Material buscarMaterialPorId(Integer idMaterial);

    //Permite añadir y actualizar materiales
    void guardarMaterial(Material material);

    //Permite eliminar un material
    void eliminarMaterial(Integer idMaterial);

    //Busca un material por nombre
    Page<Material> buscarPorNombre(String nombre, Pageable pageable);

    //Busca un material por un precio minimo
    Page<Material> buscarPorPrecioMin(double precio,Pageable pageable);

    //Busca un material por un precio maximo
    Page<Material> buscarPorPrecioMax(double precio,Pageable pageable);

    //Busca un material por un rango de precio
    Page<Material> buscarPorPrecioRango(double min, double max,Pageable pageable);

    //Ordena los materiales segun un criterio
    Page<Material> ordenarPorCriterio(String criterio, Pageable pageable, List<Material> materiales);


}
