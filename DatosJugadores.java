
import java.io.*;
public class DatosJugadores { 
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
