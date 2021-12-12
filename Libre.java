
public class Libre extends Cuadrante {
    private int numero;
    
    public Libre(int f, int c) {
        super(f, c);
        tieneMina = false;
    }
    public Libre(int f, int c, int num) {
        super(f, c);
        tieneMina = false;
        numero = num;
    }
    public int getNumero() {
        return numero;
    }
}
