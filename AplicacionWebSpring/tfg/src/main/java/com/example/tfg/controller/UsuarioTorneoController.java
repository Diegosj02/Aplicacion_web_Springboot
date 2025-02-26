package com.example.tfg.controller;

import com.example.tfg.model.Torneo;
import com.example.tfg.model.Usuario;
import com.example.tfg.model.UsuarioParticipaTorneo;
import com.example.tfg.services.ITorneoServicio;
import com.example.tfg.services.IUsuarioTorneoServicio;
import com.example.tfg.services.IUsuariosService;
import com.example.tfg.services.UsuarioTorneoServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioTorneoController {

    @Autowired
    IUsuarioTorneoServicio usuarioTorneoServicio;
    @Autowired
    IUsuariosService usuariosService;
    @Autowired
    ITorneoServicio torneoServicio;

    @GetMapping("/usuarioTorneo/Usuario/{id}")
    public List<UsuarioParticipaTorneo> buscarPorUsuario(@PathVariable("id")int id){
        Usuario usuario = usuariosService.buscarUsuarioPorId(id);
        return usuarioTorneoServicio.buscarPorUsuario(usuario);
    }

    @GetMapping("/usuarioTorneo/Torneo/{id}")
    public List<UsuarioParticipaTorneo> buscarPorTorneo(@PathVariable("id")int id){
        Torneo torneo = torneoServicio.buscarPorId(id);
        return usuarioTorneoServicio.buscarPorTorneo(torneo);
    }

    @GetMapping("/usuarioTorneo/Usuario/{idU}/Torneo/{idT}")
    public UsuarioParticipaTorneo buscarPorUsuarioYTorneo(@PathVariable("idU")int idU, @PathVariable("idT")int idT){
        Usuario usuario = usuariosService.buscarUsuarioPorId(idU);
        Torneo torneo = torneoServicio.buscarPorId(idT);
        return usuarioTorneoServicio.buscarPorUsuarioYTorneo(usuario,torneo);
    }

    @PostMapping(value = "/usuarioTorneo", produces = MediaType.TEXT_PLAIN_VALUE)
    public String registrarUsuarioEnTorneo(@RequestBody UsuarioParticipaTorneo usuarioT){
        return String.valueOf(usuarioTorneoServicio.registrarUsuarioEnTorneo(usuarioT));
    }

}
