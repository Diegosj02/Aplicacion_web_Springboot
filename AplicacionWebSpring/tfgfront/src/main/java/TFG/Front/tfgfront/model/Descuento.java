package TFG.Front.tfgfront.model;

public class Descuento {
    private Integer id;
    private String tipo;
    private int valor;
    private int coste;
    private Articulo articulo;
    private Usuario usuario;
    private String imagen;

    public Descuento() {
    }

    public Descuento(int id, String tipo, int valor, int coste, Articulo articulo, Usuario usuario,String imagen) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.coste = coste;
        this.articulo = articulo;
        this.usuario = usuario;
        this.imagen = imagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getCoste() {
        return coste;
    }

    public void setCoste(int coste) {
        this.coste = coste;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getImagen(){
        return imagen;}
    public void setImagen(String imagen){this.imagen=imagen;}

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Descuento{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", coste=" + coste +
                ", articulo=" + articulo.toString() +
                ", usuario=" + usuario.toString() +
                ", imagen" + imagen+
                '}';
    }
}
