package com.example.tfg.controller;

import com.example.tfg.model.Descuento;
import com.example.tfg.model.Articulo;
import com.example.tfg.model.Descuento;
import com.example.tfg.model.Usuario;
import com.example.tfg.services.IArticuloService;
import com.example.tfg.services.IDescuentoServicio;
import com.example.tfg.services.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DescuentoController {

    @Autowired
    IDescuentoServicio descuentoServicio;
    @Autowired
    IArticuloService articuloService;
    @Autowired
    IUsuariosService usuariosService;

    @GetMapping("/descuento/id/{id}")
    public Descuento buscarPorId(@PathVariable("id")int id){
        return descuentoServicio.buscarPorId(id);
    }

    @GetMapping("/descuento/tipo/{tipo}")
    public List<Descuento> buscarPorTipo(@PathVariable("tipo")String tipo){
        return descuentoServicio.buscarPorTipo(tipo);
    }

    @GetMapping("/descuento/Articulo/{articulo}")
    public List<Descuento> buscarPorArticulo(@PathVariable("articulo")int id){
        Articulo articulo = articuloService.buscarArticuloPorId(id);
        return descuentoServicio.buscarPorArticulo(articulo);
    }

    @GetMapping("/descuento/Usuario/{usuario}")
    public List<Descuento> buscarPorUsuario(@PathVariable("usuario")int id){
        Usuario usuario = usuariosService.buscarUsuarioPorId(id);
        return descuentoServicio.buscarPorUsuario(usuario);
    }

    @GetMapping("/descuento/precioMin/{precio}")
    public List<Descuento> buscarPorPrecioMin(@PathVariable("precio")double precio){
        return descuentoServicio.buscarPorPrecioMin(precio);
    }

    @GetMapping("/descuento/precioMax/{precio}")
    public List<Descuento> buscarPorPrecioMax(@PathVariable("precio")double precio){
        return descuentoServicio.buscarPorPrecioMax(precio);
    }
    @GetMapping("/descuento/precioRang/{min}/{max}")
    public List<Descuento> buscarPorPrecioRang(@PathVariable("min")double min,@PathVariable("max")double max){
        return descuentoServicio.buscarPorPrecioRango(min,max);
    }

    @GetMapping("/descuento/TipoPrecioMin/{tipo}/{min}")
    public List<Descuento> buscarPorTipoYPrecioMin(@PathVariable("min")double min,@PathVariable("tipo")String tipo){
        return descuentoServicio.buscarPorTipoYPrecioMin(tipo,min);
    }

    @GetMapping("/descuento/TipoprecioMax/{tipo}/{max}")
    public List<Descuento> buscarPorTipoYPrecioMax(@PathVariable("max")double max,@PathVariable("tipo")String tipo){
        return descuentoServicio.buscarPorTipoYPrecioMax(tipo,max);
    }

    @GetMapping("/descuento/TipoPrecioRang/{tipo}/{min}/{max}")
    public List<Descuento> buscarPorTipoYPrecioRang(@PathVariable("max")double max,@PathVariable("min")double min,@PathVariable("tipo")String tipo){
        return descuentoServicio.buscarPorTipoYPrecioRang(tipo,min,max);
    }

    @PostMapping(value = "/descuento", produces = MediaType.TEXT_PLAIN_VALUE)
    public String registrarDescuento(@RequestBody Descuento descuento){
        return String.valueOf(descuentoServicio.anadirDescuento(descuento));
    }

    @GetMapping("/descuento")
    public List<Descuento> buscarTodos(){
        return descuentoServicio.buscarDescuentos();
    }

    @DeleteMapping(value = "/descuento/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarDescuento(@PathVariable("id") int id) {
        return String.valueOf(descuentoServicio.eliminarDescuento(id));
    }

    @PutMapping("/descuento")
    public void actualizarDescuento(@RequestBody Descuento descuento){descuentoServicio.actualizarDescuento(descuento);}
}
