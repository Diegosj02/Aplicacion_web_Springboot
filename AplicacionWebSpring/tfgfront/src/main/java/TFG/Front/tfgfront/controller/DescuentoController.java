package TFG.Front.tfgfront.controller;

import TFG.Front.tfgfront.model.*;
import TFG.Front.tfgfront.paginator.PageRender;
import TFG.Front.tfgfront.services.IArticuloService;
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

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("ddescuentos")
public class DescuentoController {

    @Autowired
    IDescuentoService descuentoService;
    @Autowired
    IArticuloService articuloService;
    @Autowired
    IUsuarioService usuarioService;

    @GetMapping("/listado")
    public String listado(Model model, @RequestParam(name="page",defaultValue = "0")int page, HttpSession sesion){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Muestra todos los descuentos que pertenecen al administrador en paginas de 100 descuentos
        Pageable pageable = PageRequest.of(page,100);
        Page<Descuento> listado = descuentoService.buscarPorUsuario(-1,pageable);
        PageRender<Descuento> pageRender = new PageRender<>("/ddescuentos/listado", listado);

        //Agregar atributos al modelo
        model.addAttribute("titulo","Lista de todos las descuentos");
        model.addAttribute("listadoDescuentos",listado);
        model.addAttribute("page",pageRender);
        model.addAttribute("usuarioActual",usuario);
        //Renderizar la vista correspondiente
        return "descuentos/tienda";
    }

    @GetMapping("/detallesDescuento/{idDescuento}")
    public String detallesDescuento(Pageable pageable, Model model, HttpSession sesion, @PathVariable("idDescuento")Integer idDescuento){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        //Recoger el descuento al que se quiere acceder
        Page<Descuento> listado = descuentoService.buscarTodos(pageable);
        Descuento descuento =  descuentoService.buscarPorId(idDescuento);

        //Agregar atributos al modelo
        model.addAttribute("listadoDescuentos",listado);
        model.addAttribute("descuentoActual",descuento);
        model.addAttribute("titulo",descuento.getId());
        model.addAttribute("usuarioActual",usuario);

        return "descuentos/detallesDescuento";
    }

    @GetMapping("/filtrarArticulo")
    public String filtrarArticulo(@RequestParam(name="page",defaultValue = "0")int page, Model model, HttpSession sesion, @RequestParam(value = "idArticulo", required = false)Integer idArticulo){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Si no se introduce ningún ID, se muestran todos los descuentos
        if (idArticulo==null){
            return "redirect:/ddescuentos/listado";
        }

        //Obtener el listado paginado de descuentos filtrado por artículo
        Pageable pageable = PageRequest.of(page,100);
        Page<Descuento> listado = descuentoService.buscarPorArticulo(idArticulo,pageable);
        PageRender<Descuento> pageRender = new PageRender<>("/ddescuentos/listado", listado);

        //Agregar atributos al modelo
        model.addAttribute("titulo","Lista de todos los descuentos");
        model.addAttribute("listadoDescuentos",listado);
        model.addAttribute("page",pageRender);


        return "descuentos/tienda";
    }

    @GetMapping("/filtrarFormulario")
    public String filtrarFormulario( @RequestParam(name="page",defaultValue = "0")int page,Model model,HttpSession sesion,@RequestParam String precioMin,@RequestParam String precioMax,@RequestParam(name="marcaRadios", required = false) String marca){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        //Declaramos la variable para guardar todos los descuentos
        Pageable pageable = PageRequest.of(page,100);
        Page<Descuento> listado;

        //Verificar los filtros aplicados
        if(precioMax.isEmpty()) {
            if (precioMin.isEmpty()) {
                if (marca == null) {
                    return "redirect:/ddescuentos/listado";
                } else {
                    if (marca.equals("valor")) {
                        listado = descuentoService.buscarPorTipo("valor", pageable);
                    } else {
                        listado = descuentoService.buscarPorTipo("porcentaje", pageable);
                    }
                }
            } else {
                if (marca == null) {
                    listado = descuentoService.buscarPorPrecioMin(Integer.parseInt(precioMin), pageable);
                } else {
                    if (marca.equals("valor")) {
                        listado = descuentoService.buscarPorTipoYPrecioMin("valor", Integer.parseInt(precioMin), pageable);
                    } else {
                        listado = descuentoService.buscarPorTipoYPrecioMin("porcentaje", Integer.parseInt(precioMin), pageable);
                    }
                }
            }
        }else{
            if (precioMin.isEmpty()) {
                if (marca == null) {
                    listado = descuentoService.buscarPorPrecioMax(Integer.parseInt(precioMax),pageable);
                } else {
                    if (marca.equals("valor")) {
                        listado = descuentoService.buscarPorTipoYPrecioMax("valor",Integer.parseInt(precioMax),pageable);
                    } else {
                        listado = descuentoService.buscarPorTipoYPrecioMax("porcentaje",Integer.parseInt(precioMax), pageable);
                    }
                }
            }
            else {
                if (marca == null) {
                    listado = descuentoService.buscarPorPrecioRango(Integer.parseInt(precioMin),Integer.parseInt(precioMax), pageable);
                }
                else {
                    if (marca.equals("valor")) {
                        listado = descuentoService.buscarPorTipoYPrecioRang("valor", Integer.parseInt(precioMin),Integer.parseInt(precioMax), pageable);
                    }
                    else {
                        listado = descuentoService.buscarPorTipoYPrecioRang("porcentaje", Integer.parseInt(precioMin),Integer.parseInt(precioMax), pageable);
                    }
                }
            }
        }
        PageRender<Descuento> pageRender = new PageRender<>("/aarticulos/listado", listado);

        //Agregar atributos al modelo
        model.addAttribute("titulo","Lista de todos las articulos");
        model.addAttribute("listadoDescuentos",listado);
        model.addAttribute("page",pageRender);
        model.addAttribute("usuarioActual",usuario);

        return "descuentos/tienda";
    }

    @PostMapping("/comprarDescuento")
    public String comprarDescuento(HttpSession sesion, Model model, @RequestParam("descuento")String idDescuento,RedirectAttributes attributes){
        //Se busca el descuento que se quiere comprar
        Descuento descuento = descuentoService.buscarPorId(Integer.parseInt(idDescuento));

        //Se rescata al usuario de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Si el usuario tiene menos puntos que el coste del descuento, se envia un error
        if (usuario.getPuntos()<=descuento.getCoste()){
            model.addAttribute("msgE", "No posees de puntos suficientes");
            Pageable pageable = PageRequest.of(0,100);
            Page<Descuento> listado = descuentoService.buscarPorUsuario(-1,pageable);
            PageRender<Descuento> pageRender = new PageRender<>("/ddescuentos/listado", listado);

            //Agregar atributos al modelo
            model.addAttribute("titulo","Lista de todos las descuentos");
            model.addAttribute("listadoDescuentos",listado);
            model.addAttribute("page",pageRender);
            model.addAttribute("usuarioActual",usuario);
            //Renderizar la vista correspondiente
            return "descuentos/tienda";
        }else{
            //Si tiene los puntos necesarios, se le consumen y se actualiza el usuario
            usuario.setPuntos(usuario.getPuntos()-descuento.getCoste());
            usuarioService.guardarUsuario(usuario);
        }
        //Se genera un descuento igual al comprado, pero con un ID aleatorio entre 10.000.000 y 99.999.999 (8 digitos)
        Random random = new Random();
        Descuento descuentoComprado = new Descuento(random.nextInt(10000000,99999999),descuento.getTipo(),descuento.getValor(),descuento.getCoste(),descuento.getArticulo(),usuario,descuento.getImagen());

        //Se comprueba que ese código no exista ya en la base de datos. En caso de existir, se genera un nuevo ID aleatorio

        String veredicto = descuentoService.registrarDescuento(descuentoComprado);
        while (veredicto.equals("false")){
            descuentoComprado.setId(random.nextInt(10000000,99999999));
            veredicto = descuentoService.registrarDescuento(descuentoComprado);
        }
        model.addAttribute("msg", "Descuento comprado con exito");
        Pageable pageable = PageRequest.of(0,100);
        Page<Descuento> listado = descuentoService.buscarPorUsuario(-1,pageable);
        PageRender<Descuento> pageRender = new PageRender<>("/ddescuentos/listado", listado);

        //Agregar atributos al modelo
        model.addAttribute("titulo","Lista de todos las descuentos");
        model.addAttribute("listadoDescuentos",listado);
        model.addAttribute("page",pageRender);
        model.addAttribute("usuarioActual",usuario);
        //Renderizar la vista correspondiente
        return "descuentos/tienda";
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

            model.addAttribute("descuentos",descuentoService.buscarTodos(pageable));

            return "administracion/descuentos";
        }
    }

    @GetMapping("/listadoAdministrador")
    public String listadoAdministrador(Model model,HttpSession sesion, @RequestParam(name="page", defaultValue="0") int page) {

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {
            //Muestra una lista en grupos de 5 de todos los descuentos
            Pageable pageable = PageRequest.of(page, 5);
            Page<Descuento> listado = descuentoService.buscarPorUsuario(-1,pageable);
            PageRender<Descuento> pageRender = new PageRender<>("/ddescuentos/listadoAdministrador", listado);

            //Agregar atributos al modelo
            model.addAttribute("titulo", "Lstado de todos los descuentos");
            model.addAttribute("listadoDescuentos", listado);
            model.addAttribute("page", pageRender);

            return "administracion/listaDescuentos";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarDescuento(Model model, HttpSession sesion, @PathVariable("id") Integer id){

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {

            //Obtener el descuento por su ID
            Descuento descuento = descuentoService.buscarPorId(id);

            //Obtener todos los artículos para mostrar en el formulario de edición
            Pageable pageable = PageRequest.of(0, 1000000000);
            List<Articulo> articulos = articuloService.buscarTodos(pageable).getContent();

            //Agregar atributos al modelo
            model.addAttribute("titulo", "Editar descuento");
            model.addAttribute("descuento", descuento);
            model.addAttribute("articulos",articulos);
            return "administracion/formDescuento";

        }
    }

    @PostMapping("/guardar/")
    public String guardarDescuento(Model model, Descuento descuento, RedirectAttributes attributes,HttpSession sesion) {

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Configurar la imagen del descuento
        descuento.setImagen("/images/"+descuento.getImagen());
        descuento.setArticulo(articuloService.buscarArticuloPorId(descuento.getArticulo().getId()));
        descuento.setUsuario(usuarioService.buscarUsuarioPorId(-1));
        //Actualizar el descuento en la base de datos
        descuentoService.actualizarDescuento(descuento);

        //Agregar mensaje de éxito para mostrar en la redirección
        model.addAttribute("titulo", "Nuevo Descuento");
        attributes.addFlashAttribute("msg", "Los datos del descuento fueron guardados!");

        return "redirect:/ddescuentos/listadoAdministrador";
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

        Descuento descuento = new Descuento();
        //Obtener todos los artículos para mostrar en el formulario de creación
        Pageable pageable = PageRequest.of(0, 1000000000);
        List<Articulo> articulos = articuloService.buscarTodos(pageable).getContent();

        //Agregar atributos al modelo
        model.addAttribute("titulo", "Nuevo descuento");
        model.addAttribute("articulos",articulos);
        model.addAttribute("descuento", descuento);

        return "administracion/formDescuento";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarDescuento(@PathVariable("id") Integer id, RedirectAttributes
            attributes,HttpSession sesion, Model model) {

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Eliminar el descuento de la base de datos
        descuentoService.eliminarDescuento(id);

        //Agregar mensaje de éxito para mostrar en la redirección
        attributes.addFlashAttribute("msg", "Los datos del descuento fueron borrados!");

        return "redirect:/ddescuentos/listadoAdministrador";
    }
}