package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Articulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ArticuloServiceImpl implements IArticuloService{

    @Autowired
    RestTemplate template;

    //URL para acceder al back-end y a la DDBB
    String url="http://localhost:8080/articulo";

    //Devuelve todos los articulos divididos en paginas de tamaño especificado
    @Override
    public Page<Articulo> buscarTodos(Pageable pageable) {
        Articulo[] articulos = template.getForObject(url,Articulo[].class);
        List<Articulo> articulosList = Arrays.asList(articulos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (articulosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,articulosList.size());
            list = articulosList.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),articulosList.size());
        return page;
    }

    //Devuelve, si existe, el articulo con la ID especificada. Si no existe, devuelve null
    @Override
    public Articulo buscarArticuloPorId(Integer idArticulo) {
        Articulo articulo= template.getForObject(url+"/id/"+idArticulo,Articulo.class);
        return articulo;
    }

    //Si el articulo existia anteriormente, lo modifica. Si no, lo crea
    @Override
    public void guardarArticulo(Articulo articulo) {
        if (articulo.getId()!=null && articulo.getId() > 0){
            template.put(url,articulo);
        }else{
            articulo.setId(0);
            System.out.println(articulo);
            template.postForObject(url,articulo,String.class);
        }
    }

    //Permite eliminar un articulo concreto
    @Override
    public void eliminarArticulo(Integer idArticulo) {
        template.delete(url+"/"+idArticulo);
    }

    //Devuelve todos los articulos con el nombre especificado divididos en paginas de tamaño especificado
    @Override
    public Page<Articulo> buscarPorNombre(String nombre, Pageable pageable) {

        Articulo[] articulos = template.getForObject(url + "/nombre/"+nombre,Articulo[].class);
        List<Articulo> articulosList = Arrays.asList(articulos);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (articulosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,articulosList.size());
            list = articulosList.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),articulosList.size());
        return page;

    }

    //Devuelve todos los articulos por tipo especificado divididos en paginas de tamaño especificado
    @Override
    public Page<Articulo> buscarPorTipo(String tipo, Pageable pageable) {

        Articulo[] articulos = template.getForObject(url+"/tipo/"+tipo,Articulo[].class);
        List<Articulo> articulosList = Arrays.asList(articulos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (articulosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,articulosList.size());
            list = articulosList.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),articulosList.size());
        return page;
    }

    //Devuelve todos los articulos según un precio minimo divididos en paginas de tamaño especificado
    @Override
    public Page<Articulo> buscarPorPrecioMin(double precio, Pageable pageable) {
        Articulo[] articulos = template.getForObject(url+"/precioMin/"+precio,Articulo[].class);
        List<Articulo> articulosList = Arrays.asList(articulos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (articulosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,articulosList.size());
            list = articulosList.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),articulosList.size());
        return page;
    }

    //Devuelve todos los articulos según un precio maximo divididos en paginas de tamaño especificado
    @Override
    public Page<Articulo> buscarPorPrecioMax(double precio, Pageable pageable) {
        Articulo[] articulos = template.getForObject(url+"/precioMax/"+precio,Articulo[].class);
        List<Articulo> articulosList = Arrays.asList(articulos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (articulosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,articulosList.size());
            list = articulosList.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),articulosList.size());
        return page;
    }

    //Devuelve todos los articulos según un rango de precios divididos en paginas de tamaño especificado
    @Override
    public Page<Articulo> buscarPorPrecioRango(double min, double max, Pageable pageable) {
        Articulo[] articulos = template.getForObject(url+"/precioRango/"+min+"/"+max,Articulo[].class);
        List<Articulo> articulosList = Arrays.asList(articulos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (articulosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,articulosList.size());
            list = articulosList.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),articulosList.size());
        return page;
    }

    //Devuelve todos los articulos según un precio minimo y tipo especifico divididos en paginas de tamaño especificado
    @Override
    public Page<Articulo> buscarPorTipoYPrecioMinimo(String tipo, double precio,Pageable pageable) {
        Articulo[] articulos = template.getForObject(url+"/TipoMin/"+tipo+"/"+precio,Articulo[].class);
        List<Articulo> articulosList = Arrays.asList(articulos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (articulosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,articulosList.size());
            list = articulosList.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),articulosList.size());
        return page;
    }

    //Devuelve todos los articulos según un precio maximo y tipo especifico divididos en paginas de tamaño especificado
    @Override
    public Page<Articulo> buscarPorTipoYPrecioMaximo(String tipo, double precio,Pageable pageable) {

        Articulo[] articulos = template.getForObject(url+"/TipoMax/"+tipo+"/"+precio,Articulo[].class);
        List<Articulo> articulosList = Arrays.asList(articulos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (articulosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,articulosList.size());
            list = articulosList.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),articulosList.size());
        return page;
    }

    //Devuelve todos los articulos según un rango de precios y tipo especifico divididos en paginas de tamaño especificado
    @Override
    public Page<Articulo> buscarPorTipoYPrecioRango(String tipo, double min, double max,Pageable pageable) {
        Articulo[] articulos = template.getForObject(url+"/TipoRan/"+tipo+"/"+min+"/"+max,Articulo[].class);
        List<Articulo> articulosList = Arrays.asList(articulos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (articulosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,articulosList.size());
            list = articulosList.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),articulosList.size());
        return page;
    }

    //Permite ordenar una lista de Articulos según un criterio
    @Override
    public Page<Articulo> ordenarPorCriterio(String criterio,Pageable pageable,List<Articulo> articulosLista) {
        Articulo[] articulos = template.getForObject(url+"/orden/"+criterio,Articulo[].class);
        List<Articulo> ordenado = new ArrayList<>();
        for (int i=0;i<articulos.length;i++){
            for (int j=0;j<articulosLista.size();j++){
                if (articulos[i].getId() == articulosLista.get(j).getId()){
                    ordenado.add(articulos[i]);
                }
            }
        }
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Articulo> list;

        if (ordenado.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,ordenado.size());
            list = ordenado.subList(startItem,toIndex);
        }
        Page<Articulo> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),ordenado.size());
        return page;
    }
}
