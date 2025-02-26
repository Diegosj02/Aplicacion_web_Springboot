package TFG.Front.tfgfront.controller;

import TFG.Front.tfgfront.model.*;
import TFG.Front.tfgfront.paginator.PageRender;
import TFG.Front.tfgfront.services.ICanchaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ccanchas")
public class CanchaController {

    @Autowired
    ICanchaService canchaService;

    @GetMapping("/listado")
    public String listadoCanchas(Model model, @RequestParam(name="page",defaultValue = "0")int page, HttpSession sesion){
        
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Muestra todas las canchas en paginas en grupos de 100
        Pageable pageable = PageRequest.of(page,100);
        Page<Cancha> listado = canchaService.buscarTodos(pageable);
        PageRender<Cancha> pageRender = new PageRender<>("/ccanchas/listado",listado);
        
        //Agregar atributos al modelo
        model.addAttribute("titulo","Lista de todas las canchas");
        model.addAttribute("listadoCanchas",listado);
        model.addAttribute("page",pageRender);
        
        return "canchas/instalaciones";
    }

    
    @GetMapping("/detallesCancha/{idCancha}")
    public String detallesCancha(Pageable pageable,Model model,HttpSession sesion,@PathVariable("idCancha")Integer idCancha){
        
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Recoge los datos de la cancha seleccionada
        Page<Cancha> listado = canchaService.buscarTodos(pageable);
        Cancha cancha =  canchaService.buscarCanchaPorId(idCancha);
        
        //Agregar atributos al modelo
        model.addAttribute("listadoCanchas",listado);
        model.addAttribute("canchaActual",cancha);
        sesion.setAttribute("canchaActual",cancha);
        model.addAttribute("titulo",cancha.getId());

        return "canchas/detallesCancha";
    }

    
    @GetMapping("/opcionesAdministrador")
    public String opcionesAdministrador(Model model,HttpSession sesion,Pageable pageable){
        
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es un administrador
        if (usuario==null || usuario.getId()!=-1){
            return "redirect:/uusuario";
        }else{
            
        //Agregar atributos al modelo

            model.addAttribute("canchas",canchaService.buscarTodos(pageable));
            
            return "administracion/instalaciones";
        }
    }

    
    @GetMapping("/listadoAdministrador")
    public String listadoAdministrador(Model model,HttpSession sesion, @RequestParam(name="page", defaultValue="0") int page) {
        
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Verificar si el usuario es un administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {

            //Muestra todas las canchas en paginas de 5 en 5
            Pageable pageable = PageRequest.of(page, 5);
            Page<Cancha> listado = canchaService.buscarTodos(pageable);
            PageRender<Cancha> pageRender = new PageRender<>("/ccanchas/listadoAdministrador", listado);
            
            //Agregar atributos al modelo

            model.addAttribute("titulo", "Listado de todas las canchas");
            model.addAttribute("listadoCanchas", listado);
            model.addAttribute("page", pageRender);
            
            return "administracion/listaCanchas";
        }
    }

    
    @GetMapping("/editar/{id}")
    public String editarCancha(Model model, HttpSession sesion, @PathVariable("id") Integer id){
        
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Verificar si el usuario es un administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {
            
            //Obtener la cancha a editar por su ID
            Cancha cancha = canchaService.buscarCanchaPorId(id);
            
            //Agregar atributos al modelo

            model.addAttribute("titulo", "Editar cancha");
            model.addAttribute("cancha", cancha);
            
            return "administracion/formCancha";
        }
    }

    
    @PostMapping("/guardar/")
    public String guardarCancha(Model model, Cancha cancha, RedirectAttributes attributes,HttpSession sesion) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Verificar si el usuario es un administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Actualizar la ruta de la imagen de la cancha
        cancha.setImagen("/images/"+cancha.getImagen());
        
        //Guardar la cancha
        canchaService.guardarCancha(cancha);
        
        //Agregar atributos al modelo
        model.addAttribute("titulo", "Nuevo Cancha");
        attributes.addFlashAttribute("msg", "Los datos de la cancha fueron guardados!");
        
        return "redirect:/ccanchas/listadoAdministrador";
    }

    
    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession sesion) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Verificar si el usuario es un administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Se crea una cancha nueva sin datos
        Cancha cancha = new Cancha();

        //Agregar atributos al modelo
        model.addAttribute("titulo", "Nueva cancha");
        model.addAttribute("cancha", cancha);
        
        return "administracion/formCancha";
    }

    
    @GetMapping("/borrar/{id}")
    public String eliminarCancha(@PathVariable("id") Integer id, RedirectAttributes
            attributes,HttpSession sesion,Model model) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Verificar si el usuario es un administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Eliminar la cancha por su ID
        canchaService.eliminarCancha(id);
        
        //Agregar mensaje de éxito para mostrar en la redirección
        attributes.addFlashAttribute("msg", "Los datos de la cancha fueron borrados!");

        return "redirect:/ccanchas/listadoAdministrador";
    }
}
