import java.util.*;
public class Game {
    private Tablero miTablero;
    private Ejercito ejer1;
    private Ejercito ejer2;
    
    public Game() {
        miTablero = new Tablero();
        ejer1 = new Ejercito(0, 1);
        ejer2 = new Ejercito(11, 2);
        jugarPorTurnos(ejer1, ejer2, miTablero);
    }
    public void iniciarJuego() {
        Scanner sc= new Scanner(System.in);
        String conf;
        do {
        	imprimirTablero();
        	System.out.print("¿Desea iniciar otro juego?(S/N): ");
        	conf= sc.next();
        }
        while(conf.equals("S"));
    }
    public void imprimirTablero(String pos){
        System.out.println("\n \tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ\tK\tL");
        for (int i=0; i<miTablero.getCuadrantes().length; i++){
            System.out.print("\n"+(i+1)+"\t");
            for (int j=0; j<miTablero.getCuadrantes()[0].length; j++){
                if (miTablero.getCuadrantes()[i][j].equals(pos)) 
                    System.out.print("S\t");     
                else if (miTablero.getCuadrantes()[i][j].getEstadoOculto())
                    System.out.print("-\t");
                else {
                    if (miTablero.getCuadrantes()[i][j] instanceof Mina)
                        System.out.print("*\t");
                    else 
                        System.out.println("_\t");    
                }
            }
        }  
    }
    public static ArrayList<String> movimientosValidos(String posSoldado) {
        ArrayList<String> movValidas = new ArrayList<String>();
        int fil = Integer.parseInt(posSoldado.substring(0, 1));
        int col = posSoldado.substring(1).compareTo("A");
        for (int i=fil-1; i<=fil+1; i++){
            if (i < 0 || i > 9) continue;
            for (int j=col-1; j<=col+1; j++){
                if (j < 0 || j > 11) continue;
                if (i == fil && j == col) continue; 
                movValidas.add(fil+"ABCDEFGHIJKL".substring(col, col+1));
            }
        }
        return movValidas;
    }
    public static void jugarPorTurnos(Ejercito ejerJug, Ejercito ejerEnem, Tablero miT){
        Scanner scan = new Scanner(System.in);
        int cont = 0;
        String pInicial = "", pMovimiento, nomReino;
        int fila, columna;
        for (String key : ejerJug.getSoldados().keySet()){
            if (cont == 0){
                pInicial = key;
                cont++;
            }
        }
        System.out.println("A que posición quiere moverlo");
        System.out.println("Fila");
        fila = scan.nextInt();
        System.out.println("Columna");
        columna = scan.nextInt();
        pMovimiento = fila+"ABCDEFGHIJKL".substring(columna, columna+1);
        while(!movimientosValidos(pInicial).contains(pMovimiento)){
            System.out.println("No es posible moverlo a esa ubicación");
            System.out.println("A que posición quiere moverlo");
            System.out.println("Fila");
            fila = scan.nextInt();
            System.out.println("Columna");
            columna = scan.nextInt();
        }
        mover(pInicial, miT, pMovimiento, ejerJug.getSoldados());
    }
    public static void mover(String posInicial, Tablero miTablero, String posMovimiento, HashMap<String, Soldado> misSoldados) {
        int filaI = Integer.parseInt(posInicial.substring(0, 1));
        int colI = posInicial.substring(1).compareTo("A");
        int filaM = Integer.parseInt(posMovimiento.substring(0, 1));
        int colM = posMovimiento.substring(1).compareTo("A");
        
        if (miTablero.getCuadrantes()[filaM][colM] instanceof Mina) {
            System.out.println("El soldado ha muerto, ha pisado una mina");
            misSoldados.remove(posInicial);
        }
        else if (miTablero.getCuadrantes()[filaM][colM] instanceof Libre) {
            System.out.println("El soldado se ha movido");
            miTablero.getCuadrantes()[filaI][colI].setFila(filaM);
            miTablero.getCuadrantes()[filaI][colI].setColumna(colM);
        }
    }
}
