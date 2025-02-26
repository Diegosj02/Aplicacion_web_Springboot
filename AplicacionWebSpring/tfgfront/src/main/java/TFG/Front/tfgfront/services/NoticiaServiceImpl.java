package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class NoticiaServiceImpl implements INoticiaService{
    
    @Autowired
    RestTemplate template;

    //URL para conectarse al back-end y a la DDBB
    String url = "http://localhost:8080/noticia";

    //Busca todas las noticias y las muestra en paginas de tamaño establecido
    @Override
    public Page<Noticia> buscarTodos(Pageable pageable) {
        Noticia[] noticias = template.getForObject(url,Noticia[].class);
        List<Noticia> noticiasList = Arrays.asList(noticias);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Noticia> list;

        if (noticiasList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,noticiasList.size());
            list = noticiasList.subList(startItem,toIndex);
        }
        Page<Noticia> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),noticiasList.size());
        return page;
    }

    //Busca una noticia por su id. Si la encuentra la devuelve y si no, devuelve null
    @Override
    public Noticia buscarNoticiaPorId(Integer idNoticia) {
        return template.getForObject(url+"/id/"+idNoticia,Noticia.class);
    }

    //Permite guardar y actualizar noticias
    @Override
    public void guardarNoticia(Noticia noticia) {
        if (noticia.getId()!=null && noticia.getId() > 0){
            template.put(url,noticia);
        }else{
            noticia.setId(0);
            System.out.println(noticia);
            template.postForObject(url,noticia,String.class);
        }
    }

    //Elimina la noticia solicitada
    @Override
    public void eliminarNoticia(Integer idNoticia) {
        template.delete(url+"/"+idNoticia);
    }

}
