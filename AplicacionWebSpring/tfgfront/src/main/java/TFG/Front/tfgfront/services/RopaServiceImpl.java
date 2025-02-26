package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.*;
import TFG.Front.tfgfront.model.Ropa;
import TFG.Front.tfgfront.model.Ropa;
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
public class RopaServiceImpl implements IRopaService{

    @Autowired
    RestTemplate template;

    //URL para conectarse al back-end y a la DDBB
    String url="http://localhost:8080/ropa";

    //Busca todos los articulos de tipo ropa y los devuelve en paginas del tamaño solicitado
    @Override
    public Page<Ropa> buscarTodos(Pageable pageable) {
        Ropa[] ropas = template.getForObject(url,Ropa[].class);
        List<Ropa> ropasList = Arrays.asList(ropas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Ropa> list;

        if (ropasList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,ropasList.size());
            list = ropasList.subList(startItem,toIndex);
        }
        Page<Ropa> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),ropasList.size());
        return page;
    }

    //Permite buscar un articulo por ID y, si no lo encuentra, devuelve null
    @Override
    public Page<Ropa> buscarPorId(Pageable pageable, Articulo articulo) {
        Ropa[] torneos = template.getForObject(url+"/id/"+articulo.getId(),Ropa[].class);
        List<Ropa> torneosList = Arrays.asList(torneos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Ropa> list;

        if (torneosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,torneosList.size());
            list = torneosList.subList(startItem,toIndex);
        }
        Page<Ropa> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),torneosList.size());
        return page;
    }

    //Permite actualizar la ropa o añadir nuevas tallas
    @Override
    public void guardarRopa(Ropa ropa) {
        if(ropa.getIdPropio() != null && ropa.getIdPropio().getIdropa() != null){
            template.put(url,ropa);
        }else{
            ropa.getIdPropio().setIdropa(ropa.getIdRopa().getId());
            template.postForObject(url,ropa, String.class);
        }
    }

    //Permite eliminar tallas de Ropa
    @Override
    public void eliminarRopa(RopaId ropaId) {
        template.delete(url+"/"+ropaId.getIdropa()+"/"+ropaId.getTalla());
    }

}
