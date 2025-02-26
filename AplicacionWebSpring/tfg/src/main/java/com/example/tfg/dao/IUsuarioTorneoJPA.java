package com.example.tfg.dao;

import com.example.tfg.model.Torneo;
import com.example.tfg.model.Usuario;
import com.example.tfg.model.UsuarioParticipaTorneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioTorneoJPA extends JpaRepository<UsuarioParticipaTorneo,Integer> {

    //SELECT * FROM USUARIOPARTICIPATORNEO WHERE IDUSUARIO = ?
    List<UsuarioParticipaTorneo> findByIdusuario(Usuario usuario);

    //SELECT * FROM USUARIOPARTICIPATORNEO WHERE IDTORNEO = ?
    List<UsuarioParticipaTorneo> findByIdtorneo(Torneo torneo);

    //SELECT * FROM USUARIOPARTICIPATORNEO WHERE IDUSUARIO = ? AND IDTORNEO = ?
    Optional<UsuarioParticipaTorneo> findByIdusuarioAndIdtorneo(Usuario usuario,Torneo torneo);


}
