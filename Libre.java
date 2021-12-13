
public class Libre extends Cuadrante {
    private int numero;

    public Libre(int f, int c) {
        super(f, c);
        tieneMina = false;
    }
    public void setNumero(int num) {
        numero = num;
    }
    public int getNumero() {
        return numero;
    }
}
