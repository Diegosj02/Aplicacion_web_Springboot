package TFG.Front.tfgfront.controller;

import TFG.Front.tfgfront.model.*;
import TFG.Front.tfgfront.paginator.PageRender;
import TFG.Front.tfgfront.services.*;
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
import java.util.List;

@Controller
@RequestMapping("/nnoticias")
public class NoticiaController {
    
    @Autowired
    INoticiaService noticiaService;
    @Autowired
    ITorneoService torneoService;
    @Autowired
    IUsuarioParticipaTorneo usuarioParticipaTorneo;
    @Autowired
    ICanchaService canchaService;
    @Autowired
    IReservaService reservaService;
    @Autowired
    IAlquilerService alquilerService;
    @Autowired
    IUsuarioService usuarioService;
    
    @GetMapping("/listado")
    public String listadoNoticias(Model model, @RequestParam(name="page",defaultValue = "0")int page, HttpSession sesion){

        //Recoge el usuario de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        //Muestra todas las noticias en paginas de 10 en 10
        Pageable pageable = PageRequest.of(page,10);
        Page<Noticia> listado = noticiaService.buscarTodos(pageable);
        PageRender<Noticia> pageRender = new PageRender<>("/nnoticias/listado", listado);

        //Se añaden atributos al modelo
        model.addAttribute("titulo","Lista de todas las noticias");
        model.addAttribute("listadoNoticias",listado);
        model.addAttribute("page",pageRender);
        model.addAttribute("usuarioActual",usuario);

        return "noticias/listaNoticias";

    }

    @GetMapping("/detallesNoticia/{id}")
    public String detallesNoticia(Model model, @PathVariable("id")int id,HttpSession session){

        //Se recoge al usuario de la sesion
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        //Se recoge la noticia seleccionada
        Noticia noticia = noticiaService.buscarNoticiaPorId(id);

        //Si es un torneo...
        if (noticia.getTipo().equalsIgnoreCase("torneo")){

            //Se busca y añade el torneo al modelo
            Torneo torneo = torneoService.buscarPorId(id);
            model.addAttribute("torneo", torneo);

            //Se añade a la sesion para la posible participación del usuario
            session.setAttribute("TorneoParticipar",torneo);
        //Si no es un torneo, se guarda en el modelo como null
        }else{
            model.addAttribute("torneo",null);
        }

        //Se añaden atributos al modelo
        model.addAttribute("noticiaActual",noticia);
        model.addAttribute("titulo",noticia.getCabecera());
        model.addAttribute("usuarioActual",usuario);

        return "noticias/detallesNoticia";
    }

    @GetMapping("/pagoTorneo")
    public String pagarTorneo(HttpSession sesion, Model model,Pageable pageable){
        //Recogemos al usuario y torneo de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        Torneo torneo = (Torneo) sesion.getAttribute("TorneoParticipar");

        if (torneo.getFecha().isBefore(LocalDate.now()) || (torneo.getFecha().equals(LocalDate.now()) && torneo.getHora().isBefore(LocalTime.now()))){
            model.addAttribute("msgE","El torneo ya se ha celebrado");
            model.addAttribute("titulo","Lista de todas las noticias");
            Page<Noticia> listado = noticiaService.buscarTodos(pageable);
            PageRender<Noticia> pageRender = new PageRender<>("/nnoticias/listado", listado);
            model.addAttribute("listadoNoticias",listado);
            model.addAttribute("page",pageRender);
            model.addAttribute("usuarioActual",usuario);
            return "noticias/listaNoticias";
        }

        //Buscamos todos los usuarios que participan en ese torneo
        Page<UsuarioParticipaTorneo> usuarioT = usuarioParticipaTorneo.buscarPorTorneo(torneo,pageable);

        //Añadimos atributos al modelo
        model.addAttribute("usuarioActual",usuario);
        model.addAttribute("torneoActual",torneo);
        model.addAttribute("ParticipantesTorneo",usuarioT);
        model.addAttribute("HuecosDisponibles",torneo.getParticipantes()-usuarioT.getContent().size());

        return "torneo/PagoTorneo";

    }

    @GetMapping("/opcionesAdministrador")
    public String opcionesAdministrador(Model model,HttpSession sesion,Pageable pageable){
        //Comprueba si el usuario es un administrador
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        if (usuario==null || usuario.getId()!=-1){
            return "redirect:/uusuario";
        }else{
            //Si es administrador, muestra la pagina de opciones para las noticias

            model.addAttribute("noticias",noticiaService.buscarTodos(pageable));

            return "administracion/noticias";
        }
    }

    @GetMapping("/listadoAdministrador")
    public String listadoAdministrador(Model model,HttpSession sesion, @RequestParam(name="page", defaultValue="0") int
            page) {
        //Comprueba que el usuario es un administrador
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {
            //Si lo es, muestra las noticias en páginas de 5 en 5
            Pageable pageable = PageRequest.of(page, 5);
            Page<Noticia> listado = noticiaService.buscarTodos(pageable);
            PageRender<Noticia> pageRender = new PageRender<>("/nnoticias/listadoAdministrador", listado);

            //Se añaden atributos al modelo

            model.addAttribute("titulo", "Listado de todas las noticias");
            model.addAttribute("listadoNoticias", listado);
            model.addAttribute("page", pageRender);

            return "administracion/listaNoticias";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarNoticia(Model model, HttpSession sesion, @PathVariable("id") Integer id){

        //Comprobar que el usuario es adminsitrador
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {
            //Recoger la noticia de la base de datos
            Noticia noticia = noticiaService.buscarNoticiaPorId(id);

            //Añadir atributos al modelo

            model.addAttribute("titulo", "Editar Noticia");
            model.addAttribute("noticia", noticia);

            return "administracion/formNoticias";
        }
    }

    @PostMapping("/guardar/")
    public String guardarNoticia(Model model, Noticia noticia, RedirectAttributes attributes,HttpSession sesion) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Se establece el tipo de la noticia que tenia previamente
        if (noticia.getId() != null){
            noticia.setTipo(noticiaService.buscarNoticiaPorId(noticia.getId()).getTipo());
        }
        //Se actualiza la fecha y hora de la noticia
        noticia.setFecha(LocalDate.now());
        noticia.setHora(LocalTime.now());

        //Se guarda la noticia
        noticiaService.guardarNoticia(noticia);

        //Si es un torneo...
        if (noticia.getTipo().equalsIgnoreCase("torneo")){
            //...y no existe...
            if (torneoService.buscarPorId(noticia.getId())==null) {
                //... se crea uno nuevo
                Torneo torneo = new Torneo();

                //Se cargan las canchas y la noticia a la que corresponde
                Pageable pageable = PageRequest.of(0, 1000000000);
                List<Cancha> canchas = canchaService.buscarTodos(pageable).getContent();
                Noticia noticiaLast = noticiaService.buscarTodos(pageable).getContent().get(0);

                //Se añaden al modelo
                model.addAttribute("noticiaLast", noticiaLast);
                model.addAttribute("canchas", canchas);
                model.addAttribute("torneo", torneo);
            }else{
                //Si ya existe, se cogen sus datos
                Torneo torneo = torneoService.buscarPorId(noticia.getId());

                //Se cargan las canchas y la noticia correpsondiente
                Pageable pageable = PageRequest.of(0, 1000000000);
                List<Cancha> canchas = canchaService.buscarTodos(pageable).getContent();
                Noticia noticiaLast = noticiaService.buscarTodos(pageable).getContent().get(0);

                //Se añaden atributos al modelo
                model.addAttribute("noticiaLast", noticiaLast);
                model.addAttribute("canchas", canchas);
                model.addAttribute("torneo", torneo);
            }
            model.addAttribute("hoy",LocalDate.now());
            return "administracion/formTorneo";
        }
        //Se añaden atrubutos
        model.addAttribute("titulo", "Nueva Noticia");
        attributes.addFlashAttribute("msg", "Los datos de la noticia fueron guardados!");
        return "redirect:/nnoticias/listadoAdministrador";
    }

    @PostMapping("/guardarTorneo/")
    public String guardarTorneo(Model model, Torneo torneo, RedirectAttributes attributes,HttpSession sesion) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Establece la noticia y cancha seleccionadas
        torneo.setNoticia(noticiaService.buscarNoticiaPorId(torneo.getNoticia().getId()));
        torneo.setCancha(canchaService.buscarCanchaPorId(torneo.getCancha().getId()));

        //Busca si se puede reservar la cancha seleccionada en el dia y hora que se solicita
        Pageable pageable = PageRequest.of(0, 1000000000);
        List<Reserva> existentes = reservaService.buscarReservaPorCanchaYFecha(torneo.getCancha().getId(),torneo.getFecha(),pageable).getContent();
        boolean existeReserva = false;
        for (Reserva existente : existentes) {
            if (existente.getHoraInicio().equals(torneo.getHora())) {
                existeReserva = true;
                break;
            }
        }
        //Si existe una reserva el mismo dia a la misma hora en esa cancha,,,
        if (existeReserva){
            //Se devuelve al usuario al formulario con un mensaje de error
            Noticia noticia = noticiaService.buscarNoticiaPorId(torneo.getNoticia().getId());
            List<Cancha> canchas = canchaService.buscarTodos(pageable).getContent();

            model.addAttribute("noticiaLast", noticia);
            model.addAttribute("canchas", canchas);
            model.addAttribute("torneo", torneo);
            model.addAttribute("msg", "No es posible hacer el torneo en esa hora porque ya existe una reserva!");
            attributes.addFlashAttribute("msg", "No es posible hacer el torneo en esa hora porque ya existe una reserva!");
            return "administracion/formTorneo";
        }
        //Si no existe ninguna reserva, se guarda el torneo, se reserva la pista y se añaden atributos al modelo
        torneoService.guardarTorneo(torneo);
        Reserva reserva = new Reserva(null,torneo.getFecha(),torneo.getHora(),torneo.getHora().plusHours(1),torneo.getCancha(),usuarioService.buscarUsuarioPorId(-1));
        reservaService.guardarReserva(reserva);
        model.addAttribute("titulo", "Nueva Noticia");
        attributes.addFlashAttribute("msg", "Los datos del torneo fueron guardados!");

        return "redirect:/nnoticias/listadoAdministrador";
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

        //Se crea una nueva noticia
        Noticia noticia = new Noticia();

        //Se añaden atributos al modelo
        model.addAttribute("titulo", "Nueva noticia");
        model.addAttribute("noticia", noticia);
        model.addAttribute("hoy",LocalDate.now());

        return "administracion/formNoticias";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarNoticia(@PathVariable("id") Integer id, RedirectAttributes
            attributes,Model model,HttpSession sesion) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Se elimina la noticia especificada
        noticiaService.eliminarNoticia(id);

        //Se añaden atributos al modelo
        attributes.addFlashAttribute("msg", "Los datos de la noticia fueron borrados!");

        return "redirect:/nnoticias/listadoAdministrador";
    }

    @GetMapping("/mirarParticipantes/{id}")
    public String mirarParticipantes(Model model, @PathVariable("id") Integer id, RedirectAttributes
            attributes,HttpSession sesion) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Verificar si el usuario es administrador
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Localiza los datos del torneo seleccionado
        Torneo torneo = torneoService.buscarPorId(id);
        if (torneo == null){
            //Si dicho no existe, se devuelve al usuario a la lista de noticias con un mensaje
            model.addAttribute("msg","Rellena los datos del torneo para poder ver los participantes");
            Pageable pageable = PageRequest.of(0, 5);
            Page<Noticia> listado = noticiaService.buscarTodos(pageable);

            PageRender<Noticia> pageRender = new PageRender<>("/nnoticias/listadoAdministrador", listado);
            model.addAttribute("usuarioActual", usuarioService.buscarUsuarioPorId(-1));
            model.addAttribute("titulo", "Listado de todas las noticias");
            model.addAttribute("listadoNoticias", listado);
            model.addAttribute("page", pageRender);
            return "administracion/listaNoticias";
        }
        //Si el torneo existe, se abre un listado con todos los participantes
        Pageable pageable = PageRequest.of(0, 1000000000);
        List<UsuarioParticipaTorneo> usuarios = usuarioParticipaTorneo.buscarPorTorneo(torneo,pageable).getContent();
        model.addAttribute("participantes",usuarios);
        attributes.addFlashAttribute("msg", "Los datos de la noticia fueron borrados!");
        model.addAttribute("titulo", "Lista de participantes");
        return "administracion/usuariosTorneo";
    }
    
}
