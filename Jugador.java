import java.util.*;
import java.awt.*;
import java.io.*;
public class Jugador implements Serializable {
    private ArrayList<String> ubicaciones;
    private Color colorEscogido;
    private String nombreJugador;
    private String nombreReino;
    private boolean haGanado;
    private int turnosUtilizados;
    
    public Jugador(Color c, String nombreJ, String nombreR){
        haGanado = false;
        colorEscogido = c;
        nombreJugador = nombreJ;
        nombreReino = nombreR;
        turnosUtilizados = 0;
    }
    public void eliminarJugador() {
        ubicaciones.remove(0);
    }
    public void aumentarTurno() {
        turnosUtilizados++;
    }
    public void haGanado() {
        haGanado = true;
    }
}
