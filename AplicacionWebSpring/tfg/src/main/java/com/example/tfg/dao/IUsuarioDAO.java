package com.example.tfg.dao;

import com.example.tfg.model.Usuario;

import java.util.List;

public interface IUsuarioDAO {
    Usuario buscarUsuarioPorCorreo(String correo);

    Usuario buscarUsuarioPorNombre(String nombre);

    Usuario buscarUsuarioPorNombreYContraseña(String nombre, String contraseña);

    Usuario buscarUsuarioPorCorreoYContraseña(String correo, String contraseña);

    void registrarUsuario(Usuario usuario);

    List<Usuario> getUsuarios();

    List<Usuario> getUsuariosPorPuntos(int puntos);

    Usuario buscarUsuarioPorId(int id);

    void eliminarUsuario(int id);

    void actualizarUsuario(Usuario usuario);
}
