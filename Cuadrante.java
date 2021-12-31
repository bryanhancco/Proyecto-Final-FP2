import java.util.*;
public class Cuadrante {
    private int fila;
    private int columna;
    protected boolean tieneMina;
    private boolean estaOculto = true;
    
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
    public void cambiarEstado() {
        estaOculto = false;
    }
    public boolean getEstado() {
        return estaOculto;
    }
    public int getFila() {
        return fila;
    } 
    public int getColumna() {
        return columna;
    }
    public int getNumero() {
        return 0;
    }
    public boolean tieneEscudo() {
        return false;
    }
}
