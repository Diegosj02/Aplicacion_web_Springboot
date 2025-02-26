package com.example.tfg.dao;

import com.example.tfg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioJPA extends JpaRepository<Usuario, Integer> {

    //SELECT * FROM USUARIO WHERE LOWER(NOMBRE) = LOWER(?)
    Optional<Usuario> findByNombreIgnoreCase(String nombre);

    //SELECT * FROM USUARIO WHERE LOWER(CORREO) = LOWER(?)
    Optional<Usuario> findByCorreoIgnoreCase(String correo);

    //SELECT * FROM USUARIO WHERE NOMBRE = ? AND CONTRASEÑA = ?
    Optional<Usuario> findByNombreAndContraseña(String nombre, String contraseña);

    //SELECT * FROM USUARIO WHERE CORREO = ? AND CONTRASEÑA = ?
    Optional<Usuario> findByCorreoAndContraseña(String correo, String contraseña);

    //SELECT * FROM USUARIO WHERE ID = ?
    Optional<Usuario> findById(int id);

    //SELECT * FROM USUARIO WHERE PUNTOS = ?
    List<Usuario>  findByPuntos(int puntos);
}