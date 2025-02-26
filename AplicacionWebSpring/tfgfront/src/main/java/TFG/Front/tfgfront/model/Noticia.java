package TFG.Front.tfgfront.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Noticia {

    //Atributos
    private Integer id;
    private String cabecera;
    private String tipo;
    private String contenido;
    private LocalDate fecha;
    private LocalTime hora;

    //Constructores
    public Noticia() {
    }

    public Noticia(Integer id, String cabecera, String tipo, String contenido, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.cabecera = cabecera;
        this.tipo = tipo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.hora = hora;
    }

    //Getters & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getContenido() {
        return contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    //ToString
    @Override
    public String toString() {
        return "Noticia{" +
                "id=" + id +
                ", cabecera='" + cabecera + '\'' +
                ", tipo='" + tipo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}
