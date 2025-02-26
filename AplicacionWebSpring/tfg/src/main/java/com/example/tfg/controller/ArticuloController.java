package com.example.tfg.controller;

import com.example.tfg.model.*;
import com.example.tfg.services.IArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticuloController {

    @Autowired
    IArticuloService articuloService;

    @GetMapping("/articulo/id/{id}")
    public Articulo obtenerArticuloPorId(@PathVariable("id") int id) {
        return articuloService.buscarArticuloPorId(id);
    }

    @GetMapping("/articulo/nombre/{nombre}")
    public List<Articulo> obtenerArticuloPorNombre(@PathVariable("nombre") String nombre) {
        return articuloService.buscarArticuloPorNombre(nombre);
    }

    @GetMapping("/articulo/tipo/{tipo}")
    public List<Articulo> obtenerArticuloPorId(@PathVariable("tipo") String tipo) {
        return articuloService.buscarArticuloPorTipo(tipo);
    }

    @GetMapping("/articulo/TipoMin/{tipo}/{min}")
    public List<Articulo> obtenerArticulosTipoYPrecioMin(@PathVariable("tipo") String tipo, @PathVariable("min") double min) {
        return articuloService.buscarPorTipoYPrecioMinimo(tipo, min);
    }

    @GetMapping("/articulo/TipoMax/{tipo}/{max}")
    public List<Articulo> obtenerArticulosTipoYPrecioMax(@PathVariable("tipo") String tipo, @PathVariable("max") double max) {
        return articuloService.buscarPorTipoYPrecioMaximo(tipo, max);
    }

    @GetMapping("/articulo/TipoRan/{tipo}/{min}/{max}")
    public List<Articulo> obtenerArticulosTipoYPrecio(@PathVariable("tipo") String tipo, @PathVariable("min") double min, @PathVariable("max") double max) {
        return articuloService.buscarPorTipoYPrecioRango(tipo, min, max);
    }

    @GetMapping("/articulo/orden/{criterio}")
    public List<Articulo> ordenarArticulosPorCriterio(@PathVariable("criterio") String criterio) {
        return articuloService.ordenarPorCriterio(criterio);
    }

    @GetMapping("/articulo")
    public List<Articulo> obtenerArticulos() {
        return articuloService.buscarArticulos();
    }

    @GetMapping("/articulo/precioMax/{precio}")
    public List<Articulo> obtenerArticuloPrecioMax(@PathVariable("precio") double precio) {
        return articuloService.buscarPorPrecioMaximo(precio);
    }

    @GetMapping("/articulo/precioMin/{precio}")
    public List<Articulo> obtenerArticuloPrecioMin(@PathVariable("precio") double precio) {
        return articuloService.buscarPorPrecioMinimo(precio);
    }

    @GetMapping("/articulo/precioRango/{precio_min}/{precio_max}")
    public List<Articulo> obtenerArticuloPrecioMinMax(@PathVariable("precio_min") double min, @PathVariable("precio_max") double max) {
        return articuloService.buscarPorPrecioRango(min, max);
    }

    @PostMapping("/articulo")
    public String annadirArticulo(@RequestBody Articulo articulo) {
        return String.valueOf(articuloService.annadirArticulo(articulo));

    }

    @PutMapping("/articulo")
    public void actualizarArticulo(@RequestBody Articulo articulo) {
        articuloService.actualizarArticulo(articulo);
    }

    @DeleteMapping(value = "/articulo/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminararticulo(@PathVariable("id") int id) {
        return String.valueOf(articuloService.eliminarArticulo(id));
    }

}
