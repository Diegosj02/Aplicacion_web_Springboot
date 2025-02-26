package com.example.tfg.controller;

import com.example.tfg.model.Material;
import com.example.tfg.model.Material;
import com.example.tfg.services.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MaterialController {

    @Autowired
    IMaterialService materialServicio;

    @GetMapping("/material/id/{id}")
    public Material buscarPorId(@PathVariable("id")int id){
        return materialServicio.buscarMaterialPorId(id);
    }

    @GetMapping("/material/nombre/{nombre}")
    public List<Material> buscarPorNombre(@PathVariable ("nombre")String nombre){
        return materialServicio.buscarMaterialPorNombre(nombre);
    }

    @PostMapping(value="/material",produces = MediaType.TEXT_PLAIN_VALUE)
    public String añadirMaterial(@RequestBody Material material){
        return String.valueOf(materialServicio.añadirMaterial(material));
    }

    @GetMapping("/material")
    public List<Material> buscarMateriales(){
        return materialServicio.buscarMateriales();
    }

    @GetMapping("/material/deporte/{deporte}")
    public List<Material> buscarMaterialPorDeporte(@PathVariable("deporte")String deporte){
        return materialServicio.buscarPorDeporte(deporte);
    }

    @GetMapping("/material/precioMin/{precio}")
    public List<Material> buscarPorPrecioMin(@PathVariable("precio")double precio){
        return materialServicio.buscarPorPrecioMin(precio);
    }

    @GetMapping("/material/precioMax/{precio}")
    public List<Material> buscarPorPrecioMax(@PathVariable("precio")double precio){
        return materialServicio.buscarPorPrecioMax(precio);
    }
    @GetMapping("/material/precioRang/{min}/{max}")
    public List<Material> buscarPorPrecioRang(@PathVariable("min")double min,@PathVariable("max")double max){
        return materialServicio.buscarPorPrecioRango(min,max);
    }
    @GetMapping("/material/orden/{criterio}")
    public List<Material> ordenarPorCriterio(@PathVariable("criterio")String criterio){
        return materialServicio.ordenarPorCriterio(criterio);
    }

    @PutMapping("/material")
    public void actualizarMaterial(@RequestBody Material Material) {
        materialServicio.actualizarMaterial(Material);
    }

    @DeleteMapping(value = "/material/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarMaterial(@PathVariable("id") int id) {
        return String.valueOf(materialServicio.eliminarMaterial(id));
    }
}
