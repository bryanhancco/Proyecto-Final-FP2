import java.util.*;
public class Tablero {
    private Cuadrante[][] miTablero;
    
    public Tablero() {
        miTablero = new Cuadrante[10][12];
        generarMinas();
        generarLibres();
    }
    public Cuadrante[][] getCuadrantes(){
        return miTablero;
    }
    public void generarMinas() {
        int[][] pos = new int[10][12];
        int fila;
        int columna;
        for (int i=0; i<Mina.cantMinas; i++){
            do {
                fila = (int)(Math.random()*10);
                columna = (int)(Math.random()*12);
            } while(pos[fila][columna] != 0  || columna == 0 || columna == 11);
            pos[fila][columna] = 1;
            miTablero[fila][columna] = new Mina(fila, columna);
        }
    }
    public void generarLibres() {
        for (int i=0; i<10; i++) {
            for (int j=0; j<12; j++) {
                if (miTablero[i][j] == null){
                    //se genera un cuadrante Libre, e inmediatamente se le asigna
                    //un número (si tiene minas alrededor)
                    miTablero[i][j] = new Libre(i, j);
                    insertarNumero((Libre) miTablero[i][j]);
                }
            }
        }
    }
    public void insertarNumero(Libre cuadrante) {
        int fil = cuadrante.getFila();
        int col = cuadrante.getColumna();
        int cantidadMinas = 0;
        //el siguiente ciclo es para revisar a las posiciones circundantes
        //con la finalidad de encontrar la cantidad de minas (si es que hay)
        for (int i=fil-1; i<=fil+1; i++){
            if (i < 0 || i > 9) continue;
            for (int j=col-1; j<=col+1; j++){
                if (j < 0 || j > 11) continue;
                //no se tomara en cuenta al propio cuadrante
                //tampoco se tomara en cuenta a las posiciones aun no definidas
                if ((i == fil && j == col) || miTablero[i][j] == null) continue;
                //no se coloca else, pues es posible que existan posiciones 
                //definidas como libre
                if (miTablero[i][j] instanceof Mina){
                    cantidadMinas++;
                }
            }
        }
        if (cantidadMinas != 0){
            cuadrante.setNumero(cantidadMinas);
        }
    }
    public void generarEscudos(){
        int[][] pos = new int[10][12];
        int fila;
        int columna;
        for (int i=0; i<Libre.cantEscudos; i++){
            do {
                fila = (int)(Math.random()*10);
                columna = (int)(Math.random()*12);
            } while(pos[fila][columna] != 0 && miTablero[fila][columna] instanceof Mina  || columna == 0 || columna == 11);
            Libre lib = (Libre) miTablero[fila][columna];
            lib.setEscudo();
        }
    }
    
    public static String toKey(String c, int f) {
    	return c + f;
    }
    
    public static String toKey(int f, int c) {
    	return toKey("ABCDEFHIJKL".substring(c, c + 1), f);
    }
	public Object getCuadrantes() {
		Falta definir;
	}    
}
