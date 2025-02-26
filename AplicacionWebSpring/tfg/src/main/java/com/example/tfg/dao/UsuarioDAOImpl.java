package com.example.tfg.dao;

import com.example.tfg.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAOImpl implements IUsuarioDAO {

    @Autowired
    IUsuarioJPA usuarioJPA;
    @Override
    public Usuario buscarUsuarioPorCorreo(String correo) {
        Optional<Usuario> usuario = usuarioJPA.findByCorreoIgnoreCase(correo);
        if (usuario.isPresent()){
            return usuario.get();
        }
        return null;
    }

    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) {
        Optional<Usuario> usuario = usuarioJPA.findByNombreIgnoreCase(nombre);
        if (usuario.isPresent()){
            return usuario.get();
        }
        return null;
    }

    @Override
    public Usuario buscarUsuarioPorNombreYContraseña(String nombre,String contraseña) {
        Optional<Usuario> usuario = usuarioJPA.findByNombreAndContraseña(nombre,contraseña);
        if (usuario.isPresent()){
                return usuario.get();
        }
        return null;
    }

    @Override
    public Usuario buscarUsuarioPorCorreoYContraseña(String correo,String contraseña) {
        Optional<Usuario> usuario = usuarioJPA.findByCorreoAndContraseña(correo,contraseña);
        if (usuario.isPresent()){
            return usuario.get();
        }
        return null;
    }

    @Override
    public Usuario buscarUsuarioPorId(int id) {
        Optional<Usuario> usuario = usuarioJPA.findById(id);
        if (usuario.isPresent()){
            return usuario.get();
        }
        return null;
    }

    @Override
    public void registrarUsuario(Usuario usuario){
        usuarioJPA.save(usuario);
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioJPA.findAll();
    }

    @Override
    public List<Usuario> getUsuariosPorPuntos(int puntos) {
        return usuarioJPA.findByPuntos(puntos);
    }

    @Override
    public void eliminarUsuario(int idUsuario){
        usuarioJPA.deleteAllById(Collections.singleton(idUsuario));
    }

    @Override
    public void actualizarUsuario(Usuario usuario){
        usuarioJPA.save(usuario);
    }
}
