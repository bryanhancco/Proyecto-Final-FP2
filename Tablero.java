
public class Tablero {
    private Cuadrante[][] miTablero;
    
    public void generarMinas() {
        int[][] pos = new int[10][10];
        int fila;
        int columna;
        for (int i=0; i<Mina.cantMinas; i++){
            do {
                fila = (int)(Math.random()*10);
                columna = (int)(Math.random()*10);
            } while(pos[fila][columna] != 0);
            miTablero[fila][columna] = new Mina(fila, columna);
        }
    }
}
