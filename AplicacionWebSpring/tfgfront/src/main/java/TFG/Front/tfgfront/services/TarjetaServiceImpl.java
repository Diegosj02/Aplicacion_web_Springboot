package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TarjetaServiceImpl implements ITarjetaSservice{
    @Autowired
    RestTemplate template;

    //URL para conectarse al back-end y a la DDBB
    String url="http://localhost:8080/tarjeta";

    //Permite buscar una tarjeta según su ID. Si no encuentra ninguna, devuelve null
    @Override
    public Tarjeta buscarPorId(int id) {
        return template.getForObject(url+"/id/"+id,Tarjeta.class);
    }

    //Permite guardar cambios en la tarjeta
    @Override
    public void guardarTarjeta(Tarjeta tarjeta) {
        if(tarjeta.getId() != null && tarjeta.getId() > 0){
            template.put(url,tarjeta);
        }else{
            tarjeta.setId(0);
            template.postForObject(url,tarjeta, String.class);
        }
    }
}
