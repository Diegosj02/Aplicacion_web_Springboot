package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Descuento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDescuentoService {

    //Busca todos los descuentos
    Page<Descuento> buscarTodos(Pageable pageable);

    //Busca un descuento especifico por su ID
    Descuento buscarPorId(int IdDescuento);

    //Busca todos los descuentos por usuario especificado
    Page<Descuento> buscarPorUsuario(int idUsuario,Pageable pageable);

    //Busca todos los descuentos por articulo especificado
    Page<Descuento> buscarPorArticulo(int idArticulo,Pageable pageable);

    //Busca todos los descuentos del tipo especificado
    Page<Descuento> buscarPorTipo(String tipo, Pageable pageable);

    //Busca todos los descuentos que tenga un precio superior o igual al especificado
    Page<Descuento> buscarPorPrecioMin(double min, Pageable pageable);

    //Busca todos los descuentos que tenga un precio inferior o igual al especificado
    Page<Descuento> buscarPorPrecioMax(double max, Pageable pageable);

    //Busca todos los descuentos que tenga un precio en un rango especificado
    Page<Descuento> buscarPorPrecioRango(double min, double max, Pageable pageable);

    //Busca todos los descuentos que tenga un precio superior o igual y un tipo especificado
    Page<Descuento> buscarPorTipoYPrecioMin(String tipo,double min,Pageable pageable);

    //Busca todos los descuentos que tenga un precio inferior o igual y un tipo especificado
    Page<Descuento> buscarPorTipoYPrecioMax(String tipo,double max,Pageable pageable);

    //Busca todos los descuentos que tenga un precio dentro de un rango y un tipo especificado
    Page<Descuento> buscarPorTipoYPrecioRang(String tipo,double min,double max,Pageable pageable);

    //Permite eliminar un descuento
    void eliminarDescuento(Integer idDescuento);

    //Permite crear descuentos
    String registrarDescuento(Descuento descuento);

    //Permite actualizar descuentos
    void actualizarDescuento(Descuento descuento);
    
}
