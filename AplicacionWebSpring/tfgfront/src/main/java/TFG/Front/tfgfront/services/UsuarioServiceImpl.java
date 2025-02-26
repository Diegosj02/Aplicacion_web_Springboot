package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    RestTemplate template;

    //Permite acceder al back-end y a la DDBB
    String url = "http://localhost:8080/usuarios";

    //Devuelve todos los usuarios en paginas de tamaño especificado
    @Override
    public Page<Usuario> buscarTodos(Pageable pageable) {
        Usuario[] usuarios = template.getForObject(url+"/lista",Usuario[].class);
        List<Usuario> usuariosList = Arrays.asList(usuarios);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Usuario> list;

        if (usuariosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,usuariosList.size());
            list = usuariosList.subList(startItem,toIndex);
        }
        Page<Usuario> page = new PageImpl<>(list,PageRequest.of(currentPage,pageSize),usuariosList.size());
        return page;
    }

    //Devuelve un usuario con la ID especifica y, si no lo encuentra, devuelve null
    @Override
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        Usuario usuario = template.getForObject(url + "/id/" + idUsuario,Usuario.class);
        return usuario;
    }

    //Busca un usuario por correo y, si no lo encuentra, devuelve null
    @Override
    public Usuario buscarUsuarioPorCorreo(String correo) {
        Usuario usuario = template.getForObject(url + "/correo" + correo,Usuario.class);
        return usuario;
    }

    //Busca un usuario por nombre y, si no lo encuentra, devuelve null
    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) {
        Usuario usuario = template.getForObject(url + "/nombre" + nombre,Usuario.class);
        return usuario;
    }

    //Busca un usuario por nombre y contraseña y, si no lo encuentra, devuelve null
    @Override
    public Usuario buscarUsuarioPorNombreYContrasenna(String nombre, String contraseña) {
        Usuario usuario = template.getForObject(url + "/nombreYcontraseña/" + nombre + "/" + contraseña,Usuario.class);
        return usuario;
    }

    //Busca un usuario por correo y contraseña y, si no lo encuentra, devuelve null
    @Override
    public Usuario buscarUsuarioPorCorreoYContrasenna(String correo, String contraseña) {
        Usuario usuario = template.getForObject(url + "/correoYcontraseña/" + correo + "/" + contraseña,Usuario.class);
        if (usuario == null){
            return null;
        }
        return usuario;
    }

    //Busca los usuarios que tengan los puntos especificados
    @Override
    public Page<Usuario> getUsuariosPorPuntos(Integer puntos, Pageable pageable) {
        Usuario[] usuarios = template.getForObject(url + "/puntos" + puntos,Usuario[].class);
        List<Usuario> usuariosList = Arrays.asList(usuarios);
        Page<Usuario> page = new PageImpl<>(usuariosList,pageable,usuariosList.size());
        return page;
    }

    //Permite actualizar y guardar nuevos usuarios
    @Override
    public void guardarUsuario(Usuario usuario) {
        if (usuario.getId() > 0){
            template.put(url,usuario);
        }else{
            usuario.setId(0);
            template.postForObject(url,usuario,String.class);
        }
    }

    //Permite eliminar usuarios
    @Override
    public void eliminarUsuario(Integer idUsuario) {
        template.delete(url + "/" + idUsuario);
    }

    //Permite registrar nuevos usuarios
    @Override
    public String registrarUsuario(Usuario usuario) {
        return template.postForObject(url,usuario,String.class);
    }
}
