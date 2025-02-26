package TFG.Front.tfgfront.model;

public class Cancha {

    //Atributos
    private Integer id;
    private String deporte;
    private Double precio;
    private String imagen;

    //Constructores
    public Cancha(Integer id, String deporte, Double precio, String imagen) {
        this.id = id;
        this.deporte = deporte;
        this.precio = precio;
        this.imagen = imagen;
    }

    public Cancha() {
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    //ToString
    @Override
    public String toString() {
        return "Cancha{" +
                "id=" + id +
                ", deporte='" + deporte + '\'' +
                ", precio=" + precio +
                '}';
    }
}
