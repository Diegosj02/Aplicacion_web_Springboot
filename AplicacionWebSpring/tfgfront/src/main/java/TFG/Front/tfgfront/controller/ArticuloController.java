package TFG.Front.tfgfront.controller;

import TFG.Front.tfgfront.model.*;
import TFG.Front.tfgfront.paginator.PageRender;
import TFG.Front.tfgfront.services.IArticuloService;
import TFG.Front.tfgfront.services.IDeportivoService;
import TFG.Front.tfgfront.services.IDescuentoService;
import TFG.Front.tfgfront.services.IRopaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/aarticulos")
public class ArticuloController {

    //Para utilizar los servicios necesarios
    @Autowired
    IArticuloService articuloService;
    @Autowired
    IDeportivoService deportivoService;
    @Autowired
    IRopaService ropaService;
    @Autowired
    IDescuentoService descuentoService;

    //Permite obtener todos los articulos
    @GetMapping("/listado")
    public String listadoArticulos(Model model, @RequestParam(name="page",defaultValue = "0")int page, HttpSession sesion){
        //Se recoje el usuario actual, se buscan todos los articulos y se crea un nuevo carrito de Compra
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        Pageable pageable = PageRequest.of(page,100);
        Page<Articulo> listado = articuloService.buscarTodos(pageable);

        PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listado",listado);

        //Se pasan como variables al HTML para utilizarlas como Thymeleaf
        model.addAttribute("titulo","Lista de todos las articulos");
        model.addAttribute("listadoArticulos",listado);
        model.addAttribute("page",pageRender);
        model.addAttribute("usuarioActual",usuario);

        return "articulos/tienda";
    }

    //Permite observar los detalles de un articulo concreto
    @GetMapping("/detallesArticulo/{idArticulo}")
    public String detallesArticulo(Pageable pageable,Model model,HttpSession sesion,@PathVariable("idArticulo")Integer idArticulo){
        //Obtenemos el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        //Buscamos todos los artículos disponible
        Page<Articulo> listado = articuloService.buscarTodos(pageable);
        //Buscamos el artículo específico por su ID
        Articulo articulo = articuloService.buscarArticuloPorId(idArticulo);
        Deportivo deportivo = new Deportivo();
        List<Ropa> ropa = new ArrayList<>();
        List<String> tallas = new ArrayList<>();

        //Verificamos si el artículo es de tipo deportivo o ropa
        if (articulo.getTipo().equalsIgnoreCase("deportivo")) {
            //Si es deportivo, buscamos los detalles del artículo deportivo
            deportivo = deportivoService.buscarDeportivoPorId(articulo.getId());
        } else {
            //Si es ropa, buscamos los detalles de la ropa y sus tallas disponibles
            ropa = ropaService.buscarPorId(pageable, articulo).getContent();
            for (Ropa value : ropa) {
                tallas.add(value.getIdPropio().getTalla());
            }
        }

        //Añadimos los atributos al modelo para la vista
        model.addAttribute("deportivoActual",deportivo);
        model.addAttribute("ropaActual",ropa);
        model.addAttribute("tallas",tallas);
        model.addAttribute("listadoArticulos",listado);
        model.addAttribute("articuloActual",articulo);
        model.addAttribute("titulo",articulo.getId());
        model.addAttribute("usuarioActual",usuario);

        return "articulos/detallesArticulo";
    }
    @GetMapping("/filtrarNombre")
    public String filtrarNombre(Model model,HttpSession sesion,@RequestParam String nombre,@RequestParam(name="page",defaultValue = "0")int page){
        //Obtenemos el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Definimos el pageable
        Pageable pageable = PageRequest.of(page, 100);

        //Si el nombre está vacío, redireccionamos al listado completo de artículos
        if (nombre.isEmpty()) {
            return "redirect:/aarticulos/listado";
        }

        //Buscamos los artículos por el nombre proporcionado
        Page<Articulo> listado = articuloService.buscarPorNombre(nombre, pageable);
        PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listado", listado);

        //Añadimos los atributos al modelo para la vista
        model.addAttribute("titulo", "Lista de todos los artículos");
        model.addAttribute("listadoArticulos", listado);
        model.addAttribute("page", pageRender);


        return "articulos/tienda";
    }
    @GetMapping("/filtrarFormulario")
    public String filtrarFormulario( @RequestParam(name="page",defaultValue = "0")int page,Model model,HttpSession sesion,@RequestParam String precioMin,@RequestParam String precioMax,@RequestParam(name="tipoRadios", required = false) String tipo,@RequestParam(name="ordenRadios", required = false) String orden){
        //Obtenemos el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Definimos el pageable
        Pageable pageable = PageRequest.of(page, 100);
        //Inicializamos el listado de artículos
        Page<Articulo> listado = articuloService.buscarTodos(pageable);
        //Realizamos filtrado basado en los parámetros recibidos
        //Ningun filtro -> Todos los articulo por orden de ID
        if(precioMax.isEmpty()) {
            if (precioMin.isEmpty()) {
                if (tipo == null) {
                    //Sin orden
                    if (orden == null){
                        return "redirect:/aarticulos/listado";
                    }
                }
                //Solo con filtro de Tipo
                else {
                    //Solo ropa
                    if (tipo.equals("ropa")) {
                        listado = articuloService.buscarPorTipo("ropa", pageable);
                    }
                    //Solo articulos deportivos
                    else {
                        listado = articuloService.buscarPorTipo("deportivo", pageable);
                    }
                }
            }
            //Ha rellenado el precio minimo...
            else {
                //...pero no el tipo -> Busqueda por precio minimo
                if (tipo == null) {
                    listado = articuloService.buscarPorPrecioMin(Integer.parseInt(precioMin), pageable);
                    //...pero si el tipo...
                } else {
                    //Solo ropa con precio minimo
                    if (tipo.equals("ropa")) {
                        listado = articuloService.buscarPorTipoYPrecioMinimo("ropa", Integer.parseInt(precioMin), pageable);
                    }
                    //Solo articulos deportivo con precio minimo
                    else {
                        listado = articuloService.buscarPorTipoYPrecioMinimo("deportivo", Integer.parseInt(precioMin), pageable);
                    }
                }
            }
        }
        //Ha rellenado el precio máximo...
        else{
            //...pero no el minimo...
            if (precioMin.isEmpty()) {
                //... ni el tipo
                if (tipo == null) {
                    //Busqueda por precio maximo
                    listado = articuloService.buscarPorPrecioMax(Integer.parseInt(precioMax),pageable);
                }
                //Precio maximo y tipo
                else {
                    //Solo ropa con precio maximo
                    if (tipo.equals("ropa")) {
                        listado = articuloService.buscarPorTipoYPrecioMaximo("ropa",Integer.parseInt(precioMax),pageable);
                    }
                    //Solo articulos deportivos con precio maximo
                    else {
                        listado = articuloService.buscarPorTipoYPrecioMaximo("deportivo",Integer.parseInt(precioMax), pageable);
                    }
                }
            }
            //Precio minimo y maximo...
            else {
                //Sin especificar tipo
                if (tipo == null) {
                    //Busqueda por precio en un rango
                    listado = articuloService.buscarPorPrecioRango(Integer.parseInt(precioMin),Integer.parseInt(precioMax), pageable);
                }
                //Especificando tipo
                else {
                    //Solo ropa entre precio minimo y maximo
                    if (tipo.equals("ropa")) {
                        listado = articuloService.buscarPorTipoYPrecioRango("ropa", Integer.parseInt(precioMin),Integer.parseInt(precioMax), pageable);
                    }
                    //Solo articulos deportivos entre precio minimo y maximo
                    else {
                        listado = articuloService.buscarPorTipoYPrecioRango("deportivo", Integer.parseInt(precioMin),Integer.parseInt(precioMax), pageable);
                    }
                }
            }
        }
        //Se ha seleccionado un orden
        if (orden!=null){
            //Por orden alfabetico
            if (orden.equals("alfabetico")){
                listado = articuloService.ordenarPorCriterio("alfabetico",pageable,listado.getContent());
            }
            //Por orden de precio creciente
            else if(orden.equals("precioAsc")){
                listado =articuloService.ordenarPorCriterio("precioAsc",pageable,listado.getContent());
            }
            //Por orden de precio decreciente
            else{
                listado = articuloService.ordenarPorCriterio("precioDesc",pageable,listado.getContent());
            }
        }

        //Añadimos los atributos al modelo para la vista
        PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listado",listado);
        model.addAttribute("titulo","Lista de todos las articulos");
        model.addAttribute("listadoArticulos",listado);
        model.addAttribute("page",pageRender);

        return "articulos/tienda";
    }

    @GetMapping("/formularioCompra")
    public String formularioCompra(Pageable pageable,HttpSession sesion,Model model,@RequestParam("idArticulo")int idArticulo){
        //Obtenemos el usuario de la sesión y lo añadimos al modelo
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Buscamos el artículo por su ID y lo añadimos al modelo
        Articulo articulo = articuloService.buscarArticuloPorId(idArticulo);
        model.addAttribute("articuloActual", articulo);

        //Inicializamos las listas de ropa y deportivos
        List<Ropa> ropa = new ArrayList<>();
        Deportivo deportivo = null;

        //Verificamos si el artículo es de tipo ropa o deportivo
        if (articulo.getTipo().equalsIgnoreCase("ropa")) {
            //Si es ropa, buscamos los detalles de la ropa
            ropa = ropaService.buscarPorId(pageable, articulo).getContent();
        } else {
            //Si es deportivo, buscamos los detalles del artículo deportivo
            deportivo = deportivoService.buscarDeportivoPorId(idArticulo);
        }
        //Añadimos el articulo concreto al modelo
        model.addAttribute("concretoRopa", ropa);
        model.addAttribute("concretoDeportivo", deportivo);

        return "articulos/formularioCompra";
    }

    @GetMapping("/confirmarCompraArticulo")
    public String confirmarCompraArticulo(HttpServletRequest request, Pageable pageable, HttpSession sesion, Model model, @RequestParam("articulo")int idArticulo, @RequestParam(value = "cantidad", required = false)String cantidad){
        //Se obtiene el usuario actual de la sesión y se añade al modelo
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Se busca el artículo específico por su ID y se añade al modelo
        Articulo articulo = articuloService.buscarArticuloPorId(idArticulo);
        model.addAttribute("articuloActual",articulo);

        //Se obtienen los nombres de los parámetros enviados en la solicitud
        Enumeration<String> parametros = request.getParameterNames();

        //Se crea un HashMap para almacenar las tallas y las cantidades seleccionadas
        HashMap<String,Integer> tallaCantidad = new HashMap<>();

        //Se inicializa el precio total en 0
        double precioTotal = 0;

        //Si el tipo de artículo es ropa...
        if (articulo.getTipo().equalsIgnoreCase("ropa")){

            //Se recorren los parámetros de la solicitud...
            while (parametros.hasMoreElements()){
                String parametro = parametros.nextElement();

                //Si el parámetro comienza con "cantidad_"...
                if (parametro.startsWith("cantidad_")){

                    //Y se divide el parámetro en partes utilizando el guion bajo como separador
                    String[] partes = parametro.split("_");

                    //La segunda parte corresponde a la talla seleccionada
                    String talla = partes[1];

                    //Se obtiene la cantidad seleccionada para esa talla
                    int cantidadTalla = Integer.parseInt(request.getParameter(parametro));

                    //Se añade la talla y la cantidad al HashMap
                    tallaCantidad.put(talla,cantidadTalla);
                }
            }

            //Se calcula el precio total sumando el precio de cada talla por la cantidad seleccionada
            for (String clave: tallaCantidad.keySet()){
                precioTotal += tallaCantidad.get(clave) * articulo.getPrecio();
            }

        }else{
            //Si el artículo no es de tipo ropa, se calcula el precio total utilizando la cantidad proporcionada
            precioTotal += Integer.parseInt(cantidad) * articulo.getPrecio();
        }

        //Se obtienen los descuentos disponibles para el usuario actual
        List<Descuento> descuentos = descuentoService.buscarPorUsuario(usuario.getId(),pageable).getContent();

        if (precioTotal == 0){
            Page<Articulo> listado = articuloService.buscarTodos(pageable);
            PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listado",listado);
            model.addAttribute("titulo","Lista de todos las articulos");
            model.addAttribute("listadoArticulos",listado);
            model.addAttribute("page",pageRender);
            model.addAttribute("usuarioActual",usuario);
            model.addAttribute("msgE","No se ha introducido ningún elemento para comprar");
            return "articulos/tienda";
        }

        //Se agrega el resto de elementos al modelo
        model.addAttribute("tallasCantidades",tallaCantidad);
        model.addAttribute("precioFinal",precioTotal);
        model.addAttribute("cantidadDeportivo",cantidad);
        model.addAttribute("descuentos",descuentos);

        return "articulos/PagarCompraArticulo";
    }

    @GetMapping("/listaArticulos")
    public String listaArticulos(Pageable pageable,HttpSession sesion,Model model){
        //Obtiene el usuario actual almacenado en la sesión y lo agrega al modelo
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Obtiene el carrito de la sesión
        List<Articulo> carrito = (List<Articulo>) sesion.getAttribute("carritoArticulo");

        if (carrito.isEmpty()){
            return "redirect:/aarticulos/listado";
        }
        //Crea una lista para almacenar los IDs únicos de los artículos en el carrito
        List<Integer> articulos = new ArrayList<>();

        //Filtra los IDs únicos de los artículos en el carrito
        for (Articulo articulo : carrito) {
            if (!articulos.contains(articulo.getId())) {
                articulos.add(articulo.getId());
            }
        }

        //Limpia el carrito para evitar duplicados
        carrito.clear();

        //Obtiene nuevamente los artículos del carrito, evitando duplicados, y los agrega de nuevo al carrito
        for (Integer integer : articulos) {
            carrito.add(articuloService.buscarArticuloPorId(integer));
        }

        //Agrega el carrito actualizado al modelo
        model.addAttribute("carritoArticulo", carrito);

        //Crea listas separadas para almacenar los artículos de tipo Deportivo y Ropa presentes en el carrito
        List<Deportivo> deportivos = new ArrayList<>();
        List<Ropa> ropas = new ArrayList<>();

        //Itera sobre el carrito para separar los artículos por tipo
        for (Articulo articulo : carrito) {
            if (articulo.getTipo().equalsIgnoreCase("ropa")) {
                //Si el artículo es de tipo ropa, busca y agrega todas las tallas correspondientes
                ropas.addAll(ropaService.buscarPorId(pageable, articulo).getContent());
            } else {
                //Si el artículo es de tipo deportivo, se agrega a la lista de deportivos
                deportivos.add(deportivoService.buscarDeportivoPorId(articulo.getId()));
            }
        }

        //Agrega las listas de deportivos y ropas al modelo
        model.addAttribute("deportivos", deportivos);
        model.addAttribute("ropas", ropas);

        return "articulos/ListaArticulos";
    }

    @GetMapping("/annadirLista")
    public String annadirArticuloLista(Model model, HttpSession sesion,@RequestParam("idArticulo")int idArticulo, @RequestParam(name="page",defaultValue = "0")int page){

        //Obtiene el usuario actual almacenado en la sesión y lo agrega al modelo
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Obtiene el artículo correspondiente al ID proporcionado
        Articulo articulo = articuloService.buscarArticuloPorId(idArticulo);

        //Obtiene el carrito de la sesión, se verifica que el artículo no está ya en el carrito, y si no está, lo agrega y actualiza la sesion
        List<Articulo> carritoArticulo = (List<Articulo>) sesion.getAttribute("carritoArticulo");
        if (!carritoArticulo.contains(articulo)){
            carritoArticulo.add(articulo);
        }
        sesion.setAttribute("carritoArticulo", carritoArticulo);


        //Recoge todos los articulos y los muestra en paginas de tamaño 100
        Pageable pageable = PageRequest.of(page, 100);
        Page<Articulo> listado = articuloService.buscarTodos(pageable);
        PageRender<Articulo> pageRender = new PageRender<>("/aarticulo/listado", listado);

        //Agrega atributos al modelo
        model.addAttribute("msg","Artículo incluido en el carrito de la compra");
        model.addAttribute("titulo", "Lista de todos los artículos");
        model.addAttribute("listadoArticulos", listado);
        model.addAttribute("page", pageRender);
        model.addAttribute("usuarioActual", usuario);

        return "articulos/tienda";
    }

    @GetMapping("/pagarCompras")
    public String pagarCompras(Model model,HttpSession sesion,Pageable pageable,@RequestParam("cantidades")String[] cantidades,@RequestParam("carritoArticulo") String carritoArticulo){

        //Obtiene el usuario actual almacenado en la sesión y lo añadde al model
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);

        //Si el usuario no ha iniciado sesión, se le devuelve a la página anterior
        if (usuario == null){
            return "redirect:/uusuario/";
        }

        //Lista para almacenar los IDs de los artículos en el carrito
        List<Integer> articulosId = new ArrayList<>();

        //Separa la cadena de texto del carrito y extrae los IDs de los artículos
        String[] articulosCarrito = carritoArticulo.split("},");
        for (int i = 0; i < articulosCarrito.length; i++) {
            if (i == 0) {
                articulosCarrito[i].substring(1);
            }
            String id = articulosCarrito[i].substring(13).split(",")[0];
            articulosId.add(Integer.parseInt(id));
        }

        /*Lista para almacenar todos los IDs de los artículos. En caso de los articulos deportivos, solo una vez,
        pero en caso de ropas, una vez por cada talla existente. */
        List<Integer> articulosListaCompleta = new ArrayList<>();
        for (int i = 0; i < articulosCarrito.length; i++) {
            if (articuloService.buscarArticuloPorId(articulosId.get(i)).getTipo().equalsIgnoreCase("ropa")) {
                List<Ropa> ropas = ropaService.buscarPorId(pageable, articuloService.buscarArticuloPorId(articulosId.get(i))).getContent();
                for (Ropa ropa : ropas) {
                    articulosListaCompleta.add(ropa.getIdPropio().getIdropa());
                }
            } else {
                articulosListaCompleta.add(articulosId.get(i));
            }
        }

        //Map para almacenar los IDs de los artículos junto con sus cantidades
        Map<String, Integer> articulosCantidades = new HashMap<>();

        //Se inicializa un contador para saber que cantidad del ArrayList 'cantidades' se debe escoger en cada caso
        int cont = 0;

        //Para cada articulo...
        for (Integer integer : articulosId) {
            //...si es ropa...
            if (articuloService.buscarArticuloPorId(integer).getTipo().equalsIgnoreCase("ropa")) {
                //... se buscan todas las tallas relacionadas, se añade al Map y se añade uno al contador por cada talla
                List<Ropa> ropas = ropaService.buscarPorId(pageable, articuloService.buscarArticuloPorId(integer)).getContent();
                for (Ropa ropa : ropas) {
                    articulosCantidades.put(integer + " - " + articuloService.buscarArticuloPorId(integer).getNombre() + " (Talla: " + ropa.getIdPropio().getTalla() + ")", Integer.parseInt(cantidades[cont]));
                    cont += 1;
                }
            }
            //Si es deportivo
            else {
                //Se añade al Map y se suma 1 al contador
                articulosCantidades.put(integer + " - " + articuloService.buscarArticuloPorId(integer).getNombre(), Integer.parseInt(cantidades[cont]));
                cont += 1;
            }
        }

        //Se calcula el precio total de la compra
        double precio = 0;
        for (int i = 0; i < articulosCantidades.size(); i++) {
            if (cantidades.length==1 && cantidades[i].equalsIgnoreCase("0")){
                return "redirect:/aarticulos/listado";
            }else{
                precio += articuloService.buscarArticuloPorId(articulosListaCompleta.get(i)).getPrecio() * Integer.parseInt(cantidades[i]);
            }
        }

        //Ordena el mapa de artículos y cantidades
        Map<String, Integer> treeMap = new TreeMap<>(Comparator.naturalOrder());
        treeMap.putAll(articulosCantidades);
        articulosCantidades = treeMap;

        //Lista para almacenar los artículos
        List<Articulo> articulos = new ArrayList<>();

        //Lista para almacenar los IDs de los artículos repetidos y sus cantidades
        List<Integer> articulosRepetidosCantidades = new ArrayList<>();
        HashMap<Integer, Integer> articulosConCantidades = new HashMap<>();

        //Se obtiene la informacion completa de cada articulo
        for (Integer integer : articulosId) {
            articulos.add(articuloService.buscarArticuloPorId(integer));
        }

        //Se añade el ID del producto tantas veces como cantidad solicitada de ese mismo producto se haya pedido
        for (String clave : articulosCantidades.keySet()) {
            for (int i = 0; i < articulosCantidades.get(clave); i++) {
                articulosRepetidosCantidades.add(Integer.parseInt(clave.split(" - ")[0]));
            }
        }

        //Se actualiza la cantidad de veces que se ha solicitado un producto
        for (int i = 0; i < articulosRepetidosCantidades.size(); i++) {
            articulosConCantidades.put(articulosRepetidosCantidades.get(i), Collections.frequency(articulosRepetidosCantidades, articulosRepetidosCantidades.get(i)));
        }

        //Obtiene los descuentos del usuario
        List<Descuento> descuentos = descuentoService.buscarPorUsuario(usuario.getId(), pageable).getContent();

        if (precio == 0){
            Page<Articulo> listado = articuloService.buscarTodos(pageable);
            PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listado",listado);
            model.addAttribute("titulo","Lista de todos las articulos");
            model.addAttribute("listadoArticulos",listado);
            model.addAttribute("page",pageRender);
            model.addAttribute("usuarioActual",usuario);
            model.addAttribute("msgE","No se ha introducido ningún elemento para comprar");
            return "articulos/tienda";
        }

        //Agrega atributos al modelo
        model.addAttribute("precioTotal", precio);
        model.addAttribute("articulosCantidades", articulosCantidades);
        model.addAttribute("articulos", articulos);
        model.addAttribute("descuentos", descuentos);
        model.addAttribute("articulosConCantidades", articulosConCantidades);

        return "articulos/PagarCompraArticulos";
    }

    @GetMapping("/opcionesAdministrador")
    public String opcionesAdministrador(Model model,HttpSession sesion,Pageable pageable){
        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual",usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario==null || usuario.getId()!=-1){
            return "redirect:/uusuario";
        }else{
            //Si es administrador, muestra las opciones de administrador para los articulos
            model.addAttribute("articulos",articuloService.buscarTodos(pageable));
            return "administracion/articulos";
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
            //Si es administrador, muestra una lista con todos los articulos organizados en paginas de tamaño 5.
            Pageable pageable = PageRequest.of(page, 5);
            Page<Articulo> listado = articuloService.buscarTodos(pageable);
            PageRender<Articulo> pageRender = new PageRender<>("/aarticulos/listadoAdministrador", listado);

            //Agrega atributos al modelo

            model.addAttribute("titulo", "Listado de todos los articulos");
            model.addAttribute("listadoArticulos", listado);
            model.addAttribute("page", pageRender);
            return "administracion/listaArticulos";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarArticulo(Model model, HttpSession sesion, @PathVariable("id") Integer id){
        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        } else {
            //Si es administrador, recoge los datos del articulo
            Articulo articulo = articuloService.buscarArticuloPorId(id);

            Deportivo deportivo = null;
            //Si es deportivo, recoge los datos pertinentes
            if (articulo.getTipo().equalsIgnoreCase("deportivo")){
                deportivo = deportivoService.buscarDeportivoPorId(articulo.getId());
            }

            //Agrega atributos al modelo

            model.addAttribute("titulo", "Editar artículo");
            model.addAttribute("articulo", articulo);
            model.addAttribute("tipo",articulo.getTipo());
            model.addAttribute("deportivo",deportivo);
            return "administracion/formArticuloEditar";
        }
    }

    @PostMapping("/guardarEdicionRopa/")
    public String guardarEdicionRopa(Articulo articulo,Model model,RedirectAttributes attributes,HttpSession sesion){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }



        //Establece el tipo del articulo y actualiza la ruta de la imagen. Posteriormente, guarda los cambios
        articulo.setTipo(articuloService.buscarArticuloPorId(articulo.getId()).getTipo());
        articulo.setImagen("/images/"+articulo.getImagen());
        articuloService.guardarArticulo(articulo);
        model.addAttribute("titulo", "Nuevo Articulo");
        attributes.addFlashAttribute("msg", "Los datos del articulo fueron guardados!");
        return "redirect:/aarticulos/listadoAdministrador";
    }
    @PostMapping("/guardarEdicionDeportivo/")
    public String guardarEdicionDeportivo(Deportivo deportivo,Model model,RedirectAttributes attributes,HttpSession sesion){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Establece la id, tipo e imagen del deportivo tras editarlo y guarda tanto el articulo como el material
        Articulo articulo = deportivo.getArticulo();
        articulo.setId(deportivo.getId());
        articulo.setTipo(articuloService.buscarArticuloPorId(deportivo.getId()).getTipo());
        articulo.setImagen("/images/"+articulo.getImagen());
        articuloService.guardarArticulo(articulo);
        deportivo.setArticulo(articuloService.buscarArticuloPorId(articulo.getId()));
        deportivoService.guardarDeportivo(deportivo);
        model.addAttribute("titulo", "Nuevo Articulo");
        attributes.addFlashAttribute("msg", "Los datos del articulo fueron guardados!");
        return "redirect:/aarticulos/listadoAdministrador";
    }

    @PostMapping("/formConcreto/")
    public String formularioConcreto(Pageable pageable,Model model, Articulo articulo,HttpSession sesion){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }



        //Si el articulo existe pero su ID es nulo (articulo nuevo) le coloca el id = 0
        if (articulo.getId() == null){
            articulo.setId(0);
        }
        //Si es un Articulo editado, le coloca el tipo que le corresponde y su imagen
        else{
            articulo.setTipo(articuloService.buscarArticuloPorId(articulo.getId()).getTipo());
            articulo.setImagen("/images/"+articulo.getImagen());
        }

        //Se guarda el articulo
        articuloService.guardarArticulo(articulo);

        //Si el articulo se acaba de añadir, debe ser el ultimo, por lo que ese articulo se asocia con el elemento de Ropa o deportivo correspondiente, segun su tipo
        Articulo articuloLast = null;
        if (articulo.getId() == 0){
            articuloLast = articuloService.buscarTodos(pageable).getContent().get(articuloService.buscarTodos(pageable).getContent().size()-1);
        }
        model.addAttribute("articulo",articuloLast);
        if (articulo.getTipo().equalsIgnoreCase("ropa")){
            Ropa ropa = new Ropa();
            ropa.setIdPropio(new RopaId(articulo.getId(),"Sin especificar"));
            model.addAttribute("ropa",ropa);
        }else{
            model.addAttribute("deportivo",new Deportivo());
        }
        model.addAttribute("titulo", "Nuevo Articulo");
        return "administracion/formularioConcreto";
    }

    @PostMapping("/guardarConcreto/")
    public String guardarConcreto(Model model,Pageable pageable, Ropa ropa, Deportivo deportivo,HttpSession sesion){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Recoge el ultimo articulo guardado
        Articulo articuloLast = articuloService.buscarTodos(pageable).getContent().get(articuloService.buscarTodos(pageable).getContent().size()-1);
        //Si es ropa y tiene id, entonces se guardan los cambios
        if (ropa.getIdPropio() != null){
            ropa.setIdRopa(articuloLast);
            ropaService.guardarRopa(ropa);
        //Si es deportivo y no es nulo, se guardan los cambios
        } else if (deportivo != null) {
            deportivoService.guardarDeportivo(deportivo);
        }
        return "redirect:/aarticulos/listadoAdministrador";
    }

    @PostMapping("/guardar/")
    public String guardarArticulo(Pageable pageable, Model model, Articulo articulo, RedirectAttributes attributes,HttpSession sesion){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }



        //Verificar si el ID del artículo es nulo
        if (articulo.getId() == null) {
            //Si el ID es nulo, establecerlo en 0 (indicando un nuevo artículo)
            articulo.setId(0);
        } else {
            //Si el ID no es nulo, obtener y establecer el tipo del artículo basado en el artículo existente
            articulo.setTipo(articuloService.buscarArticuloPorId(articulo.getId()).getTipo());
        }

        //Guardar el artículo en la base de datos
        articuloService.guardarArticulo(articulo);

        //Verificar si el artículo es nuevo (ID = 0)
        if (articulo.getId() == 0) {
            //Si es nuevo, se obtienen sus datos actualizados (ID)
            Articulo articuloLast = articuloService.buscarTodos(pageable).getContent().get(articuloService.buscarTodos(pageable).getContent().size() - 1);
            //Verificar el tipo de artículo
            if (articulo.getTipo().equalsIgnoreCase("ropa")) {
                //Si es ropa, guardar un nuevo objeto de Ropa con una talla predeterminada de "No especificado"
                ropaService.guardarRopa(new Ropa(new RopaId(null, "No especificado"), articuloLast, 0));
            } else {
                //Si no es ropa, guardar un nuevo objeto de Deportivo con un deporte predeterminado de "No especificado"
                deportivoService.guardarDeportivo(new Deportivo(articuloLast.getId(), "No especificado", 0, articuloLast));
            }
        }

        //Agregar atributos al modelo
        model.addAttribute("titulo", "Nuevo Articulo");
        attributes.addFlashAttribute("msg", "Los datos del articulo fueron guardados!");

        return "redirect:/aarticulos/listadoAdministrador";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarArticulo(Model model,@PathVariable("id") Integer id, RedirectAttributes attributes,HttpSession sesion){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);

        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Llamar al servicio para eliminar el artículo con el ID proporcionado
        articuloService.eliminarArticulo(id);

        //Agregar un mensaje para notificar al usuario que los datos del artículo fueron borrados
        attributes.addFlashAttribute("msg", "Los datos del artículo fueron borrados!");

        return "redirect:/aarticulos/listadoAdministrador";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model,HttpSession sesion){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Agregar el título para la página de nuevo artículo
        model.addAttribute("titulo", "Nuevo artículo");

        //Crear un nuevo Articulo y agregarlo al modelo
        Articulo articulo = new Articulo();
        model.addAttribute("articulo", articulo);

        return "administracion/formArticulo";
    }

    //ROPA
    @GetMapping("/listaRopa/{id}")
    public String listadoAdministradorRopa(Model model, HttpSession sesion, @PathVariable("id") Integer id,@RequestParam(name = "page", defaultValue = "0") int page) {

        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Verificar si el usuario está autenticado como administrador
        if (usuario == null || usuario.getId() != -1) {
            //Redirigir al usuario no autenticado a la página de inicio de sesión
            return "redirect:/uusuario";
        } else {
            //Crear un objeto Pageable para la paginación
            Pageable pageable = PageRequest.of(page, 5);

            //Obtener un el articulo por su ID
            Articulo articulo = articuloService.buscarArticuloPorId(id);
            //Obtener todas las ropas asociadas al artículo
            Page<Ropa> listado = ropaService.buscarPorId(pageable, articuloService.buscarArticuloPorId(id));
            PageRender<Ropa> pageRender = new PageRender<>("/aarticulos/listaRopa", listado);

            //Agregar atributos al modelo

            model.addAttribute("titulo", "Lista de todas las tallas del articulo " + articulo.getNombre());
            model.addAttribute("listadoRopa", listado);
            model.addAttribute("articulo", articulo);
            model.addAttribute("page", pageRender);

            return "administracion/listaRopa";
        }
    }

    @GetMapping("/editarRopa/{id}/{talla}")
    public String editarRopa(@RequestParam(name = "page", defaultValue = "0") int page, Model model, HttpSession sesion, @PathVariable("id") Integer id, @PathVariable("talla") String talla) {
        //Obtener el usuario de la sesión
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);

        //Verificar si el usuario está autenticado como administrador
        if (usuario == null || usuario.getId() != -1) {
            //Redirigir al usuario no autenticado a la página de inicio de sesión
            return "redirect:/uusuario";
        } else {
            Pageable pageable = PageRequest.of(page, 5);

            //Buscar el artículo por su ID
            Articulo articulo = articuloService.buscarArticuloPorId(id);

            //Obtener las ropas asociadas con el artículo
            Page<Ropa> listado = ropaService.buscarPorId(pageable, articuloService.buscarArticuloPorId(id));
            PageRender<Ropa> pageRender = new PageRender<>("/aarticulos/listaRopa", listado);

            //Buscar la ropa por su talla
            Ropa ropa = null;
            for (int i = 0; i < listado.getContent().size(); i++) {
                if (listado.getContent().get(i).getIdPropio().getTalla().equalsIgnoreCase(talla)) {
                    ropa = listado.getContent().get(i);
                }
            }

            //Agregar atributos al modelo
            model.addAttribute("titulo", "Editar Ropa");
            ropa.setIdRopa(new Articulo(articulo.getId(), articulo.getPrecio(), articulo.getNombre(), articulo.getTipo(), articulo.getImagen()));
            model.addAttribute("ropa", ropa);

            return "administracion/formRopa";
        }
    }

    @PostMapping("/guardarRopa/")
    public String guardarArticulo(HttpSession sesion, Model model, Ropa ropa, RedirectAttributes attributes){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }


        //Guardar el artículo y la ropa asociada en la base de datos
        Articulo articulo = ropa.getIdRopa();
        articulo.setTipo("Ropa");
        articulo.setImagen("/images/"+ropa.getIdRopa().getImagen());
        articuloService.guardarArticulo(articulo);
        ropa.getIdPropio().setTalla(ropa.getIdPropio().getTalla().replace(",",""));
        ropaService.guardarRopa(ropa);

        //Agregar atributos al modelo
        model.addAttribute("titulo", "Nueva Ropa");
        attributes.addFlashAttribute("msg", "Los datos del artículo fueron guardados!");


        return "redirect:/aarticulos/listaRopa/" + ropa.getIdPropio().getIdropa();
    }

    @GetMapping("/nuevaRopa/{id}")
    public String nuevaRopa(HttpSession sesion, Model model, @PathVariable("id") int id){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Agregar atributos al modelo

        model.addAttribute("titulo", "Nueva Ropa");

        //Crear un nuevo objeto de Ropa y agregarlo al modelo
        Ropa ropa = new Ropa();
        ropa.setIdRopa(articuloService.buscarArticuloPorId(id));
        model.addAttribute("ropa", ropa);

        return "administracion/formRopa";
    }

    @GetMapping("/borrarRopa/{id}/{talla}")
    public String eliminarRopa(Model model,@PathVariable("id") int id, @PathVariable("talla") String talla, RedirectAttributes attributes,HttpSession sesion){

        //Comprobamos que el usuario actual es un administrador (ID=-1)
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        model.addAttribute("usuarioActual", usuario);
        //Si no lo es, lo devuelve a la pagina de inicio
        if (usuario == null || usuario.getId() != -1) {
            return "redirect:/uusuario";
        }

        //Crear un objeto RopaId con el ID del artículo y la talla
        RopaId ropaId = new RopaId(id, talla);
        //Eliminar la ropa de la base de datos
        ropaService.eliminarRopa(ropaId);
        //Agregar un mensaje flash para notificar al usuario que los datos de la ropa fueron borrados
        attributes.addFlashAttribute("msg", "Los datos de la ropa fueron borrados!");

        //Redirigir al usuario a la lista de ropas asociadas con el artículo
        return "redirect:/aarticulos/listaRopa/" + ropaId.getIdropa();
    }


}
