package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AlquilerServiceImpl implements IAlquilerService{

    @Autowired
    RestTemplate template;

    //URL para conectarse con el Back-End y la DDBB
    String url="http://localhost:8080/alquiler";

    //Devuelve todos los alquileres divididos en paginas de tamaño especificado
    @Override
    public Page<Alquiler> buscarTodos(Pageable pageable) {
        Alquiler[] alquileres = template.getForObject(url,Alquiler[].class);
        List<Alquiler> alquileresList = Arrays.asList(alquileres);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Alquiler> list;

        if (alquileresList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,alquileresList.size());
            list = alquileresList.subList(startItem,toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage,pageSize),alquileresList.size());
    }

    //Devuelve todos los alquileres con un usuario concreto divididos en paginas de tamaño especificado
    @Override
    public Page<Alquiler> buscarPorResponsable(Usuario usuario, Pageable pageable) {
        Alquiler[] alquileres = template.getForObject(url+"/usuario/"+usuario.getId(),Alquiler[].class);
        List<Alquiler> alquileresList = Arrays.asList(alquileres);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Alquiler> list;

        if (alquileresList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,alquileresList.size());
            list = alquileresList.subList(startItem,toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage,pageSize),alquileresList.size());
    }

    //Devuelve todos los alquileres con fecha, hora y material indicados, divididos en paginas de tamaño especificado
    @Override
    public Page<Alquiler> buscarPorFechaHoraMaterial(LocalDate fecha, LocalTime hora, int  idMaterial, Pageable pageable) {
        Alquiler[] alquileres = template.getForObject(url+"/fecha/"+fecha+"/hora/"+hora+"/material/"+idMaterial,Alquiler[].class);
        List<Alquiler> alquileresList = Arrays.asList(alquileres);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Alquiler> list;

        if (alquileresList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,alquileresList.size());
            list = alquileresList.subList(startItem,toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage,pageSize),alquileresList.size());
    }

    //Devuelve todos los alquileres por fecha especifica divididos en paginas de tamaño especificado
    @Override
    public Page<Alquiler> buscarPorFecha(LocalDate fecha, Pageable pageable) {
        Alquiler[] alquileres = template.getForObject(url+"/fecha/"+fecha,Alquiler[].class);
        List<Alquiler> alquileresList = Arrays.asList(alquileres);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Alquiler> list;

        if (alquileresList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,alquileresList.size());
            list = alquileresList.subList(startItem,toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage,pageSize),alquileresList.size());
    }

    //Devuelve todos los alquileres por material concreto divididos en paginas de tamaño especificado
    @Override
    public Page<Alquiler> buscarPorMaterial(int idMaterial, Pageable pageable) {
        Alquiler[] alquileres = template.getForObject(url+"/material/"+idMaterial,Alquiler[].class);
        List<Alquiler> alquileresList = Arrays.asList(alquileres);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Alquiler> list;

        if (alquileresList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,alquileresList.size());
            list = alquileresList.subList(startItem,toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage,pageSize),alquileresList.size());
    }

    //Permite guardar los cambios de un alquiler ya existente o añadir uno nuevo
    @Override
    public void guardarAlquiler(Alquiler alquiler) {
        if (alquiler.getId() != null && alquiler.getId() > 0){
            template.put(url,alquiler);
        }else{
            alquiler.setId(0);
            template.postForObject(url,alquiler,String.class);
        }
    }
}
