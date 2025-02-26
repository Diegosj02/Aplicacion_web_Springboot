package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Cancha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICanchaService {

    //Busca todas las canchas
    Page<Cancha> buscarTodos(Pageable pageable);

    //Busca una cancha por ID
    Cancha buscarCanchaPorId(Integer idCancha);

    //Permite guardar y actualizar canchas
    void guardarCancha(Cancha cancha);

    //Elimina la cancha específicada
    void eliminarCancha(Integer idCancha);

}
