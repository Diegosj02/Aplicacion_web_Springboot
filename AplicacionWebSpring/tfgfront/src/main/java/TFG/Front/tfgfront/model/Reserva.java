package TFG.Front.tfgfront.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    //Atributos
    private Integer id;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Cancha cancha;
    private Usuario responsable;

    //Constructores
    public Reserva(Integer id, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Cancha cancha, Usuario responsable) {
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cancha = cancha;
        this.responsable = responsable;
    }

    public Reserva() {
    }

    //Getters & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    //ToString
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", cancha=" + cancha +
                ", responsable=" + responsable +
                '}';
    }
}
