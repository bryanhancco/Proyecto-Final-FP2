
import java.io.*;
public class DatosJugadores {
    private ObjectOutputStream fileOut;
    private ObjectInputStream fileIn;
    private Jugador jug1;
    private Jugador jug2;
    
    public void actualizarDatos(Jugador j1, Jugador j2){
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
    public Jugador[] obtenerDatosJugador () {
        Jugador[] jugadores = new Jugador[2];
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
