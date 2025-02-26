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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/aalquileres")
public class AlquileresController {

    //Para utilizar los servicios necesarios
    @Autowired
    IAlquilerService alquilerService;
    @Autowired
    IMaterialService materialService;
    @Autowired
    ICanchaService canchaService;
    @Autowired
    IReservaService reservaService;
    @Autowired
    IUsuarioService usuarioService;


    @GetMapping("/confirmarDatosAlquiler")
    public String confirmarDatosAlquiler(Pageable pageable, HttpSession sesion, Model model, @RequestParam("material")int idMaterial, @RequestParam("fecha")LocalDate fecha, @RequestParam("hora")int hora){
        //Recoger usuario que inició sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Buscamos el material que se quiere reservar
        Material material = materialService.buscarMaterialPorId(idMaterial);
        model.addAttribute("materialActual",material);

        //Añadimos la fecha y hora del alquiler
        model.addAttribute("fecha",fecha);
        model.addAttribute("hora",hora);

        //Busqueda de alquileres iguales al solicitado
        List<Alquiler> alquileres = alquilerService.buscarPorFechaHoraMaterial(fecha, LocalTime.of(hora,0,0),idMaterial,pageable).getContent();

        /*Busqueda de canchas del mismo tipo que el deporte que se va a practicar
        Para poder reservar tanto material como canchas con mismo deporte sin reservar existan
        */
        List<Cancha> canchas = canchaService.buscarTodos(pageable).getContent();
        List<Cancha> mismoTipo = new ArrayList<>();
        for (Cancha cancha : canchas) {
            if (cancha.getDeporte().equalsIgnoreCase(material.getDeporte())) {
                mismoTipo.add(cancha);
            }
        }


        List<Reserva> reservas;
        int restar = 0;

        //Cantidad máxima disponible = Max - alquileres similares
        //Por cada alquiler existente y coincidente en fecha, espacio y material, se resta uno a la cantidad maxima para alquilar
        for (int i=0;i<alquileres.size();i++){
            restar +=1;
        }

        //Mirar las pistas de ese mismo deporte que faltan por reservar
        for (Cancha cancha : mismoTipo) {
            //Obtener las reservas para esas pistas
            reservas = reservaService.buscarReservaPorCanchaYFecha(cancha.getId(), fecha, pageable).getContent();
            //Recorro todas las reservas que se dan ese mismo dia para saber si alguna coincide en hora con las del alquiler.
            //Si no hay ninguna -> NO hay reserva -> -1 cantidad maxima
            //Si hay reserva -> No pasa nada
            boolean existeReserva = false;
            for (Reserva reserva : reservas) {
                if (reserva.getHoraInicio().equals(LocalTime.of(hora, 0, 0))) {
                    existeReserva = true;
                    break;
                }
            }
            //Si no existen reservas para esa pista, debo reservar un material para alquilar con esa pista
            if (!existeReserva) {
                restar += 1;
            }
        }

        if (LocalDate.now().isAfter(fecha) || (LocalDate.now().equals(fecha) && LocalTime.now().getHour() >= hora)){
            model.addAttribute("msgE", "Lo sentimos, la hora seleccionada no es valida");
            Page<Material> listado = materialService.buscarTodos(pageable);
            model.addAttribute("listadoMateriales",listado);
            return "materiales/MaterialesAlquiler";
        }
        //Si no hay alquileres, puede alquilar la cantidad total menos las pistas sin reservar
        if (alquileres.isEmpty()){
            model.addAttribute("cantidadMax",material.getCantidad()-restar);
            sesion.setAttribute("cantidadMax",material.getCantidad()-restar);
            return "alquiler/pagoAlquiler";
        }else{
            //Si hay alquileres, puede alquilar la cantidad total menos las pistas sin reservar y los alquileres ya establecidos
            if (material.getCantidad()-restar>0){
                model.addAttribute("cantidadMax",material.getCantidad()-restar);
                sesion.setAttribute("cantidadMax",material.getCantidad()-restar);
                return "alquiler/pagoAlquiler";
            }else{
                //No quedan materiales suficientes para alquilar
                Page<Material> listado = materialService.buscarTodos(pageable);
                PageRender<Material> pageRender = new PageRender<>("/mmaterial/listado", listado);
                model.addAttribute("listadoMateriales",listado);

                model.addAttribute("msgE", "Lo sentimos, no quedan materiales suficientes para alquilar");

                return "materiales/MaterialesAlquiler";
            }
        }
    }


    @GetMapping("/listaMaterial")
    public String listadoMaterial(HttpSession sesion, Model model){
        //Recogemos el usuario Actual
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }
        //Selecciona por defecto la fecha actual
        model.addAttribute("fechaSeleccionada",LocalDate.now());
        model.addAttribute("hoy",LocalDate.now());

        return "alquiler/ListaMateriales";
    }


    @GetMapping("/fechaConfirmada")
    public String fechaConfirmada(HttpSession sesion, Model model,@RequestParam("fecha")LocalDate fecha,@RequestParam("hora")int hora,Pageable pageable){
        //Recogemos al usuario
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Recogemos la lista de materiales seleccionados
        List<Material> materiales = (List<Material>) sesion.getAttribute("carritoMaterial");

        if (materiales.isEmpty()){
            return "redirect:/mmateriales/listado";
        }
        List<Reserva> reservas = new ArrayList<>();
        List<Cancha> canchas = canchaService.buscarTodos(pageable).getContent();
        List<Alquiler> alquileres = new ArrayList<>();

        //Para cada material...
        for (Material material : materiales) {
            //Y cada pista...
            for (Cancha cancha : canchas) {
                //Comprobamos si la cancha es para el mismo deporte que el material...
                if (cancha.getDeporte().equalsIgnoreCase(material.getDeporte())) {
                    //Y si coinciden, buscamos todas las reservas que se hayan hecho ese dia sobre esa cancha para limitar las cantidades que se pueden alquilar
                    reservas.addAll(reservaService.buscarReservaPorCanchaYFecha(cancha.getId(), fecha, pageable).getContent());
                }
            }
            //Añadimos todos los alquileres que coinciden en material, fecha y hora para limiar las cantidades que se pueden alquilar
            alquileres.addAll(alquilerService.buscarPorFechaHoraMaterial(fecha, LocalTime.of(hora, 0, 0), material.getId(), pageable).getContent());
        }

        //Creamos un HashMap para saber cuanta cantidad de cada material se puede alquilar
        HashMap<Integer,Integer> materialesCantidad = new HashMap<>();

        //Añadimos todas las canchas que tengan el mismo deporte que los materiales solicitados
        List<Cancha> mismoTipo;

        for (Material material : materiales) {
            //Reiniciamos las pistas para cada material
            mismoTipo = new ArrayList<>();
            //Si el HashMap no contiene el material, lo añade...
            if (!materialesCantidad.containsKey(material.getId())) {

                //Cantidad máxima disponible = Cantidad en la base de datos - alquileres similares
                int cantidadMax = material.getCantidad();
                //Por cada alquiler existente, se resta en 1 la cantidad maxima alquilable
                for (int j = 0; j < alquilerService.buscarPorFechaHoraMaterial(fecha, LocalTime.of(hora, 0, 0), material.getId(), pageable).getContent().size(); j++) {
                    cantidadMax -= 1;
                }

                //Se añade la cancha al ArrayList si coinciden en el deporte
                for (Cancha cancha : canchas) {
                    if (cancha.getDeporte().equalsIgnoreCase(material.getDeporte())) {
                        mismoTipo.add(cancha);
                    }
                }

                //Para cada pista que coincida en deporte...
                for (Cancha cancha : mismoTipo) {

                    //Se obtienen las reservas para esas pistas...
                    reservas = reservaService.buscarReservaPorCanchaYFecha(cancha.getId(), fecha, pageable).getContent();

                    //Recorro todas las reservas que se dan ese mismo dia para saber si alguna coincide en hora con las del alquiler.
                    //Si no hay ninguna -> NO hay reserva -> -1 cantidad maxima
                    //Si hay reserva -> No pasa nada
                    boolean existeReserva = false;
                    for (Reserva reserva : reservas) {
                        if (reserva.getHoraInicio().equals(LocalTime.of(hora, 0, 0))) {
                            existeReserva = true;
                            break;
                        }
                    }
                    if (!existeReserva) {
                        cantidadMax -= 1;
                    }
                }
                //Por ultimo, se añade el ID del material solicitado con su cantidad Máxima disponible
                materialesCantidad.put(material.getId(), cantidadMax);
            }
        }

        //Creamos una variable para obtener todos los datos de los materiales solicitados
        materiales = new ArrayList<>();
        for (Integer clave : materialesCantidad.keySet()){
            materiales.add(materialService.buscarMaterialPorId(clave));
        }

        //Los añadimos como variables al HTML para usarlos con Thymeleaf
        model.addAttribute("carritoMaterial",materiales);
        model.addAttribute("cantidades",materialesCantidad);
        model.addAttribute("fechaSeleccionada",fecha);
        model.addAttribute("horaSeleccionada",hora);
        model.addAttribute("hoy",LocalDate.now());

        return "alquiler/ListaMateriales";
    }

    @GetMapping("/pagarAlquileres")
    public String pagarAlquileres(HttpSession sesion,Model model,@RequestParam("fecha")LocalDate fecha,@RequestParam("hora")int hora,@RequestParam("cantidades")String[] cantidades,@RequestParam("carritoMaterial") String carritoMaterial){
        //Recogemos el usuario actual
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        List<Integer> materialesId = getIntegers(carritoMaterial);

        //Creamos un array para obtener todos  los datos de los materiales solicitaos y un HashMap para saber las cantidades solicitadas de cada material
        List<Material> materialesLista = new ArrayList<>();
        HashMap<Integer,Integer> materiales = new HashMap<>();

        //Por cada cantidad recogida, se añade el material y la cantidad correspondiente al HashMap y el material correspondiente a la Lista
        for (int i=0;i<cantidades.length;i++){
            materiales.put(materialService.buscarMaterialPorId(materialesId.get(i)).getId(),Integer.parseInt(cantidades[i]));
            materialesLista.add(materialService.buscarMaterialPorId(materialesId.get(i)));
        }

        //Por último, se calcula el precio final sin utilizar descuentos
        double precio = 0;
        for (Material material : materialesLista) {
            precio += material.getPrecio() * materiales.get(material.getId());
        }

        if (precio == 0){
            Pageable pageable = PageRequest.of(0,100);
            Page<Material> listado = materialService.buscarTodos(pageable);
            PageRender<Material> pageRender = new PageRender<>("/mmateriales/listado",listado);
            model.addAttribute("titulo","Lista de todos las articulos");
            model.addAttribute("listadoMateriales",listado);
            model.addAttribute("page",pageRender);
            model.addAttribute("usuarioActual",usuario);
            model.addAttribute("msgE","No se ha introducido ningún elemento para alquilar");
            return "materiales/MaterialesAlquiler";
        }

        if (LocalDate.now().isAfter(fecha) || (LocalDate.now().equals(fecha) && LocalTime.now().getHour() >= hora)){
            model.addAttribute("titulo","Lista de todos las articulos");
            Pageable pageable = PageRequest.of(0,100);
            model.addAttribute("msgE", "Lo sentimos, la hora seleccionada no es valida");
            Page<Material> listado = materialService.buscarTodos(pageable);
            model.addAttribute("listadoMateriales",listado);
            PageRender<Material> pageRender = new PageRender<>("/mmaterial/listado", listado);
            model.addAttribute("page",pageRender);
            return "materiales/MaterialesAlquiler";
        }

        //Se pasa al HTML como variables para usarlas con Thymeleaf
        model.addAttribute("fechaAlquiler",fecha);
        model.addAttribute("fechaSeleccionada",fecha);
        model.addAttribute("horaAlquiler",LocalTime.of(hora,0,0));
        model.addAttribute("materialesHash",materiales);
        model.addAttribute("materialesList",materialesLista);
        model.addAttribute("precioTotal",precio);

        return "alquiler/pagarAlquileres";
    }

    private static List<Integer> getIntegers(String carritoMaterial) {
        List<Integer> materialesId = new ArrayList<>();

        //Dividimos el String devuelto por cada tupla para localizar todos los materiales.
        //[Material{idMaterial=1,nombre...}, Material{idMaterial=2,nombre...}] -->
        // [Material{idMaterial=1, nombre...
        // Material{idMaterial=2,nombre...}]
        String[] materialesCarrito = carritoMaterial.split("},");

        //Localizamos los ID del material
        for (int i=0;i<materialesCarrito.length;i++){
            //Si es el primer elemento, quitamos el '[' inicial
            if (i==0){
                materialesCarrito[i].substring(1);
            }


            //Eliminamos 'Material{idMaterial=' y nos quedamos con lo que falte hasta llegar a la coma.
            String id = materialesCarrito[i].substring(21).split(",")[0];
            //Guardamos los ID en el arrayList
            materialesId.add(Integer.parseInt(id));

        }
        return materialesId;
    }

    @GetMapping("/opcionesAdministrador")
    public String opcionesAdministrador(Model model,HttpSession sesion,Pageable pageable){
        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario==null || usuario.getId()!=-1){
            return "redirect:/uusuario";
            //Si es administrador, muestra las opciones para administradores acerca de los materiales y alquileres
        }else{
            model.addAttribute("alquileres",alquilerService.buscarTodos(pageable));
            return "administracion/alquileres";
        }
    }

    @GetMapping("/listadoAdministrador")
    public String listadoAdministrador(Model model,HttpSession sesion, @RequestParam(name="page", defaultValue="0") int
            page) {
        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {
            //Si lo es, devuelve la lista de todos los alquileres
            Pageable pageable = PageRequest.of(page, 10);
            Page<Alquiler> listado = alquilerService.buscarTodos(pageable);
            PageRender<Alquiler> pageRender = new PageRender<>("/aalquileres/listadoAdministrador", listado);

            model.addAttribute("titulo", "Listado de todas los alquileres");
            model.addAttribute("listadoAlquileres", listado);
            model.addAttribute("page", pageRender);
            return "administracion/listaAlquileres";
        }
    }

    @GetMapping("/buscar")
    public String buscar(Model model,HttpSession sesion) {
        //Comprobamos si el usuario es administrador
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario==null || usuario.getId()!=-1) {
            return "redirect:/uusuario";
        }
        //Si lo es, permite la busqueda de alquileres por usuario, materiales o fechas
        Pageable pageable = PageRequest.of(0, 10000000);
        //Se presentan todos los usuarios
        List<Usuario> usuarios = usuarioService.buscarTodos(pageable).getContent();
        model.addAttribute("usuarios",usuarios);
        //Se presentan todos los materiales
        List<Material> materiales = materialService.buscarTodos(pageable).getContent();
        model.addAttribute("materiales",materiales);

        return "administracion/busquedaAlquiler";
    }

    @GetMapping("/usuario")
    public String buscarAlquilerPorUsuario(Model model, @RequestParam(name="page", defaultValue = "0")int page, @RequestParam("usuario")Integer id, HttpSession sesion) {
        //Se comprueba si el usuario es Administrador
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario==null || usuario.getId()!=-1) {
            return "redirect:/uusuario";
        }
        //Si lo es, busca los alquileres que corresponden con el usuario especificado
        Pageable pageable = PageRequest.of(page,5);
        Usuario usuarioA=usuarioService.buscarUsuarioPorId(id);
        Page<Alquiler> listado = alquilerService.buscarPorResponsable(usuarioA,pageable);
        PageRender<Alquiler> pageRender = new PageRender<>("usuario?usuario=" + id, listado);
        model.addAttribute("titulo", "Listado de reservas por usuario");
        model.addAttribute("listadoAlquileres", listado);
        model.addAttribute("page", pageRender);
        return "administracion/listaAlquileres";

    }

    @GetMapping("/fecha")
    public String buscarAlquilerPorFecha(Model model, @RequestParam(name="page", defaultValue = "0")int page, @RequestParam("fecha")LocalDate fecha, HttpSession sesion) {
        //Se comprueba si el usuario es Administrador
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario==null || usuario.getId()!=-1) {
            return "redirect:/uusuario";
        }
        //Si lo es, busca los alquileres que corresponden con la fecha especificada
        Pageable pageable = PageRequest.of(page,5);
        Page<Alquiler> listado =alquilerService.buscarPorFecha(fecha,pageable);
        PageRender<Alquiler> pageRender = new PageRender<>("fecha?fecha=" + fecha, listado);
        model.addAttribute("titulo", "Listado de reservas por fecha");
        model.addAttribute("listadoAlquileres", listado);
        model.addAttribute("page", pageRender);
        return "administracion/listaAlquileres";

    }

    @GetMapping("/material")
    public String buscarAlquilerPorMaterial(Model model, @RequestParam(name="page", defaultValue = "0")int page, @RequestParam("material")Integer id, HttpSession sesion) {
        //Se comprueba si el usuario es Administrador
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario==null || usuario.getId()!=-1) {
            return "redirect:/uusuario";
        }
        //Si lo es, busca los alquileres que corresponden con el material especificado
        Pageable pageable = PageRequest.of(page,5);
        Page<Alquiler> listado = alquilerService.buscarPorMaterial(id,pageable);
        PageRender<Alquiler> pageRender = new PageRender<>("material?material=" + id, listado);
        model.addAttribute("titulo", "Listado de reservas por material");
        model.addAttribute("listadoAlquileres", listado);
        model.addAttribute("page", pageRender);
        return "administracion/listaAlquileres";

    }


}
