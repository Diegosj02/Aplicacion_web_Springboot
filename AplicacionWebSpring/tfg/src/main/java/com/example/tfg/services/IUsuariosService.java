package com.example.tfg.services;

import com.example.tfg.model.Usuario;

import java.util.List;

public interface IUsuariosService {
    Usuario buscarUsuarioPorCorreo(String correo);

    Usuario buscarUsuarioPorNombre(String nombre);

    Usuario buscarUsuarioPorNombreYContraseña(String nombre,String contraseña);

    Usuario buscarUsuarioPorCorreoYContraseña(String correo,String contraseña);

    boolean registrarUsuario(Usuario usuario);

    List<Usuario> getUsuarios();

    List<Usuario> getUsuariosPorPuntos(int puntos);

    Usuario buscarUsuarioPorId(int id);

    void actualizarUsuario(Usuario usuario);

    boolean eliminarUsuario(int idUsuario);
}
