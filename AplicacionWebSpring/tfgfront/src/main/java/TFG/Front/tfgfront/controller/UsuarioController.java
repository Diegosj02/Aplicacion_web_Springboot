package TFG.Front.tfgfront.controller;

import TFG.Front.tfgfront.model.Articulo;
import TFG.Front.tfgfront.model.Descuento;
import TFG.Front.tfgfront.model.Material;
import TFG.Front.tfgfront.model.Usuario;
import TFG.Front.tfgfront.paginator.PageRender;
import TFG.Front.tfgfront.services.IDescuentoService;
import TFG.Front.tfgfront.services.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value={"/uusuario",""})
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;
    @Autowired
    IDescuentoService descuentoService;

    @GetMapping(value = {"/","/home",""})
    public String home(Model model,HttpSession sesion){
        //Pagina principal
        //Se coge el usuario de la sesión
        Usuario usuarioActual = (Usuario) sesion.getAttribute("usuario");

        //Se añade al modelo
        model.addAttribute("usuarioActual",usuarioActual);
        return "home";
    }

    @GetMapping("/inicioSesion")
    public String inicioSesionUsuario(Model model){
        //Abre el formulario para iniciar Sesion
        model.addAttribute("titulo","Iniciar Sesion");
        model.addAttribute("usuarioActual",null);
        return "usuario/inicioSesión";
    }

    @GetMapping(value={"/cerrarSesion","/inicio"})
    public String cerrarSesion(Model model,HttpSession sesion){
        //Establece el usuario a null para cerrar sesion
        model.addAttribute("usuarioActual", null);
        sesion.setAttribute("usuario",null);
        sesion.setAttribute("carritoArticulo",null);
        sesion.setAttribute("carritoMaterial",null);
        return "home";
    }

    @GetMapping("/nuevoUsuario")
    public String nuevoUsuario(Model model){
        //Abre el formulario de registro de usuario
        model.addAttribute("titulo","Nuevo usuario");
        model.addAttribute("usuarioActual",null);
        return "usuario/registro";
    }

    @GetMapping("/datosUsuario")
    public String datosUsuario(Model model,HttpSession sesion,Pageable pageable){
        //Recoge el usuario de la sesion, sus descuentos y muestra en la pagina todos los datos
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        if (usuario == null){
            return "redirect:/uusuario/";
        }

        model.addAttribute("usuarioVer",usuario);
        List<Descuento> descuentos = descuentoService.buscarPorUsuario(usuario.getId(),pageable).getContent();
        model.addAttribute("descuentosUser",descuentos);
        return "usuario/datosUsuario";
    }

    @GetMapping("/verDatosUsuario/{id}")
    public String verDatosUsuario(Model model,HttpSession sesion,Pageable pageable,@PathVariable("id")int id){
        //Busca al usuario por ID y muestra sus datos y descuentos
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        if (usuario == null){
            return "redirect:/uusuario/";
        }

        Usuario verUsuario = usuarioService.buscarUsuarioPorId(id);
        model.addAttribute("usuarioVer",verUsuario);
        List<Descuento> descuentos = descuentoService.buscarPorUsuario(verUsuario.getId(),pageable).getContent();
        model.addAttribute("descuentosUser",descuentos);
        return "usuario/datosUsuario";
    }

    @GetMapping("/listadoUsuarios")
    public String listadoUsuario(Model model, @RequestParam(name="page",defaultValue ="0")int page,HttpSession sesion){

        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Muestra los usuarios en grupos de 15 en 15
        Pageable pageable = PageRequest.of(page,15);
        Page<Usuario> listado = usuarioService.buscarTodos(pageable);
        PageRender<Usuario> pageRender = new PageRender<>("/uusuario/listadoUsuarios", listado);
        model.addAttribute("titulo","Listado de todos los usuarios");
        model.addAttribute("listadoUsuarios",listado);
        model.addAttribute("page",pageRender);
        return "administracion/listUsuario";
    }

    @GetMapping("/nombreContraseñaUsuario")
    public String buscarUsuarioPorNombreYContraseña(@RequestParam String nombre, @RequestParam String contraseña, RedirectAttributes attributes, HttpSession sesion){
        //Comprueba si el nombre de usuario y contraseña coinciden en la base de datos
        Usuario usuario = usuarioService.buscarUsuarioPorNombreYContrasenna(nombre,contraseña);
        if (usuario == null){
            //Si no coinciden, se manda un mensaje de error
            attributes.addFlashAttribute("msg", "Nombre o contraseña invalida");
            return "redirect:/uusuario/inicioSesion";
        }
        //Si coinciden, se inicia sesión
        sesion.setAttribute("usuario",usuario);
        List<Articulo> carroArticulos = new ArrayList<>();
        sesion.setAttribute("carritoArticulo",carroArticulos);
        List<Material> carritoMaterial = new ArrayList<>();
        sesion.setAttribute("carritoMaterial",carritoMaterial);
        return "redirect:/uusuario";
    }

    @PostMapping("/guardarUsuario/")
    public String guardarUsuario(Model model, Usuario usuario, RedirectAttributes attributes){
        //Comprueba que el nombre de usuario y su correo no existen en la base de datos
        String veredicto = usuarioService.registrarUsuario(usuario);
        if (veredicto.equals("false")){
            //Si existen, salta un mensaje de error
            attributes.addFlashAttribute("msg", "Este Correo o usuario ya está registrado.");
            return "redirect:/uusuario/nuevoUsuario";
        }else{
            //Si no, se registra correctamente
            attributes.addFlashAttribute("msg","Los datos del nuevo usuario han sido guardados");
        }
        //Se guarda el usuario en la base de datos
        usuarioService.guardarUsuario(usuario);
        model.addAttribute("titulo","Nuevo usuario");
        return "redirect:/uusuario/";
    }

    @GetMapping("/borrarUsuario/{id}")
    public String eliminarUsuario(Model model,@PathVariable("id") Integer id,HttpSession sesion){
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Elimina al usuario de la base de datos
        usuarioService.eliminarUsuario(id);
        //Salta un mensaje de la transaccion
        model.addAttribute("msg","Los datos del usuario han sido eliminados");
        return "redirect:/uusuario/listadoUsuarios";
    }

}
