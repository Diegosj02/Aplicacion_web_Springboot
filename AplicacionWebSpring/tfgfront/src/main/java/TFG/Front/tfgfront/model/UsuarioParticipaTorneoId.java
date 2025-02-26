package TFG.Front.tfgfront.model;

import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioParticipaTorneoId implements Serializable {

    private static final long serialVersionUID = 8864753793175940811L;

    private Integer idusuario;

    private Integer idtorneo;

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdtorneo() {
        return idtorneo;
    }

    public void setIdtorneo(Integer idtorneo) {
        this.idtorneo = idtorneo;
    }

    public UsuarioParticipaTorneoId() {
    }

    public UsuarioParticipaTorneoId(Integer idusuario, Integer idtorneo) {
        this.idusuario = idusuario;
        this.idtorneo = idtorneo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsuarioParticipaTorneoId entity = (UsuarioParticipaTorneoId) o;
        return Objects.equals(this.idtorneo, entity.idtorneo) &&
                Objects.equals(this.idusuario, entity.idusuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtorneo, idusuario);
    }

    @Override
    public String toString() {
        return "UsuarioParticipaTorneoId{" +
                "idusuario=" + idusuario +
                ", idtorneo=" + idtorneo +
                '}';
    }
}
