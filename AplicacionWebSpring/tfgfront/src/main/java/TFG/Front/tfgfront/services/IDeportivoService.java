package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Deportivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDeportivoService {

    //Busca un articulo deportivo por su ID
    Deportivo buscarDeportivoPorId(Integer idDeportivo);

    //Permite actualizar o registrar nuevos articulos deportivos
    void guardarDeportivo(Deportivo deportivo);


}
