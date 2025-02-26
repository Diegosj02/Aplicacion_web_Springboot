package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Articulo;
import TFG.Front.tfgfront.model.Deportivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeportivoServiceImpl implements IDeportivoService{

    @Autowired
    RestTemplate template;
    @Autowired
    IArticuloService articuloService;

    //URL para conectar al back-end y la DDBB
    String url="http://localhost:8080/deportivo";

    //Busca un articulo deportivo por ID y lo devuelve. Si no lo encuentra, devuelve null
    @Override
    public Deportivo buscarDeportivoPorId(Integer idDeportivo) {
        return template.getForObject(url+"/id/"+idDeportivo,Deportivo.class);
    }

    //Permite actualizar los datos de un articulo deportivo o guardar uno nuevo
    @Override
    public void guardarDeportivo(Deportivo deportivo) {
        //Actualizar
        if(deportivo.getId() != null && deportivo.getId() > 0){
            template.put(url,deportivo);
        }else{ //Annadir
            //Recogemos el ultimo articulo que se haya añadido y lo asociamos al nuevo deportivo
            Pageable pageable = PageRequest.of(0,1000000000);
            Articulo articuloLast = articuloService.buscarTodos(pageable).getContent().get(articuloService.buscarTodos(pageable).getContent().size()-1);
            deportivo.setArticulo(articuloLast);

            deportivo.setId(articuloLast.getId());
            template.postForObject(url,deportivo, String.class);
        }
    }

}
