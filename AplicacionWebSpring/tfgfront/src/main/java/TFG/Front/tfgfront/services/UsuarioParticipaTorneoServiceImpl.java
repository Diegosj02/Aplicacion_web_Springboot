package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Torneo;
import TFG.Front.tfgfront.model.UsuarioParticipaTorneo;
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
public class UsuarioParticipaTorneoServiceImpl implements IUsuarioParticipaTorneo{
    @Autowired
    RestTemplate template;

    //URL para conectarse al back-end y a la DDBB
    String url="http://localhost:8080/usuarioTorneo";

    //Busca todos los usuarios que participan en el torneo especificado
    @Override
    public Page<UsuarioParticipaTorneo> buscarPorTorneo(Torneo torneo, Pageable pageable) {
        UsuarioParticipaTorneo[] torneos = template.getForObject(url+"/Torneo/"+torneo.getId(),UsuarioParticipaTorneo[].class);
        List<UsuarioParticipaTorneo> torneosList = Arrays.asList(torneos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<UsuarioParticipaTorneo> list;

        if (torneosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,torneosList.size());
            list = torneosList.subList(startItem,toIndex);
        }
        Page<UsuarioParticipaTorneo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),torneosList.size());
        return page;
    }

    //Permite registrar a un usuario en un torneo
    @Override
    public String registrarUsuarioEnTorneo(UsuarioParticipaTorneo usuarioParticipaTorneo) {
        return template.postForObject(url,usuarioParticipaTorneo,String.class);
    }
}
