public class Cuadrante {
    private int fila;
    private int columna;
    protected boolean tieneMina;
    private boolean estaOculto = true;
    
    public Cuadrante(int f, int c) {
        fila = f;
        columna = c;
    }
    public int getFila() {
        return fila;
    }
    public int getColumna() {
        return columna;
    }
    public boolean getEstadoOculto(){
        return estaOculto;
    }
}
