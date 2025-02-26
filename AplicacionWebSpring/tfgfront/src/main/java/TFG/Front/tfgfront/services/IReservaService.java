package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IReservaService {

    //Busca todas las reservas
    Page<Reserva> buscarTodas(Pageable pageable);

    //Busca reservas por cancha especificada
    Page<Reserva> buscarReservaPorCancha(int idCancha, Pageable pageable);

    ////Busca reservas por usuario especificado
    Page<Reserva> buscarReservaPorUsuario(int idUsuario, Pageable pageable);

    //Busca reservas por cancha y fecha especificada
    Page<Reserva> buscarReservaPorCanchaYFecha(int idCancha, LocalDate fecha, Pageable pageable);

    //Busca reservas por fecha especificada
    Page<Reserva> buscarPorFecha(LocalDate fecha,Pageable pageable);

    //Permite añadir y actualizar reservas
    void guardarReserva(Reserva reserva);

}
