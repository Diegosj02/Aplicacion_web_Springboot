package TFG.Front.tfgfront.model;

public class UsuarioParticipaTorneo {
    private UsuarioParticipaTorneoId id;

    private Usuario idusuario;
    private Torneo idtorneo;

    public UsuarioParticipaTorneoId getUsuarioParticipaTorneoId() {
        return id;
    }

    public void setUsuarioParticipaTorneoId(UsuarioParticipaTorneoId usuarioParticipaTorneoId) {
        this.id = usuarioParticipaTorneoId;
    }

    public UsuarioParticipaTorneo(UsuarioParticipaTorneoId usuarioParticipaTorneoId,Usuario idusuario, Torneo idtorneo) {
        this.id = usuarioParticipaTorneoId;
        this.idusuario = idusuario;
        this.idtorneo = idtorneo;
    }

    public UsuarioParticipaTorneo() {
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public Torneo getIdtorneo() {
        return idtorneo;
    }

    public void setIdtorneo(Torneo idtorneo) {
        this.idtorneo = idtorneo;
    }

    public UsuarioParticipaTorneoId getId() {
        return id;
    }

    public void setId(UsuarioParticipaTorneoId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UsuarioParticipaTorneo{" +
                "idusuario=" + idusuario +
                ", idtorneo=" + idtorneo +
                '}';
    }
}
