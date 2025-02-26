package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Articulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IArticuloService {

    //Busca todos los articulos
    Page<Articulo> buscarTodos(Pageable pageable);

    //Busca un articulo concreto por su ID
    Articulo buscarArticuloPorId(Integer idArticulo);

    //Busca los articulos que contengan en su nombre lo especificado
    Page<Articulo> buscarPorNombre(String nombre,Pageable pageable);

    //Busca los articulos según el tipo especificado
    Page<Articulo> buscarPorTipo(String tipo,Pageable pageable);

    //Busca los articulo según un precio minimo
    Page<Articulo> buscarPorPrecioMin(double precio,Pageable pageable);

    //Busca los articulo según un precio maximo
    Page<Articulo> buscarPorPrecioMax(double precio,Pageable pageable);

    //Busca los articulo según un rango de precio
    Page<Articulo> buscarPorPrecioRango(double min, double max,Pageable pageable);

    //Busca los articulo según un precio minimo y su tipo
    Page<Articulo> buscarPorTipoYPrecioMinimo(String tipo, double precio,Pageable pageable);

    //Busca los articulo según un precio máximo y su tipo
    Page<Articulo> buscarPorTipoYPrecioMaximo(String tipo,double precio,Pageable pageable);

    //Busca los articulo según un rango de precio y su tipo
    Page<Articulo> buscarPorTipoYPrecioRango(String tipo,double min,double max,Pageable pageable);

    //Ordena según el criterio especificado
    Page<Articulo> ordenarPorCriterio(String criterio, Pageable pageable, List<Articulo> articulos);

    //Permite guardar nuevos articulos y actualizar los ya existentes
    void guardarArticulo(Articulo articulo);

    //Permite eliminar un articulo
    void eliminarArticulo(Integer idArticulo);
    
}
