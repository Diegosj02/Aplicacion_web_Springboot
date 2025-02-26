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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("ttarjetas")
public class TarjetaController {

    @Autowired
    ICanchaService canchaService;
    @Autowired
    ITarjetaSservice tarjetaService;
    @Autowired
    IReservaService reservaService;
    @Autowired
    INoticiaService noticiaService;
    @Autowired
    ITorneoService torneoService;
    @Autowired
    IUsuarioParticipaTorneo participaTorneo;
    @Autowired
    IUsuarioService usuarioService;
    @Autowired
    IDescuentoService descuentoService;
    @Autowired
    IMaterialService materialService;
    @Autowired
    IAlquilerService alquilerService;
    @Autowired
    IArticuloService articuloService;
    @Autowired
    IRopaService ropaService;
    @Autowired
    IDeportivoService deportivoService;
    @Autowired
    IUsuarioParticipaTorneo iUsuarioParticipaTorneo;



    @GetMapping("/pagoReserva")
    public String pagoReserva(Model model, HttpSession sesion, @RequestParam("tarjeta")Integer idTarjeta, Reserva reserva, RedirectAttributes attributes){
        //Recoge el usuario de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Busca la tarjeta introducida
        Tarjeta tarjeta = tarjetaService.buscarPorId(idTarjeta);
        //Si no existe, se devuelve un mensaje de error
        if (tarjeta == null){
            model.addAttribute("msg", "Tarjeta no encontrada");
            model.addAttribute("usuarioActual",usuario);
            model.addAttribute("canchaReserva", sesion.getAttribute("canchaReserva"));
            model.addAttribute("fechaReserva",sesion.getAttribute("fechaReserva"));
            model.addAttribute("horaReserva",sesion.getAttribute("horaInicioReserva"));
            model.addAttribute("materialesReserva",sesion.getAttribute("materialesReserva"));
            model.addAttribute("precioReserva",sesion.getAttribute("precioReserva"));
            return "reservas/PagarReserva"; //Misma pagina
        }else{
            if (tarjeta.getSaldo()<(Double)sesion.getAttribute("precioReserva")){
                //Si existe pero no tiene saldo suficiente, se envia un mensaje de error
                model.addAttribute("usuarioActual",usuario);
                model.addAttribute("canchaReserva", sesion.getAttribute("canchaReserva"));
                model.addAttribute("fechaReserva",sesion.getAttribute("fechaReserva"));
                model.addAttribute("horaReserva",sesion.getAttribute("horaInicioReserva"));
                model.addAttribute("materialesReserva",sesion.getAttribute("materialesReserva"));
                model.addAttribute("precioReserva",sesion.getAttribute("precioReserva"));
                model.addAttribute("msg", "Tarjeta sin saldo suficiente");
                return "reservas/PagarReserva"; //Misma pagina
            }else{
                //Si existe y tiene saldo suficiente, se resta el saldo a la tarjeta
                tarjetaService.guardarTarjeta(new Tarjeta(tarjeta.getId(),tarjeta.getSaldo()-(Double)sesion.getAttribute("precioReserva")));
            }
        }
        //Se establecen los atributos de la reserva
        reserva.setCancha((Cancha) sesion.getAttribute("canchaReserva"));
        reserva.setFecha((LocalDate) sesion.getAttribute("fechaReserva"));
        reserva.setHoraFin((LocalTime) sesion.getAttribute("horaFinReserva"));
        reserva.setResponsable((Usuario) sesion.getAttribute("usuario"));
        reserva.setHoraInicio((LocalTime) sesion.getAttribute("horaInicioReserva"));

        //Se guarda la reserva
        reservaService.guardarReserva(reserva);

        //Se otorgan los puntos correspondientes al usuario
        usuario.setPuntos(usuario.getPuntos()+(((Double) sesion.getAttribute("precioReserva")).intValue()));
        usuario.setPuntosT(usuario.getPuntosT()+ usuario.getPuntos()+(((Double) sesion.getAttribute("precioReserva")).intValue()));

        //Se actualizan los datos del usuario
        usuarioService.guardarUsuario(usuario);

        //Para cada material de la reserva, se realiza un alquiler
        List<Material> materiales = (List<Material>) sesion.getAttribute("materialesReserva");
        for (Material materiale : materiales) {
            alquilerService.guardarAlquiler(new Alquiler(0, reserva.getFecha(), reserva.getHoraInicio(), materiale, usuario));
        }

        model.addAttribute("msg", "Reserva realizada con éxito");
        Pageable pageable = PageRequest.of(0,100);
        Page<Cancha> listado = canchaService.buscarTodos(pageable);
        PageRender<Cancha> pageRender = new PageRender<>("/ccanchas/listado",listado);
        model.addAttribute("titulo","Lista de todas las canchas");
        model.addAttribute("listadoCanchas",listado);
        model.addAttribute("page",pageRender);
        return "canchas/instalaciones";
    }

    @PostMapping("/guardarUsuarioTorneo")
    public String registrarUsuario(HttpSession sesion, Model model, RedirectAttributes attributes, UsuarioParticipaTorneo usuarioParticipaTorneo, @RequestParam("tarjeta")int idTarjeta){

        //Se recoge el usuario de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        if (usuario == null){
            return "redirect:/uusuario/";
        }

        Torneo torneo = (Torneo) sesion.getAttribute("TorneoParticipar");

        //Se crea un ID para el usuario y el torneo
        UsuarioParticipaTorneoId id= new UsuarioParticipaTorneoId();
        id.setIdtorneo(torneo.getId());
        id.setIdusuario(usuario.getId());
        usuarioParticipaTorneo.setUsuarioParticipaTorneoId(id);
        usuarioParticipaTorneo.setIdusuario(usuario);
        usuarioParticipaTorneo.setIdtorneo(torneo);

        //Se busca la tarjeta
        Tarjeta tarjeta = tarjetaService.buscarPorId(idTarjeta);
        //Si no se encuentra, devuelve un mensaje
        if (tarjeta == null){
            model.addAttribute("msg", "Tarjeta no encontrada");
            model.addAttribute("usuarioActual",usuario);
            model.addAttribute("torneoActual",torneo);
            Pageable pageable = PageRequest.of(0,100);
            model.addAttribute("ParticipantesTorneo",iUsuarioParticipaTorneo.buscarPorTorneo(torneo,pageable).getContent());
            model.addAttribute("HuecosDisponibles",torneo.getParticipantes()-iUsuarioParticipaTorneo.buscarPorTorneo(torneo,pageable).getContent().size());
            return "torneo/PagoTorneo";
        }else{
            //Si tiene saldo suficiente
            if (tarjeta.getSaldo() >= torneo.getCoste()){
                //Se comprueba si ya participa en el torneo
                String veredicto = participaTorneo.registrarUsuarioEnTorneo(usuarioParticipaTorneo);
                if (veredicto.equals("false")){
                    //Si ya participaba, se le comunica
                    model.addAttribute("msg", "No te has podido inscribir al torneo porque ya participas en él");
                }else{
                    //Si no, se le comunica y se actualizan sus puntos
                    model.addAttribute("msg", "Te has apuntado correctamente");
                    usuario.setPuntos( usuario.getPuntos() + (torneo.getCoste().intValue()));
                    usuario.setPuntosT(usuario.getPuntosT()+ usuario.getPuntos() + (torneo.getCoste().intValue()));
                    usuarioService.guardarUsuario(usuario);
                }
            }else{
                //Si no tiene saldo suficiente, devuelve un mensaje
                model.addAttribute("msg", "Tarjeta sin saldo suficiente");
                model.addAttribute("usuarioActual",usuario);
                model.addAttribute("torneoActual",torneo);
                Pageable pageable = PageRequest.of(0,100);
                model.addAttribute("ParticipantesTorneo",iUsuarioParticipaTorneo.buscarPorTorneo(torneo,pageable).getContent());
                model.addAttribute("HuecosDisponibles",torneo.getParticipantes()-iUsuarioParticipaTorneo.buscarPorTorneo(torneo,pageable).getContent().size());
                return "torneo/PagoTorneo";
            }
        }

        Pageable pageable = PageRequest.of(0,5);
        Page<Noticia> listado = noticiaService.buscarTodos(pageable);
        PageRender<Noticia> pageRender = new PageRender<>("/nnoticias/listado", listado);

        //Se añaden atributos al modelo
        model.addAttribute("titulo","Lista de todas las noticias");
        model.addAttribute("listadoNoticias",listado);
        model.addAttribute("page",pageRender);
        model.addAttribute("usuarioActual",usuario);

        return "noticias/listaNoticias";
    }

    @PostMapping("/alquilarMaterial")
    public String alquilerMaterial(HttpSession sesion, Model model,@RequestParam("cantidad") int cantidad, @RequestParam("tarjeta")int idTarjeta,@RequestParam("material")int idMaterial,@RequestParam("fecha")LocalDate fecha,@RequestParam("hora")int hora,RedirectAttributes attributes){
        //Se recoge al usuario de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Se buscan los datos del material
        Material material = materialService.buscarMaterialPorId(idMaterial);

        //Se busca la tarjeta
        Tarjeta tarjeta = tarjetaService.buscarPorId(idTarjeta);
        //Si no se encuentra, salta mensaje de error
        if (tarjeta==null){
            model.addAttribute("msg", "No hemos encontrado esa tarjeta");
            model.addAttribute("materialActual",material);
            model.addAttribute("fecha",fecha);
            model.addAttribute("hora",hora);
            model.addAttribute("cantidadMax",sesion.getAttribute("cantidadMax"));
            return "alquiler/pagoAlquiler";
        }else{
            if (tarjeta.getSaldo()>=material.getPrecio()*cantidad){
                //Se resta el dinero a la tarjeta y se otorgan puntos al usuario
                tarjetaService.guardarTarjeta(new Tarjeta(tarjeta.getId(),tarjeta.getSaldo()-material.getPrecio()*cantidad));
                usuario.setPuntos(usuario.getPuntos()+(int) (material.getPrecio()*cantidad));
                usuario.setPuntosT(usuario.getPuntosT()+ usuario.getPuntos()+(int) (material.getPrecio()*cantidad));
                usuarioService.guardarUsuario(usuario);

                //Se realiza un alquiler tantas veces como cantidad del material se haya solicitado
                for (int i=0;i<cantidad;i++){
                    alquilerService.guardarAlquiler(new Alquiler(0,fecha,LocalTime.of(hora,0,0),material,usuario));
                }

                Pageable pageable = PageRequest.of(0,100);
                Page<Material> listado = materialService.buscarTodos(pageable);
                PageRender<Material> pageRender = new PageRender<>("/mmaterial/listado", listado);


                model.addAttribute("msg", "Alquiler realizado con éxito");
                model.addAttribute("titulo","Lista de todos las articulos");
                model.addAttribute("listadoMateriales",listado);
                model.addAttribute("page",pageRender);
                model.addAttribute("usuarioActual",usuario);

                return "materiales/MaterialesAlquiler";
            }else{
                model.addAttribute("msg", "Lo sentimos, su tarjeta no cuenta con el saldo suficiente");
                model.addAttribute("materialActual",material);
                model.addAttribute("fecha",fecha);
                model.addAttribute("hora",hora);
                model.addAttribute("cantidadMax",sesion.getAttribute("cantidadMax"));
                return "alquiler/pagoAlquiler";
            }
        }

    }


    @PostMapping("/alquilarMateriales")
    public String alquilerMateriales(HttpSession sesion, Model model,@RequestParam("materiales")String materiales, @RequestParam("tarjeta")int idTarjeta,@RequestParam("cantidades")String cantidades,@RequestParam("fecha")LocalDate fecha,@RequestParam("hora")String hora, @RequestParam("precioTotal")double precioTotal, RedirectAttributes attributes){

        //Se recoge al usuario de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Lista para recoger los IDs de los materiales solicitados
        List<Integer> materialesId = new ArrayList<>();

        //Array de tipo String para conocer los materiales solicitados y dividirlos para acceder más facilmente a ellos
        String[] materialesCarrito = materiales.split("},");

        //Array de tipo String para conocer las cantidades solicitadas
        String[] cantidadesCarrito = cantidades.replace("[","").replace("]","").split(",");

        //ArrayList con las cantidades en Integer
        List<Integer> cantidadesLista = new ArrayList<>();
        for (String s : cantidadesCarrito) {
            cantidadesLista.add(Integer.parseInt(s.replace(" ", "")));
        }

        for (int i=0;i<materialesCarrito.length;i++){
            if (i==0){
                materialesCarrito[i].substring(1);
            }
            String id = materialesCarrito[i].substring(21).split(",")[0];
            materialesId.add(Integer.parseInt(id));
        }

        //Se busca la tarjeta introducida
        Tarjeta tarjeta= tarjetaService.buscarPorId(idTarjeta);

        if (tarjeta == null){
            model.addAttribute("msg", "Lo sentimos, no hemos encontrado su tarjeta");
            model.addAttribute("fechaAlquiler",fecha);
            model.addAttribute("fechaSeleccionada",fecha);
            model.addAttribute("horaAlquiler",LocalTime.of(Integer.parseInt(hora.substring(0, 2)),0,0));
            List<Material> materialesLista = new ArrayList<>();
            HashMap<Integer,Integer> materialesHash = new HashMap<>();

            //Por cada cantidad recogida, se añade el material y la cantidad correspondiente al HashMap y el material correspondiente a la Lista
            for (int i=0;i<materialesId.size();i++){
                materialesHash.put(materialService.buscarMaterialPorId(materialesId.get(i)).getId(),cantidadesLista.get(i));
                materialesLista.add(materialService.buscarMaterialPorId(materialesId.get(i)));
            }
            model.addAttribute("materialesHash",materialesHash);

            model.addAttribute("materialesList",materialesLista);
            model.addAttribute("precioTotal",precioTotal);
            return "alquiler/pagarAlquileres"; //Misma pagina
        }else{
            if (tarjeta.getSaldo()<precioTotal){
                model.addAttribute("msg", "Lo sentimos, su tarjeta no cuenta con el saldo suficiente");
                model.addAttribute("fechaAlquiler",fecha);
                model.addAttribute("fechaSeleccionada",fecha);
                model.addAttribute("horaAlquiler",LocalTime.of(Integer.parseInt(hora.substring(0, 2)),0,0));
                List<Material> materialesLista = new ArrayList<>();
                HashMap<Integer,Integer> materialesHash = new HashMap<>();

                //Por cada cantidad recogida, se añade el material y la cantidad correspondiente al HashMap y el material correspondiente a la Lista
                for (int i=0;i<materialesId.size();i++){
                    materialesHash.put(materialService.buscarMaterialPorId(materialesId.get(i)).getId(),cantidadesLista.get(i));
                    materialesLista.add(materialService.buscarMaterialPorId(materialesId.get(i)));
                }
                model.addAttribute("materialesHash",materialesHash);

                model.addAttribute("materialesList",materialesLista);
                model.addAttribute("precioTotal",precioTotal);
                return "alquiler/pagarAlquileres";//misma pagina
            }else{
                Alquiler alquiler;
                //Para cada material, se crea un alquiler tantas veces como cantidad de ese material se haya solicitado
                for (int i=0;i<materialesId.size();i++){
                    for (int j=0;j<cantidadesLista.get(i);j++) {
                        alquiler = new Alquiler(0, fecha, LocalTime.of(Integer.parseInt(hora.substring(0, 2)), 0, 0), materialService.buscarMaterialPorId(materialesId.get(i)), usuario);
                        alquilerService.guardarAlquiler(alquiler);
                    }
                }
                //Se resta el saldo de la tarjeta y se otorgan puntos al usuario
                tarjeta.setSaldo(tarjeta.getSaldo()-precioTotal);
                usuario.setPuntos(usuario.getPuntos()+(int) (precioTotal));
                usuario.setPuntosT(usuario.getPuntosT()+ usuario.getPuntos()+(int) (precioTotal));
                usuarioService.guardarUsuario(usuario);
                tarjetaService.guardarTarjeta(tarjeta);

                Pageable pageable = PageRequest.of(0,100);
                Page<Material> listado = materialService.buscarTodos(pageable);
                PageRender<Material> pageRender = new PageRender<>("/mmaterial/listado", listado);


                sesion.setAttribute("carritoMaterial",new ArrayList<Material>());
                model.addAttribute("msg", "Alquiler realizado con éxito");
                model.addAttribute("titulo","Lista de todos las articulos");
                model.addAttribute("listadoMateriales",listado);
                model.addAttribute("page",pageRender);
                model.addAttribute("usuarioActual",usuario);

                return "materiales/MaterialesAlquiler";
            }
        }

    }

    @PostMapping("/comprarArticulo")
    public String comprarArticulo(Pageable pageable,HttpSession sesion, Model model,@RequestParam(value = "cantidadDeportivo", required = false) String cantidadDeportivo,@RequestParam("precioTotal")double precioTotal,@RequestParam("tallas")List<String> tallas,@RequestParam("cantidades")String cantidades, @RequestParam("tarjeta")int idTarjeta,@RequestParam("articulo")int idArticulo,@RequestParam(value = "descuento", defaultValue = "0")int idDescuento, RedirectAttributes attributes){

        //Se recoge el usuario de la sesion
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Se busca el articulo especificado
        Articulo articulo = articuloService.buscarArticuloPorId(idArticulo);
        //Se busca el descuento en caso de haber incorporado alguno
        Descuento descuento = null;
        if (idDescuento != 0){
            descuento = descuentoService.buscarPorId(idDescuento);
        }

        Tarjeta tarjeta = tarjetaService.buscarPorId(idTarjeta);
        if (tarjeta==null){
            model.addAttribute("msg", "Lo sentimos, no hemos encontrado su tarjeta");
            return "redirect:/aarticulos/formularioCompra?idArticulo="+articulo.getId();
        }else{
            if (tarjeta.getSaldo()>=precioTotal){
                if (descuento!= null && descuento.getUsuario().getId() == usuario.getId()){
                    //Si la tarjeta tiene saldo suficiente y se ha introducido un descuento valido, se aplica el descuento
                    if (descuento.getTipo().equalsIgnoreCase("valor")){
                        precioTotal = precioTotal - descuento.getValor();
                    }else{
                        precioTotal = precioTotal - precioTotal*((double) descuento.getValor());
                    }
                    //Se elimina el descuento
                    descuentoService.eliminarDescuento(descuento.getId());
                }
                //Se resta dinero a la tarjeta y se otorgan puntos al usuario
                tarjetaService.guardarTarjeta(new Tarjeta(tarjeta.getId(),tarjeta.getSaldo()-precioTotal));
                usuario.setPuntos(usuario.getPuntos()+(int) (precioTotal));
                usuario.setPuntosT(usuario.getPuntosT()+ usuario.getPuntos()+(int) (precioTotal));
                usuarioService.guardarUsuario(usuario);
                //Si el articulo es una ropa
                if (articulo.getTipo().equalsIgnoreCase("ropa")){
                    //Se mejora la claridad del String[] y se almancenan las cantidades en formato Integer en cantidadesLista
                    cantidades = cantidades.replace("[","").replace("]","").replace(" ","");
                    String[] partes = cantidades.split(",");
                    List<Integer> cantidadesLista = new ArrayList<>();
                    for (String parte : partes){
                        cantidadesLista.add(Integer.parseInt(parte));
                    }
                    //Se buscan todas las tallas de ropa del articulo especificado
                    List<Ropa> ropas = ropaService.buscarPorId(pageable,articulo).getContent();
                    //Para cada talla, se eliminan de la cantidad máxima lo solicitado
                    for (int i=0;i<tallas.size();i++){
                        RopaId ropaId = ropas.get(i).getIdPropio();
                        ropaService.guardarRopa(new Ropa(ropaId,ropas.get(i).getIdRopa(),ropas.get(i).getCantidad()-cantidadesLista.get(i)));
                    }
                }else{
                    //Si es un deportivo, se busca y actualiza su cantidad
                    Deportivo deportivo = deportivoService.buscarDeportivoPorId(articulo.getId());
                    deportivoService.guardarDeportivo(new Deportivo(deportivo.getId(),deportivo.getDeporte(),deportivo.getCantidad()-Integer.parseInt(cantidadDeportivo),articulo));
                }

                Page<Articulo> listado = articuloService.buscarTodos(pageable);

                PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listado",listado);

                //Se pasan como variables al HTML para utilizarlas como Thymeleaf
                model.addAttribute("msg", "Compra realizada con exito");
                model.addAttribute("titulo","Lista de todos las articulos");
                model.addAttribute("listadoArticulos",listado);
                model.addAttribute("page",pageRender);
                model.addAttribute("usuarioActual",usuario);

                return "articulos/tienda";
            }else{
                model.addAttribute("msg", "Lo sentimos, no dispone de saldo suficiente");
                return "redirect:/aarticulos/formularioCompra?idArticulo="+articulo.getId();
            }
        }

    }

    @PostMapping("/comprarCarrito")
    public String comprarCarrito(Pageable pageable, Model model, HttpSession sesion, @RequestParam("articulos")String articulosCantidades,
                                 @RequestParam("cantidades")String cantidades, @RequestParam("precioTotal")double precioTotal,
                                 @RequestParam("tarjeta")int idTarjeta, @RequestParam Map<String,String> descuentos, RedirectAttributes attributes){

        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Obtener id de los productos
        String regex = "(\\d+)\\s*-\\s*";
        if (articulosCantidades.contains(",")){
            regex = ",\\s*(\\d+)";
        }
        //Buscar los IDs de los productos en el carrito
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(articulosCantidades);
        List<Integer> idList = new ArrayList<>();
        while (matcher.find()){
            String idString = matcher.group(1);
            int id = Integer.parseInt(idString);
            idList.add(id);
        }
        //Buscar la tarjeta del usuario
        Tarjeta tarjeta = tarjetaService.buscarPorId(idTarjeta);
        //Obtener cantidades de productos
        String[] elementos = cantidades.substring(1,cantidades.length()-1).split(",");
        List<Integer> cantidadesNumero = new ArrayList<>();
        for (String elemento : elementos){
            cantidadesNumero.add(Integer.parseInt(elemento.trim()));
        }
        //Si la tarjeta no existe, redirigir con mensaje de error
        if (tarjeta==null){
            model.addAttribute("msgE", "Lo sentimos, no hemos encontrado su tarjeta");
            Page<Articulo> listado = articuloService.buscarTodos(pageable);

            PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listado",listado);
            model.addAttribute("listadoArticulos",listado);
            model.addAttribute("page",pageRender);
            model.addAttribute("usuarioActual",usuario);

            return "articulos/tienda";
        }else{
            int cont = 0;
            int cont2 = 0;
            HashMap<Integer,Integer> idsCantidad = new HashMap<>();
            /*Eliminar duplicados*/
            HashSet<Integer> setWithoutDuplicates = new HashSet<>(idList);
            idList = new ArrayList<>(setWithoutDuplicates);

            //Calcular cantidades de cada producto en el carrito
            for (Integer integer : idList) {
                Articulo articulo = articuloService.buscarArticuloPorId(integer);
                idsCantidad.put(articulo.getId(), 0);
                if (articulo.getTipo().equalsIgnoreCase("ropa")) {
                    List<Ropa> listaRopa = ropaService.buscarPorId(pageable, articulo).getContent();

                    for (int j = 0; j < listaRopa.size(); j++) {
                        idsCantidad.replace(articulo.getId(), idsCantidad.get(articulo.getId()) + cantidadesNumero.get(cont2));
                        cont2 += 1;
                    }
                } else {
                    idsCantidad.replace(articulo.getId(), idsCantidad.get(articulo.getId()) + cantidadesNumero.get(cont2));
                    cont2 += 1;
                }
            }
            //Aplicar descuentos si existen
            for (Map.Entry<String, String> entry : descuentos.entrySet()) {
                String idArticulo = entry.getKey();
                String descuento = entry.getValue();
                //Manejar los descuentos según tus necesidades
                if (idArticulo.contains("descuentos")){
                    if (!descuento.isBlank() || !descuento.isEmpty()) {
                        Descuento descuento1 = descuentoService.buscarPorId(Integer.parseInt(descuento));
                        if (descuento1 != null && descuento1.getUsuario() == usuario) {
                            if (descuento1.getTipo().equalsIgnoreCase("valor")) {
                                precioTotal = precioTotal - (descuento1.getValor() * idsCantidad.get(descuento1.getArticulo().getId()));
                            } else {
                                precioTotal = precioTotal - ((double) descuento1.getValor() * descuento1.getArticulo().getPrecio() * idsCantidad.get(descuento1.getArticulo().getId()));
                            }
                        }
                        descuentoService.eliminarDescuento(descuento1.getId());
                    }
                }
            }
            //Procesar la compra si el saldo es suficiente
            if (tarjeta.getSaldo()>=precioTotal){
                tarjetaService.guardarTarjeta(new Tarjeta(tarjeta.getId(),tarjeta.getSaldo()-precioTotal));
                usuario.setPuntos(usuario.getPuntos()+(int) (precioTotal));
                usuario.setPuntosT(usuario.getPuntosT()+ usuario.getPuntos()+(int) (precioTotal));
                usuarioService.guardarUsuario(usuario);
                for (Integer integer : idList) {
                    Articulo articulo = articuloService.buscarArticuloPorId(integer);
                    if (articulo.getTipo().equalsIgnoreCase("ropa")) {
                        List<Ropa> ropas = ropaService.buscarPorId(pageable, articulo).getContent();
                        for (Ropa ropa : ropas) {
                            ropaService.guardarRopa(new Ropa(ropa.getIdPropio(), ropa.getIdRopa(), ropa.getCantidad() - cantidadesNumero.get(cont)));
                            cont += 1;
                        }
                    } else {
                        Deportivo deportivo = deportivoService.buscarDeportivoPorId(integer);
                        deportivoService.guardarDeportivo(new Deportivo(deportivo.getId(), deportivo.getDeporte(), deportivo.getCantidad() - cantidadesNumero.get(cont), articulo));
                        cont += 1;
                    }
                }
                //Redirigir con mensaje de compra exitosa
                Page<Articulo> listado = articuloService.buscarTodos(pageable);

                PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listado",listado);

                //Se pasan como variables al HTML para utilizarlas como Thymeleaf
                model.addAttribute("msg", "Compra realizada con exito");
                model.addAttribute("titulo","Lista de todos las articulos");
                model.addAttribute("listadoArticulos",listado);
                model.addAttribute("page",pageRender);
                model.addAttribute("usuarioActual",usuario);
                sesion.setAttribute("carritoArticulo",new ArrayList<Articulo>());

                return "articulos/tienda";
            }else{
                //Redirigir con mensaje de saldo insuficiente
                model.addAttribute("msgE", "Lo sentimos, no tiene saldo suficiente");
                model.addAttribute("titulo","Lista de todos las articulos");
                Page<Articulo> listado = articuloService.buscarTodos(pageable);

                PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listado",listado);
                model.addAttribute("listadoArticulos",listado);
                model.addAttribute("page",pageRender);
                model.addAttribute("usuarioActual",usuario);

                return "articulos/tienda";
            }
        }
    }

}
