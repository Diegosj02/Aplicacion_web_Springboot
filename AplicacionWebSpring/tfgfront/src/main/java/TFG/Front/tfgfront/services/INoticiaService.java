package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Noticia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INoticiaService {

    //Busca todas las noticias
    Page<Noticia> buscarTodos(Pageable pageable);

    //Busca una noticia por ID
    Noticia buscarNoticiaPorId(Integer idNoticia);

    //Guarda y actualiza noticias
    void guardarNoticia(Noticia noticia);

    //Elimina noticias
    void eliminarNoticia(Integer idNoticia);

}
