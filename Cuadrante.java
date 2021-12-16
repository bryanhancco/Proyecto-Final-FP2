public class Cuadrante {
    private int fila;
    private int columna;
    protected boolean tieneMina;
    private boolean estaOculto = false;
    
    public Cuadrante(int f, int c) {
        fila = f;
        columna = c;
    }
    
    public void setFila(int fila) {
        this.fila = fila;
    }
    
    public void setColumna(int columna) {
        this.columna = columna;
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

