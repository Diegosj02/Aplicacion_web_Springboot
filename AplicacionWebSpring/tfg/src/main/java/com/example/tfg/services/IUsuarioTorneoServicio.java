package com.example.tfg.services;

import com.example.tfg.model.Alquiler;
import com.example.tfg.model.Torneo;
import com.example.tfg.model.Usuario;
import com.example.tfg.model.UsuarioParticipaTorneo;

import java.util.List;

public interface IUsuarioTorneoServicio {

    List<UsuarioParticipaTorneo> buscarPorUsuario(Usuario usuario);

    List<UsuarioParticipaTorneo> buscarPorTorneo(Torneo torneo);

    UsuarioParticipaTorneo buscarPorUsuarioYTorneo(Usuario usuario,Torneo torneo);

    boolean registrarUsuarioEnTorneo(UsuarioParticipaTorneo usuarioT);



}
