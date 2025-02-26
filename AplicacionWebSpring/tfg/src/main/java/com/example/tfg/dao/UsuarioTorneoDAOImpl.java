package com.example.tfg.dao;

import com.example.tfg.model.Torneo;
import com.example.tfg.model.Usuario;
import com.example.tfg.model.UsuarioParticipaTorneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioTorneoDAOImpl implements IUsuarioTorneoDAO{

    @Autowired
    IUsuarioTorneoJPA usuarioTorneoJPA;

    @Override
    public List<UsuarioParticipaTorneo> buscarPorUsuario(Usuario usuario) {
        List<UsuarioParticipaTorneo> usuarioParticipaTorneo = usuarioTorneoJPA.findByIdusuario(usuario);
        return usuarioParticipaTorneo;
    }

    @Override
    public List<UsuarioParticipaTorneo> buscarPorTorneo(Torneo torneo) {
        List<UsuarioParticipaTorneo> usuarioParticipaTorneo = usuarioTorneoJPA.findByIdtorneo(torneo);
        return usuarioParticipaTorneo;
    }

    @Override
    public UsuarioParticipaTorneo buscarPorUsuarioYTorneo(Usuario usuario, Torneo torneo) {
        Optional<UsuarioParticipaTorneo> usuarioTorneo = usuarioTorneoJPA.findByIdusuarioAndIdtorneo(usuario,torneo);
        return usuarioTorneo.orElse(null);
    }

    @Override
    public void registrarUsuarioEnTorneo(UsuarioParticipaTorneo usuarioT) {
        usuarioTorneoJPA.save(usuarioT);
    }

}
