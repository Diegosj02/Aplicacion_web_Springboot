package TFG.Front.tfgfront.model;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contraseña;
    private String telefono;
    private int puntos;
    private int puntosT;

    public Usuario(int id, String nombre, String correo, String contraseña, String telefono, int puntos, int puntosT) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.puntos = puntos;
        this.puntosT = puntosT;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntosT() {
        return puntosT;
    }

    public void setPuntosT(int puntosT) {
        this.puntosT = puntosT;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", telefono='" + telefono + '\'' +
                ", puntos=" + puntos +
                ", puntosT=" + puntosT +
                '}';
    }
}
