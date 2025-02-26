package com.example.tfg.controller;

import com.example.tfg.model.Usuario;
import com.example.tfg.services.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UsuarioController {

    @Autowired
    IUsuariosService usuarioService;

    @GetMapping("/usuarios/correo/{correo}")
    public Usuario buscarPorCorreo(@PathVariable("correo") String correo){
        return usuarioService.buscarUsuarioPorCorreo(correo);
    }

    @GetMapping("/usuarios/id/{id}")
    public Usuario buscarPorId(@PathVariable("id") int id){
        return usuarioService.buscarUsuarioPorId(id);
    }

    @GetMapping("/usuarios/nombre/{nombre}")
    public Usuario buscarPorNombre(@PathVariable("nombre") String nombre){
        return usuarioService.buscarUsuarioPorNombre(nombre);
    }

    @GetMapping("/usuarios/nombreYcontraseña/{nombre}/{contraseña}")
    public Usuario buscarPorNombreYContraseña(@PathVariable("nombre") String nombre,@PathVariable("contraseña") String contraseña){
        return usuarioService.buscarUsuarioPorNombreYContraseña(nombre,contraseña);
    }

    @GetMapping("/usuarios/correoYcontraseña/{correo}/{contraseña}")
    public Usuario buscarPorCorreoYContraseña(@PathVariable("correo") String correo,@PathVariable("contraseña") String contraseña){
        return usuarioService.buscarUsuarioPorCorreoYContraseña(correo,contraseña);
    }

    @GetMapping("/usuarios/lista")
    public List<Usuario> getUsuarios(){
        return usuarioService.getUsuarios();
    }

    @GetMapping("/usuarios/puntos/{puntos}")
    public List<Usuario> getUsuariosPorPuntos(@PathVariable("puntos") int puntos){
        return usuarioService.getUsuariosPorPuntos(puntos);
    }

    @PostMapping(value = "/usuarios", produces = MediaType.TEXT_PLAIN_VALUE)
    public String registrarUsuario(@RequestBody Usuario usuario){
        return String.valueOf(usuarioService.registrarUsuario(usuario));
    }

    @PutMapping("/usuarios")
    public void actualizarUsuario(@RequestBody Usuario usuario) {
        usuarioService.actualizarUsuario(usuario);
    }

    @DeleteMapping(value = "/usuarios/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eliminarUsuario(@PathVariable("id") int id) {
        return String.valueOf(usuarioService.eliminarUsuario(id));
    }



}
