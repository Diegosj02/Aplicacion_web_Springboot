package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Cancha;
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
public class CanchaServiceImpl implements ICanchaService{

    @Autowired
    RestTemplate template;

    //URL para acceder al back-end y a la DDBB
    String url = "http://localhost:8080/cancha";

    //Devuelve todas las canchas divididos en paginas de tamaño especificado
    @Override
    public Page<Cancha> buscarTodos(Pageable pageable) {
        Cancha[] canchas = template.getForObject(url,Cancha[].class);
        List<Cancha> canchasList = Arrays.asList(canchas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Cancha> list;

        if (canchasList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,canchasList.size());
            list = canchasList.subList(startItem,toIndex);
        }
        Page<Cancha> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),canchasList.size());
        return page;
    }

    //Si existe, devuelve la cancha con el ID especificado. Si no, devuelve null.
    @Override
    public Cancha buscarCanchaPorId(Integer idCancha) {
        Cancha cancha = template.getForObject(url+"/"+idCancha,Cancha.class);
        return cancha;
    }

    //Si la cancha existia de antes, permite guardar modificaciones. Si no, la añade a la base de datos
    @Override
    public void guardarCancha(Cancha cancha) {
        if (cancha.getId()!=null && cancha.getId() > 0){
            template.put(url,cancha);
        }else{
            cancha.setId(0);
            template.postForObject(url,cancha,String.class);
        }
    }

    //Permite eliminar una cancha especifica
    @Override
    public void eliminarCancha(Integer idCancha) {
        template.delete(url+"/"+idCancha);
    }

}
