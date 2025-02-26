package TFG.Front.tfgfront.model;

public class Articulo {

    //Atributos
    private Integer id;
    private Double precio;
    private String nombre;
    private String tipo;
    private String imagen;

    //Constructores
    public Articulo() {
    }

    public Articulo(Integer id, Double precio, String nombre, String tipo,String imagen) {
        this.id = id;
        this.precio = precio;
        this.nombre = nombre;
        this.tipo = tipo;
        this.imagen = imagen;
    }

    //Getters & Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        return "Articulo{" +
                "id=" + id +
                ", precio=" + precio +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
