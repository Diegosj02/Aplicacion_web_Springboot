package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Articulo;
import TFG.Front.tfgfront.model.Ropa;
import TFG.Front.tfgfront.model.RopaId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRopaService {

    //Permite buscar toda los articulo con tipo ropa
    Page<Ropa> buscarTodos(Pageable pageable);

    //Permite buscar todas las prendas con un ID
    Page<Ropa> buscarPorId(Pageable pageable, Articulo articulo);

    //Permite guardar y actualizar una talla de ropa
    void guardarRopa(Ropa ropa);

    //Permite eliminar una talla
    void eliminarRopa(RopaId ropaId);

}
