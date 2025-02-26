package com.example.tfg.services;

import com.example.tfg.dao.IUsuarioDAO;
import com.example.tfg.model.Usuario;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosServiceImpl implements IUsuariosService{

    @Autowired
    IUsuarioDAO usuariodao;
    @Override
    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuariodao.buscarUsuarioPorCorreo(correo);
    }

    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) {
        return usuariodao.buscarUsuarioPorNombre(nombre);
    }

    @Override
    public Usuario buscarUsuarioPorNombreYContraseña(String nombre, String contraseña){
        return usuariodao.buscarUsuarioPorNombreYContraseña(nombre,contraseña);
    }

    @Override
    public Usuario buscarUsuarioPorCorreoYContraseña(String correo, String contraseña){
        return usuariodao.buscarUsuarioPorCorreoYContraseña(correo,contraseña);
    }

    @Override
    public Usuario buscarUsuarioPorId(int id){
        return usuariodao.buscarUsuarioPorId(id);
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        if (usuariodao.buscarUsuarioPorNombre(usuario.getNombre())==null
        && usuariodao.buscarUsuarioPorCorreo(usuario.getCorreo())==null){
            usuariodao.registrarUsuario(usuario);
            return true;
        }
        return false;
    }



    @Override
    public List<Usuario> getUsuarios() {
        return usuariodao.getUsuarios();
    }

    @Override
    public List<Usuario> getUsuariosPorPuntos(int puntos) {
        return usuariodao.getUsuariosPorPuntos(puntos);
    }

    @Override
    public void actualizarUsuario(Usuario usuario){
        if (usuariodao.buscarUsuarioPorId(usuario.getId())!=null){
            usuariodao.actualizarUsuario(usuario);
        }
    }

    @Override
    public boolean eliminarUsuario(int idUsuario){
        if (usuariodao.buscarUsuarioPorId(idUsuario) != null){
            usuariodao.eliminarUsuario(idUsuario);
            return true;
        }
        return false;
    }

}
