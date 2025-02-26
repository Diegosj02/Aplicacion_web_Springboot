package com.example.tfg.dao;

import com.example.tfg.model.Torneo;
import com.example.tfg.model.Usuario;
import com.example.tfg.model.UsuarioParticipaTorneo;

import java.util.List;

public interface IUsuarioTorneoDAO {

    List<UsuarioParticipaTorneo> buscarPorUsuario(Usuario usuario);

    List<UsuarioParticipaTorneo> buscarPorTorneo(Torneo torneo);

    UsuarioParticipaTorneo buscarPorUsuarioYTorneo(Usuario Usuario,Torneo Torneo);

    void registrarUsuarioEnTorneo(UsuarioParticipaTorneo usuarioT);

}

