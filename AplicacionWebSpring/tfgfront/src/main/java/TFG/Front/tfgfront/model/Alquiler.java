package TFG.Front.tfgfront.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Alquiler {

    //Atributos
    private Integer id;
    private LocalDate fecha;
    private LocalTime hora;
    private Material material;
    private Usuario responsable;

    //Constructores
    public Alquiler() {
    }
    public Alquiler(Integer id, LocalDate fecha, LocalTime hora, Material material, Usuario responsable) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.material = material;
        this.responsable = responsable;
    }

    //Getters & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    //ToString
    @Override
    public String toString() {
        return "Alquiler{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", material=" + material +
                ", responsable=" + responsable +
                '}';
    }
}
