package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService{

    @Autowired
    RestTemplate template;

    //URL para conectarse al back-end y a la DDBB
    String url = "http://localhost:8080/reserva";

    //Busca todas las reservas existentes y las muestra de tamaño especifico
    @Override
    public Page<Reserva> buscarTodas(Pageable pageable) {
        Reserva[] reservas = template.getForObject(url,Reserva[].class);
        List<Reserva> reservasList = Arrays.asList(reservas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reserva> list;

        if (reservasList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,reservasList.size());
            list = reservasList.subList(startItem,toIndex);
        }
        Page<Reserva> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),reservasList.size());
        return page;
    }

    //Busca reservas por cancha especificada y las devuelve en paginas del tamaño solicitado
    @Override
    public Page<Reserva> buscarReservaPorCancha(int idCancha, Pageable pageable) {
        Reserva[] reservas = template.getForObject(url+"/cancha/"+idCancha,Reserva[].class);
        List<Reserva> reservasList = Arrays.asList(reservas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reserva> list;

        if (reservasList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,reservasList.size());
            list = reservasList.subList(startItem,toIndex);
        }
        Page<Reserva> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),reservasList.size());
        return page;
    }

    //Busca reservas por usuario especificado y las devuelve en paginas del tamaño solicitado
    @Override
    public Page<Reserva> buscarReservaPorUsuario(int idUsuario, Pageable pageable) {
        Reserva[] reservas = template.getForObject(url+"/usuario/"+idUsuario,Reserva[].class);
        List<Reserva> reservasList = Arrays.asList(reservas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reserva> list;

        if (reservasList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,reservasList.size());
            list = reservasList.subList(startItem,toIndex);
        }
        Page<Reserva> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),reservasList.size());
        return page;
    }

    //Busca reservas por cancha y fecha especifica y las devuelve en paginas del tamaño solicitado
    @Override
    public Page<Reserva> buscarReservaPorCanchaYFecha(int idCancha, LocalDate fecha, Pageable pageable) {
        Reserva[] reservas = template.getForObject(url+"/canchaFecha/"+idCancha+"/"+fecha,Reserva[].class);
        List<Reserva> reservasList = Arrays.asList(reservas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reserva> list;

        if (reservasList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,reservasList.size());
            list = reservasList.subList(startItem,toIndex);
        }
        Page<Reserva> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),reservasList.size());
        return page;
    }

    //Busca reservas por fecha especificada y las devuelve en paginas del tamaño solicitado
    @Override
    public Page<Reserva> buscarPorFecha(LocalDate fecha, Pageable pageable) {
        Reserva[] reservas = template.getForObject(url+"/fecha/"+fecha,Reserva[].class);
        List<Reserva> reservasList = Arrays.asList(reservas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reserva> list;

        if (reservasList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,reservasList.size());
            list = reservasList.subList(startItem,toIndex);
        }
        Page<Reserva> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),reservasList.size());
        return page;
    }

    //Permite actualizar y guardar nuevas reservas
    @Override
    public void guardarReserva(Reserva reserva) {
        if (reserva.getId() != null && reserva.getId() > 0){
            template.put(url,reserva);
        }else{
            reserva.setId(0);
            template.postForObject(url,reserva,String.class);
        }
    }

}
