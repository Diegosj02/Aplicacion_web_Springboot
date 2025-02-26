package TFG.Front.tfgfront.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Torneo {

    //Atributos
    private Integer id;
    private Noticia noticia;
    private String deporte;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer participantes;
    private Double premio;
    private Double coste;
    private Cancha cancha;

    //Constructores
    public Torneo(Integer id, String deporte, LocalDate fecha, LocalTime hora, Integer participantes, Double premio, Double coste, Cancha cancha, Noticia noticia) {
        this.id = id;
        this.deporte = deporte;
        this.fecha = fecha;
        this.hora = hora;
        this.participantes = participantes;
        this.premio = premio;
        this.coste = coste;
        this.cancha = cancha;
        this.noticia = noticia;
    }
    public Torneo() {
    }



    //Getters & Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDeporte() {
        return deporte;
    }
    public void setDeporte(String deporte) {
        this.deporte = deporte;
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
    public Integer getParticipantes() {
        return participantes;
    }
    public Double getPremio() {
        return premio;
    }
    public Double getCoste() {
        return coste;
    }
    public Cancha getCancha() {
        return cancha;
    }
    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }
    public Noticia getNoticia() {
        return noticia;
    }
    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }

    public void setParticipantes(Integer participantes) {
        this.participantes = participantes;
    }

    public void setPremio(Double premio) {
        this.premio = premio;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    //ToString
    @Override
    public String toString() {
        return "Torneo{" +
                "id=" + id +
                ", deporte='" + deporte + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", participantes=" + participantes +
                ", premio=" + premio +
                ", coste=" + coste +
                ", cancha=" + cancha +
                ", noticia=" + noticia +
                '}';
    }
}
