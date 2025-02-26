package TFG.Front.tfgfront.model;

public class Ropa extends Articulo{

    //Atributos
    private RopaId id;
    private Articulo idRopa;
    private Integer cantidad;

    //Constructores
    public Ropa() {
    }
    public Ropa(RopaId id, Articulo idRopa, Integer cantidad) {
        this.id = id;
        this.idRopa = idRopa;
        this.cantidad = cantidad;
    }
    public Ropa(Integer id, Double precio, String nombre, String tipo, String imagen, RopaId id1, Articulo idRopa, Integer cantidad1) {
        super(id, precio, nombre, tipo, imagen);
        this.id = id1;
        this.idRopa = idRopa;
        this.cantidad = cantidad1;
    }

    //Getters & Setters
    public RopaId getIdPropio() {
        return id;
    }
    public void setIdPropio(RopaId id) {
        this.id = id;
    }
    public Articulo getIdRopa() {
        return idRopa;
    }
    public void setIdRopa(Articulo idRopa) {
        this.idRopa = idRopa;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    //ToString
    @Override
    public String toString() {
        return "Ropa{" +
                "id=" + id +
                ", idRopa=" + idRopa +
                ". cantidad="+cantidad+
                '}';
    }
}
