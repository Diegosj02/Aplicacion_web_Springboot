package TFG.Front.tfgfront.model;

public class Material {
    private Integer id;
    private String nombre;
    private Double precio;
    private String deporte;
    private String imagen;
    private Integer cantidad;
    public Material() {
    }

    public Material(int idMaterial, String nombre, double precio, String deporte,String imagen, int cantidad) {
        this.id= idMaterial;
        this.nombre = nombre;
        this.precio = precio;
        this.deporte = deporte;
        this.imagen = imagen;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getImagen(){
        return imagen;
    }
    public void setImagen(String imagen)
    {this.imagen=imagen;}
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Material{" +
                "idMaterial=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", deporte='" + deporte + '\'' +
                ", imagen='" +imagen + '\''+
                '}';
    }
}
