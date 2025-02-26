package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Reserva;
import TFG.Front.tfgfront.model.Torneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TorneoServiceImpl implements ITorneoService{

    @Autowired
    RestTemplate template;

    //URL para conectarse al back-end y a la DDBB
    String url = "http://localhost:8080/torneo";

    //Busca un torneo por su ID. Si no lo encuentra, devuelve null
    @Override
    public Torneo buscarPorId(Integer id) {
        return template.getForObject(url+"/id/"+id,Torneo.class);
    }

    //Permite añadir torneos y modificar los ya existentes
    @Override
    public void guardarTorneo(Torneo torneo) {
        if (torneo.getId() != null && torneo.getId() > 0){
            template.put(url,torneo);
        }else{
            torneo.setId(torneo.getNoticia().getId());
            template.postForObject(url,torneo,String.class);
        }
    }
}
