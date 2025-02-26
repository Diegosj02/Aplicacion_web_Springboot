package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Articulo;
import TFG.Front.tfgfront.model.Descuento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class DescuentoServiceImpl implements IDescuentoService{

    @Autowired
    RestTemplate template;

    //URL que conecta con el back-end y la DDBB
    String url="http://localhost:8080/descuento";

    //Busca todos los descuentos existentes y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarTodos(Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Busca un descuento por su ID
    @Override
    public Descuento buscarPorId(int IdDescuento) {
        return template.getForObject(url+"/id/"+IdDescuento,Descuento.class);
    }

    //Busca todos los descuentos según el usuario y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarPorUsuario(int idUsuario, Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url+"/Usuario/"+idUsuario,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Busca todos los descuentos según el articulo y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarPorArticulo(int idArticulo, Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url+"/Articulo/"+idArticulo,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Busca todos los descuentos según el tipo y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarPorTipo(String tipo, Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url+"/tipo/"+tipo,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Busca todos los descuentos por coste superior al especificado y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarPorPrecioMin(double min, Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url+"/precioMin/"+min,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Busca todos los descuentos por coste inferior al especificado y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarPorPrecioMax(double max, Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url+"/precioMax/"+max,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Busca todos los descuentos por coste dentro de un rango especificado y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarPorPrecioRango(double min, double max, Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url+"/precioRang/"+min+"/"+max,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Busca todos los descuentos por tipo y coste superior al especificado y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarPorTipoYPrecioMin(String tipo, double min, Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url+"/TipoPrecioMin/"+tipo+"/"+min,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Busca todos los descuentos por tipo y coste inferior al especificado y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarPorTipoYPrecioMax(String tipo, double max, Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url+"/TipoPrecioMax/"+tipo+"/"+max,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Busca todos los descuentos por tipo y coste en un rango especificado y los muestra en paginas de tamaño especifico
    @Override
    public Page<Descuento> buscarPorTipoYPrecioRang(String tipo, double min, double max, Pageable pageable) {
        Descuento[] descuentos = template.getForObject(url+"/TipoPrecioRang/"+tipo+"/"+min+"/"+max,Descuento[].class);
        List<Descuento> descuentosList = Arrays.asList(descuentos);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Descuento> list;

        if (descuentosList.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+pageSize,descuentosList.size());
            list = descuentosList.subList(startItem,toIndex);
        }
        Page<Descuento> page = new PageImpl<>(list, PageRequest.of(currentPage,pageSize),descuentosList.size());
        return page;
    }

    //Elimina un descuento
    @Override
    public void eliminarDescuento(Integer idDescuento) {
        template.delete(url+"/"+idDescuento);
    }

    //Crea un nuevo descuento
    @Override
    public String registrarDescuento(Descuento descuento) {
        return template.postForObject(url,descuento,String.class);
    }

    //Guarda las actualizaciones en los descuentos
    @Override
    public void actualizarDescuento(Descuento descuento) {
        if (descuento.getId() != null && descuento.getId() > 0){
            template.put(url,descuento);
        }else{
            Random random = new Random();
            descuento.setId(random.nextInt(10000000,99999999));
            template.postForObject(url,descuento,String.class);
        }
    }
}
