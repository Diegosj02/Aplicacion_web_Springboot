package TFG.Front.tfgfront.controller;

import TFG.Front.tfgfront.model.*;
import TFG.Front.tfgfront.paginator.PageRender;
import TFG.Front.tfgfront.services.IMaterialService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mmateriales")
public class MaterialController {

    @Autowired
    IMaterialService materialService;

    @GetMapping("/listado")
    public String listadoMateriales(Model model, @RequestParam(name="page",defaultValue = "0")int page, HttpSession sesion){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        //Muestra los materiales en paginas de tamaño 100
        Pageable pageable = PageRequest.of(page,100);
        Page<Material> listado = materialService.buscarTodos(pageable);
        PageRender<Material> pageRender = new PageRender<>("/mmaterial/listado", listado);


        //Agregar atributos al modelo
        model.addAttribute("titulo","Lista de todos las articulos");
        model.addAttribute("listadoMateriales",listado);
        model.addAttribute("page",pageRender);
        model.addAttribute("usuarioActual",usuario);

        return "materiales/MaterialesAlquiler";
    }

    @GetMapping("/detallesMaterial/{idMaterial}")
    public String detallesArticulo(Pageable pageable,Model model,HttpSession sesion,@PathVariable("idMaterial")Integer idMaterial){
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        //Obtener el material por su ID
        Material material =  materialService.buscarMaterialPorId(idMaterial);
        //Obtener el listado de materiales
        Page<Material> listado = materialService.buscarTodos(pageable);

        //Agregar atributos al modelo
        model.addAttribute("listadoMateriales",listado);
        model.addAttribute("materialActual",material);
        model.addAttribute("titulo","Lista de todos las materiales");
        model.addAttribute("usuarioActual",usuario);

        return "materiales/detallesMaterial";
    }

    @GetMapping("/filtrarNombre")
    public String filtrarNombre(Model model,HttpSession sesion,@RequestParam String nombre,@RequestParam(name="page",defaultValue = "0")int page){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Configurar paginación
        Pageable pageable = PageRequest.of(page,100);

        //Verificar si el nombre está vacío y si lo esta, devolver todos los materiales
        if (nombre.isEmpty()){
            return "redirect:/mmateriales/listado";
        }

        //Obtener el listado paginado de materiales filtrados por nombre
        Page<Material> listado = materialService.buscarPorNombre(nombre,pageable);

        //Configurar la paginación para la vista
        PageRender<Material> pageRender = new PageRender<>("/mmateriales/listado", listado);

        //Agregar atributos al modelo
        model.addAttribute("titulo","Lista de todos las articulos");
        model.addAttribute("listadoMateriales",listado);
        model.addAttribute("page",pageRender);


        return "materiales/MaterialesAlquiler";
    }

    @GetMapping("/filtrarFormulario")
    public String filtrarFormulario( @RequestParam(name="page",defaultValue = "0")int page,Model model,HttpSession sesion,@RequestParam String precioMin,@RequestParam String precioMax,@RequestParam(name="ordenRadios", required = false) String orden){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Configurar paginación
        Pageable pageable = PageRequest.of(page,100);
        //Obtener el listado de todos los materiales
        Page<Material> listado = materialService.buscarTodos(pageable);
        //Verificar los campos del filtro
        if(precioMax.isEmpty()) {
            if (precioMin.isEmpty()) {
                if(orden==null) {
                    return "redirect:/mmateriales/listado";
                }
            }
            else {
                listado = materialService.buscarPorPrecioMin(Integer.parseInt(precioMin), pageable);
            }
        }else{
            if (precioMin.isEmpty()) {
                listado = materialService.buscarPorPrecioMax(Integer.parseInt(precioMax),pageable);
            } else {
                listado = materialService.buscarPorPrecioRango(Integer.parseInt(precioMin),Integer.parseInt(precioMax),pageable);
            }
        }
        //Verificar si se seleccionó algún orden
        if (orden!=null){
            if (orden.equals("alfabetico")){
                listado = materialService.ordenarPorCriterio("alfabetico",pageable,listado.getContent());
            }else if(orden.equals("precioAsc")){
                listado =materialService.ordenarPorCriterio("precioAsc",pageable,listado.getContent());
            }else{
                listado = materialService.ordenarPorCriterio("precioDesc",pageable,listado.getContent());
            }
        }
        //Configurar la paginación para la vista
        PageRender<Material> pageRender = new PageRender<>("/mmaterial/listado", listado);

        //Agregar atributos al modelo para enviar a la vista
        model.addAttribute("titulo","Lista de todos las articulos");
        model.addAttribute("listadoMateriales",listado);
        model.addAttribute("page",pageRender);

        return "materiales/MaterialesAlquiler";
    }

    @GetMapping("/formularioAlquiler")
    public String formularioAlquiler(HttpSession sesion,Model model,@RequestParam("idMaterial")int idMaterial){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Obtener el material por su ID
        Material material = materialService.buscarMaterialPorId(idMaterial);

        //Obtener la fecha actual
        LocalDate hoy = LocalDate.now();

        //Agregar atributos al modelo para enviar a la vista
        model.addAttribute("fechaSeleccionada",hoy);
        model.addAttribute("materialActual",material);

        return "alquiler/formularioAlquiler";
    }

    @GetMapping("/annadirLista")
    public String annadirMaterialLista(Model model, HttpSession sesion,@RequestParam("idMaterial")int idMaterial, @RequestParam(name="page",defaultValue = "0")int page){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Obtener el material por su ID
        Material material = materialService.buscarMaterialPorId(idMaterial);

        //Obtener la lista de materiales del carrito de la sesión
        List<Material> carritoMaterial = (List<Material>) sesion.getAttribute("carritoMaterial");

        //Agregar el material al carrito
        carritoMaterial.add(material);

        //Actualizar el carrito en la sesión
        sesion.setAttribute("carritoMaterial",carritoMaterial);

        //Configurar paginación
        Pageable pageable = PageRequest.of(page,100);

        //Obtener el listado paginado de materiales
        Page<Material> listado = materialService.buscarTodos(pageable);

        //Configurar la paginación para la vista
        PageRender<Material> pageRender = new PageRender<>("/mmaterial/listado", listado);

        //Agregar atributos al modelo
        model.addAttribute("titulo","Lista de todos las articulos");
        model.addAttribute("listadoMateriales",listado);
        model.addAttribute("page",pageRender);
        model.addAttribute("usuarioActual",usuario);

        return "materiales/MaterialesAlquiler";
    }

    @GetMapping("/opcionesAdministrador")
    public String opcionesAdministrador(Model model,HttpSession sesion,Pageable pageable){
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario==null || usuario.getId()!=-1){
            return "redirect:/uusuario";
        }else{
            //Agregar atributos al modelo
            model.addAttribute("materiales",materialService.buscarTodos(pageable));

            return "administracion/materiales";
        }
    }

    @GetMapping("/listadoAdministrador")
    public String listadoAdministrador(Model model,HttpSession sesion, @RequestParam(name="page", defaultValue="0") int
            page) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {

            //Configurar paginación
            Pageable pageable = PageRequest.of(page, 5);

            //Obtener el listado paginado de materiales
            Page<Material> listado = materialService.buscarTodos(pageable);

            //Configurar la paginación para la vista
            PageRender<Material> pageRender = new PageRender<>("/mmateriales/listadoAdministrador", listado);

            //Agregar atributos al modelo
            model.addAttribute("titulo", "Listado de todos los materiales");
            model.addAttribute("listadoMateriales", listado);
            model.addAttribute("page", pageRender);

            return "administracion/listaMateriales";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarMaterial(Model model, HttpSession sesion, @PathVariable("id") Integer id){
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {
            //Obtener el material por su ID
            Material material = materialService.buscarMaterialPorId(id);
            //Agregar atributos al modelo
            model.addAttribute("titulo", "Editar cancha");
            model.addAttribute("material", material);

            return "administracion/formMaterial";
        }
    }

    @PostMapping("/guardar/")
    public String guardarMaterial(Material material, RedirectAttributes attributes,HttpSession sesion, Model model) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Configurar la direccion de la imagen del material
        material.setImagen("/images/"+material.getImagen());

        //Guardar el material en la base de datos
        materialService.guardarMaterial(material);

        //Agregar mensaje de éxito para mostrar en la redirección
        attributes.addFlashAttribute("msg", "Los datos del material fueron guardados!");

        return "redirect:/mmateriales/listadoAdministrador";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model,HttpSession sesion) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Se crea un material nuevo
        Material material = new Material();

        //Agregar atributos al modelo para enviar a la vista
        model.addAttribute("titulo", "Nuevo material");
        model.addAttribute("material", material);

        return "administracion/formMaterial";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarMaterial(@PathVariable("id") Integer id, RedirectAttributes
            attributes,Model model,HttpSession sesion) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Eliminar el material de la base de datos
        materialService.eliminarMaterial(id);

        //Agregar mensaje de éxito para mostrar en la redirección
        attributes.addFlashAttribute("msg", "Los datos del material fueron borrados!");

        return "redirect:/mmateriales/listadoAdministrador";
    }

}
