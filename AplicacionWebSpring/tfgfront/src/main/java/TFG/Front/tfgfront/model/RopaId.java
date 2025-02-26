package TFG.Front.tfgfront.model;

import java.io.Serializable;

public class RopaId implements Serializable {

    //Para permitir la serializaciom
    private static final long serialVersionUID = -4537416723958473324L;

    //Atributos
    private Integer idropa;
    private String talla;

    //Constructores

    public RopaId() {
    }

    public RopaId(Integer idropa, String talla) {
        this.idropa = idropa;
        this.talla = talla;
    }
    public Integer getIdropa() {
        return idropa;
    }
    public void setIdropa(Integer idropa) {
        this.idropa = idropa;
    }
    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    //ToString()
    @Override
    public String toString() {
        return "RopaId{" +
                "idropa=" + idropa +
                ", talla='" + talla + '\'' +
                '}';
    }
}
