package TFG.Front.tfgfront.controller;

import TFG.Front.tfgfront.model.Cancha;
import TFG.Front.tfgfront.model.Material;
import TFG.Front.tfgfront.model.Reserva;
import TFG.Front.tfgfront.model.Usuario;
import TFG.Front.tfgfront.paginator.PageRender;
import TFG.Front.tfgfront.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rreservas")
public class ReservaController {

    @Autowired
    IReservaService reservaService;
    @Autowired
    ICanchaService canchaService;
    @Autowired
    IMaterialService materialService;
    @Autowired
    IAlquilerService alquilerService;
    @Autowired
    IUsuarioService usuarioService;

    @GetMapping("/principal")
    public String paginaPrincipal(Pageable pageable,Model model, HttpSession sesion){

        //Recoge el usuario y la cancha de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        Cancha cancha = (Cancha) sesion.getAttribute("canchaActual");

        //Fecha por defecto
        LocalDate fechaSeleccionada = LocalDate.now();
        LocalDate hoy = LocalDate.now();

        //Lista de todas las canchas para elegir
        Page<Cancha> canchas = canchaService.buscarTodos(pageable);

        //Lista de reservas que coinciden en cancha y fecha
        Page<Reserva> reservas = reservaService.buscarReservaPorCanchaYFecha(cancha.getId(),fechaSeleccionada,pageable);

        //Cancha seleccionada
        Cancha canchaActual = canchaService.buscarCanchaPorId(cancha.getId());

        //Busqueda de todos los materiales
        Page<Material> materiales = materialService.buscarTodos(pageable);

        //Indica al HTML que no debe mostrar las horas directamente, si no que se debe rellenar primero la cnacha y la fecha deseadas
        boolean inicio = true;

        //Lista de horas que tienen una reserva
        List<String> horas = new ArrayList<>();

        //Se añaden atributos a la sesion y al modelo
        sesion.setAttribute("canchaReserva",canchaActual);
        sesion.setAttribute("fechaReserva",fechaSeleccionada);
        model.addAttribute("usuarioActual",usuario);
        model.addAttribute("listadoCanchas",canchas);
        model.addAttribute("reservas",reservas);
        model.addAttribute("horas",horas);
        model.addAttribute("canchaActual",canchaActual);
        model.addAttribute("fechaSeleccionada",fechaSeleccionada);
        model.addAttribute("materiales",materiales);
        model.addAttribute("inicio",inicio);
        model.addAttribute("hoy",hoy);

        return "reservas/ReservaCancha";
    }
    @GetMapping("/buscarReservasDisponibles")
    public String buscarReservasPorCanchaYFecha(Pageable pageable, Model model, HttpSession sesion, @RequestParam("cancha")int idCancha, @RequestParam("fecha")String fecha){
        //Se recoge el usuario de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        if (usuario==null){
            return "redirect:/uusuario/";
        }

        //Se guarda la cancha y fecha seleccionadas en la sesion
        sesion.setAttribute("canchaReserva",canchaService.buscarCanchaPorId(idCancha));
        sesion.setAttribute("fechaReserva",LocalDate.parse(fecha));

        //Lista de todas las canchas
        Page<Cancha> canchas = canchaService.buscarTodos(pageable);

        //Lista de reservas que coinciden en cancha y fecha
        Page<Reserva> reservas = reservaService.buscarReservaPorCanchaYFecha(idCancha, LocalDate.parse(fecha),pageable);

        //Lista de horas que ya tienen reserva
        List<String> horas = new ArrayList<>();
        for (int i=0; i<reservas.getContent().size();i++){
            if (reservas.getContent().get(i).getHoraInicio().toString().charAt(0) == '0'){
                horas.add(reservas.getContent().get(i).getHoraInicio().toString().substring(1));
            }else {
                horas.add(reservas.getContent().get(i).getHoraInicio().toString());
            }
        }
        //Materiales
        Page<Material> materiales = materialService.buscarTodos(pageable);

        LocalDate hoy = LocalDate.now();

        //Se añaden atributos al modelo
        model.addAttribute("usuarioActual",usuario);
        model.addAttribute("listadoCanchas",canchas);
        model.addAttribute("reservas",reservas);
        model.addAttribute("horas",horas);
        model.addAttribute("canchaActual",canchaService.buscarCanchaPorId(idCancha));
        model.addAttribute("fechaSeleccionada",LocalDate.parse(fecha));
        model.addAttribute("materiales",materiales.getContent());
        model.addAttribute("hoy",hoy);

        return "reservas/ReservaCancha";
    }
    @GetMapping("confirmarReserva")
    public String confirmarReserva(Pageable pageable,Model model,HttpSession sesion,@RequestParam("hora")String hora,@RequestParam(value = "material", required = false) List<String> materialesSeleccionados)
    {
        //Se recoge el usuario, la cancha y la fecha seleccionadas de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        Cancha cancha = (Cancha) sesion.getAttribute("canchaReserva");
        LocalDate fechaReserva = (LocalDate) sesion.getAttribute("fechaReserva");
        if (LocalDate.now().isAfter(fechaReserva) || (LocalDate.now().equals(fechaReserva) && LocalTime.now().getHour() >= Integer.parseInt(hora.substring(1)))){
            return "redirect:/ccanchas/listado";
        }
        List<Reserva> reservas = reservaService.buscarReservaPorCanchaYFecha(cancha.getId(),fechaReserva,pageable).getContent();
        for (Reserva reserva: reservas){
            if (reserva.getHoraInicio().getHour() == Integer.parseInt(hora.substring(1))){
                return "redirect:/ccanchas/listado";
            }
        }


        //Se crea una lista de materiales solicitados
        List<Material> listaMateriales = new ArrayList<>();
        if (materialesSeleccionados != null) {
            for (String materialesSeleccionado : materialesSeleccionados) {
                listaMateriales.add(materialService.buscarMaterialPorId(Integer.parseInt(materialesSeleccionado)));
            }
        }
        //Se calcula el precio de la reserva
        double precio = cancha.getPrecio();
        for (Material listaMateriale : listaMateriales) {
            precio += listaMateriale.getPrecio();
        }
        //Se establece la hora de finalizacion de la reserva (Duracion: 1h)
        int horaFin = Integer.parseInt(hora.substring(1))+1;

        //Se guardan los atributos en la sesion
        sesion.setAttribute("horaInicioReserva", LocalTime.of(Integer.parseInt(hora.substring(1)),0,0));
        sesion.setAttribute("horaFinReserva",LocalTime.of(horaFin,0,0));
        sesion.setAttribute("precioReserva",precio);
        sesion.setAttribute("materialesReserva",listaMateriales);

        //Se guardan atributos en el modelo
        model.addAttribute("usuarioActual",usuario);
        model.addAttribute("canchaReserva",cancha);
        model.addAttribute("fechaReserva",fechaReserva);
        model.addAttribute("horaReserva",hora.substring(1));
        model.addAttribute("materialesReserva",listaMateriales);
        model.addAttribute("precioReserva",precio);

        return "reservas/PagarReserva";

    }

    @GetMapping("/opcionesAdministrador")
    public String opcionesAdministrador(Model model,HttpSession sesion,Pageable pageable){
        //Se comprueba que el usuario es un administrador
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        if (usuario==null || usuario.getId()!=-1){
            return "redirect:/uusuario";
        }else{
            //Abre el menu de administrador para ver las reservas

            model.addAttribute("reservas",reservaService.buscarTodas(pageable));
            return "administracion/reservas";
        }
    }

    @GetMapping("/listadoAdministrador")
    public String listadoAdministrador(Model model,HttpSession sesion, @RequestParam(name="page", defaultValue="0") int
            page) {
        //Se comprueba que el usuario es un administrador
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {
            //Muestra las reservas en paginas de 5 en 5
            Pageable pageable = PageRequest.of(page, 5);
            Page<Reserva> listado = reservaService.buscarTodas(pageable);
            PageRender<Reserva> pageRender = new PageRender<>("/rreservas/listadoAdministrador", listado);

            //Se añaden atributos al modelo.
            model.addAttribute("titulo", "Listado de todas las reservas");
            model.addAttribute("listadoReservas", listado);
            model.addAttribute("page", pageRender);
            return "administracion/listaReservas";
        }
    }

    @GetMapping("/buscar")
    public String buscar(Model model,HttpSession sesion) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Busca todos los usuarios y canchas existentes para mostrarlas
        Pageable pageable = PageRequest.of(0, 10000000);
        List<Usuario> usuarios = usuarioService.buscarTodos(pageable).getContent();
        List<Cancha> canchas = canchaService.buscarTodos(pageable).getContent();

        //Se añaden atributos al modelo
        model.addAttribute("usuarios",usuarios);
        model.addAttribute("canchas",canchas);
        return "administracion/busquedaReserva";
    }

    @GetMapping("/usuario")
    public String buscarReservaPorUsuario(Model model, @RequestParam(name="page", defaultValue = "0")int page, @RequestParam("usuario")Integer id,HttpSession sesion) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Busca las reservas por el usuario especificado y las muestra de 5 en 5
        Pageable pageable = PageRequest.of(page,5);
        Page<Reserva> listado = reservaService.buscarReservaPorUsuario(id,pageable);
        PageRender<Reserva> pageRender = new PageRender<>("usuario?usuario=" + id, listado);

        //Añade atributos al modelo
        model.addAttribute("titulo", "Listado de reservas por usuario");
        model.addAttribute("listadoReservas", listado);
        model.addAttribute("page", pageRender);
        return "administracion/listaReservas";

    }

    @GetMapping("/fecha")
    public String buscarReservaPorFecha(Model model, @RequestParam(name="page", defaultValue = "0")int page, @RequestParam("fecha")LocalDate fecha,HttpSession sesion) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Busca las reservas por la fecha especificado y las muestra de 5 en 5
        Pageable pageable = PageRequest.of(page,5);
        Page<Reserva> listado = reservaService.buscarPorFecha(fecha,pageable);
        PageRender<Reserva> pageRender = new PageRender<>("fecha?fecha=" + fecha, listado);

        //Añade atributos al modelo
        model.addAttribute("titulo", "Listado de reservas por fecha");
        model.addAttribute("listadoReservas", listado);
        model.addAttribute("page", pageRender);
        return "administracion/listaReservas";

    }

    @GetMapping("/pista")
    public String buscarReservaPorCancha(Model model, @RequestParam(name="page", defaultValue = "0")int page, @RequestParam("cancha")Integer id,HttpSession sesion) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }
        //Busca las reservas por la cancha especificado y las muestra de 5 en 5
        Pageable pageable = PageRequest.of(page,5);
        Page<Reserva> listado = reservaService.buscarReservaPorCancha(id,pageable);
        PageRender<Reserva> pageRender = new PageRender<>("cancha?cancha=" + id, listado);

        //Añade atributos al modelo
        model.addAttribute("titulo", "Listado de reservas por pista");
        model.addAttribute("listadoReservas", listado);
        model.addAttribute("page", pageRender);
        return "administracion/listaReservas";

    }



}
