
public class Libre extends Cuadrante {
    private int numero;
    private boolean tieneEscudo;
    public static final int CANTESCUDOS = 4;
    
    public Libre(int f, int c) {
        super(f, c);
        tieneMina = false;
    }
    public void setNumero(int num) {
        numero = num;
    }
    public void setEscudo(){
        tieneEscudo = true;
    }
    public int getNumero() {
        return numero;
    }
    public void disminuirCantidad() {
        numero--;
    }
    
    public boolean tieneEscudo() {
    	return tieneEscudo;
    }
}
