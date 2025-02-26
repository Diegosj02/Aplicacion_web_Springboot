package TFG.Front.tfgfront.model;

public class Tarjeta {

    //Atributos
    private Integer id;
    private Double saldo;

    //Contructores
    public Tarjeta() {
    }
    public Tarjeta(Integer id, Double saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    //Getters & Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Double getSaldo() {
        return saldo;
    }
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    //ToString
    @Override
    public String toString() {
        return "Tarjeta{" +
                "id=" + id +
                ", saldo=" + saldo +
                '}';
    }
}
