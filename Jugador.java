

import java.awt.*;
import java.io.*;
public class Jugador implements Serializable {
    private int cantSoldados;
    private Color colorEscogido;
    private String nombreJugador;
    private String nombreReino;
    private boolean haGanado;
    private int turnosUtilizados;
    
    public Jugador(Color c, String nombreJ, String nombreR){
        cantSoldados = 10;
        haGanado = false;
        colorEscogido = c;
        nombreJugador = nombreJ;
        nombreReino = nombreR;
        turnosUtilizados = 0;
    }
   
    public String getNombre() {
        return nombreJugador;
    }
    public String getReino() {
        return nombreReino;
    }
    public int getCantSoldados() {
        return cantSoldados;
    }
    public Color getColor() {
        return colorEscogido;
    }
    public String getEstado() {
        if (haGanado)
            return "Sí";
        return "No";
    }
    public int cantTurnos() {
        return turnosUtilizados;
    }
    public void eliminarSoldado() {
        cantSoldados--;
    }
    public void aumentarTurno() {
        turnosUtilizados++;
    }
    public void haGanado() {
        haGanado = true;
    }
}
