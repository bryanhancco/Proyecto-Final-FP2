
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
    public double puntajeFinal() {
        double puntajeFinal = 0.0;
        if (haGanado) 
            puntajeFinal += 500.0;
        puntajeFinal += cantSoldados*25.0;
        puntajeFinal = (puntajeFinal*(100.0-turnosUtilizados*(0.1)))/100.0;
        
        return puntajeFinal;
    }
    public static void actualizarDatos(Jugador j1, Jugador j2){
        ObjectOutputStream fileOut;
        try {
            fileOut = new ObjectOutputStream(new FileOutputStream("ultimaPartida.dat"));
            fileOut.writeObject(j1);
            fileOut.writeObject(j2);
            fileOut.close();
        } 
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }   
    public static Jugador[] obtenerDatosJugador () {
        Jugador[] jugadores = new Jugador[2];
        ObjectInputStream fileIn;
        try {
            fileIn = new ObjectInputStream(new FileInputStream("ultimaPartida.dat"));
            jugadores[0] = (Jugador) fileIn.readObject();
            jugadores[1]  = (Jugador) fileIn.readObject();
            fileIn.close();
        } 
        catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound: " + e.getMessage());
        }
        return jugadores;
    }
}

