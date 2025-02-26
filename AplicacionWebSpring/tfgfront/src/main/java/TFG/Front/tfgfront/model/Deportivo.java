package TFG.Front.tfgfront.model;

public class Deportivo extends Articulo{

    //Atrubutos
    private Integer id;
    private String deporte;
    private Integer cantidad;

    private Articulo articulo;

    //Constructores
    public Deportivo() {
    }

    public Deportivo(Integer id, String deporte, Integer cantidad,Articulo articulo) {
        this.id = id;
        this.deporte = deporte;
        this.cantidad = cantidad;
        this.articulo = articulo;
    }

    public Deportivo(Integer id, Double precio, String nombre, String tipo, String imagen, Integer id1, String deporte, Integer cantidad,Articulo articulo) {
        super(id, precio, nombre, tipo, imagen);
        this.id = id1;
        this.deporte = deporte;
        this.cantidad = cantidad;
        this.articulo = articulo;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    //ToString
    @Override
    public String toString() {
        return "Deportivo{" +
                "id=" + id +
                ", deporte='" + deporte + '\'' +
                ", cantidad=" + cantidad +
                ", articulo=" + articulo +
                '}';
    }
}
