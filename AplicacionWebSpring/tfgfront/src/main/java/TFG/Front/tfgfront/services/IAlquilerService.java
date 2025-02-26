package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Alquiler;
import TFG.Front.tfgfront.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalTime;

public interface IAlquilerService {

    //Devuelve todos los alquileres
    Page<Alquiler> buscarTodos(Pageable pageable);

    //Busca todos los alquileres que tenga al responsable específicado
    Page<Alquiler> buscarPorResponsable(Usuario usuario, Pageable pageable);

    //Busca todos los alquileres que tengan en común hora, fecha y material
    Page<Alquiler> buscarPorFechaHoraMaterial(LocalDate fecha, LocalTime hora, int idMaterial, Pageable pageable);

    //Busca todos los alquileres que tengan la fecha especificada
    Page<Alquiler> buscarPorFecha(LocalDate fecha,Pageable pageable);

    //Busca todos los alquileres que tengan el material especificado
    Page<Alquiler> buscarPorMaterial(int idMaterial,Pageable pageable);

    //Guarda el nuevo alquiler en la base de datos
    void guardarAlquiler(Alquiler alquiler);
}
