package com.example.tfg.services;

import com.example.tfg.dao.IUsuarioTorneoDAO;
import com.example.tfg.dao.UsuarioTorneoDAOImpl;
import com.example.tfg.model.Torneo;
import com.example.tfg.model.Usuario;
import com.example.tfg.model.UsuarioParticipaTorneo;
import com.example.tfg.model.UsuarioParticipaTorneoId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioTorneoServicioImpl implements IUsuarioTorneoServicio{

    @Autowired
    IUsuarioTorneoDAO usuarioTorneoDAO;

    @Override
    public List<UsuarioParticipaTorneo> buscarPorUsuario(Usuario usuario) {
        return usuarioTorneoDAO.buscarPorUsuario(usuario);
    }

    @Override
    public List<UsuarioParticipaTorneo> buscarPorTorneo(Torneo torneo) {
        return usuarioTorneoDAO.buscarPorTorneo(torneo);
    }

    @Override
    public UsuarioParticipaTorneo buscarPorUsuarioYTorneo(Usuario usuario, Torneo torneo) {
        return usuarioTorneoDAO.buscarPorUsuarioYTorneo(usuario,torneo);
    }

    @Override
    public boolean registrarUsuarioEnTorneo(UsuarioParticipaTorneo usuarioT) {
        if (usuarioTorneoDAO.buscarPorUsuarioYTorneo(usuarioT.getIdusuario(),usuarioT.getIdtorneo()) == null){
            usuarioTorneoDAO.registrarUsuarioEnTorneo(usuarioT);
            return true;
        }
        return false;
    }

}
