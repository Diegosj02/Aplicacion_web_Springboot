package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService {

    //Busca todos los usuarios paginados
    Page<Usuario> buscarTodos(Pageable pageable);

    //Busca un usuario por su ID
    Usuario buscarUsuarioPorId(Integer idUsuario);

    //Busca un usuario por su correo electrónico
    Usuario buscarUsuarioPorCorreo(String correo);

    //Busca un usuario por su nombre
    Usuario buscarUsuarioPorNombre(String nombre);

    //Busca un usuario por su nombre y contraseña
    Usuario buscarUsuarioPorNombreYContrasenna(String nombre, String contrasenna);

    //Busca un usuario por su correo electrónico y contraseña
    Usuario buscarUsuarioPorCorreoYContrasenna(String correo, String contrasenna);

    // Método para obtener usuarios por puntos, paginados
    Page<Usuario> getUsuariosPorPuntos(Integer puntos, Pageable pageable);

    // Método para guardar un nuevo usuario
    void guardarUsuario(Usuario usuario);

    // Método para eliminar un usuario por su ID
    void eliminarUsuario(Integer idUsuario);

    // Método para registrar un nuevo usuario y retornar un mensaje de confirmación
    String registrarUsuario(Usuario usuario);


}
