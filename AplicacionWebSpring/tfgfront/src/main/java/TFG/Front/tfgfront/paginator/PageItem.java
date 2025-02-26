package TFG.Front.tfgfront.paginator;

public class PageItem {

    //Atributos
    private int numero;
    private boolean actual;

    //Constructor
    public PageItem(int numero, boolean actual) {
        this.numero = numero;
        this.actual = actual;
    }

    //Getters & Setters
    public int getNumero() {
        return numero;
    }

    public boolean isActual() {
        return actual;
    }
}
