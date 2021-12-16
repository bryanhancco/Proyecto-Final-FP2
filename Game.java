import java.util.*;
public class Game {
    private Tablero miTablero;
    private Ejercito ejer1;
    private Ejercito ejer2;
    private String [] datos = {"Plano","Jugador 1","Jugador2"};
    
    public Game() {
        miTablero = new Tablero();
        ejer1 = new Ejercito(1, 1);
        ejer2 = new Ejercito(12, 2);
    }
    public void iniciarJuego() {  
        do {        	
            mover(1);
            mover(2);
        } while (!ejer1.getSoldados().isEmpty() && !ejer2.getSoldados().isEmpty());
    }
    public void imprimirTablero(String pos1, String pos2){
        System.out.println("\n \tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ\tK\tL");
        for (int i=0; i<miTablero.getCuadrantes().length; i++){
            System.out.print("\n"+(i+1)+"\t");
            for (int j=0; j<miTablero.getCuadrantes()[0].length; j++){
                if (miTablero.getCuadrantes()[i][j] == null) continue;
                if (Tablero.toKey(i+1, j+1).equals(pos1) || Tablero.toKey(i+1, j+1).equals(pos2)) 
                    System.out.print("S\t");   
                else if (miTablero.getCuadrantes()[i][j].getEstadoOculto())
                    System.out.print("-\t");
                else {
                    if (miTablero.getCuadrantes()[i][j] instanceof Mina)
                        System.out.print("*\t");
                    else 
                        System.out.print("-\t");
                }
            }
        }  
    }
    
	public ArrayList<Soldado> getLstTeam(int team){
		if (team == 1)
			return ejer1.getLstOrdenada();
		else
			return ejer2.getLstOrdenada();
	}
	
	public HashMap<String, Soldado> getLista(){
		HashMap<String, Soldado> lst= new HashMap<String, Soldado>();
		for (Soldado s: ejer1.getLstOrdenada())
			lst.put(s.getUbicacion(), s);
		for (Soldado s: ejer2.getLstOrdenada())
			lst.put(s.getUbicacion(), s);
		return lst;
	}
	
	public Ejercito getEjercito(int team) {
		if (team == 1)
			return ejer1;
		else
			return ejer2;
	}
    
    public void mover(int team) {
		Scanner sc= new Scanner(System.in);
		imprimirTablero(getLstTeam(1).get(0).getUbicacion(), getLstTeam(2).get(0).getUbicacion());
		String ub, k;
		System.out.print("- Soldados restantes en el EjÈrcito N∞1: " + getLstTeam(1).size()
				+ "\n- Soldados restantes en el EjÈrcito N∞2: " + getLstTeam(2).size() 
				+ "\nTURNO DEL JUGADOR N∞" + team + "\n\n");
		ub= getLstTeam(team).get(0).getUbicacion(); // Solo para la primera
		int aux= 1;
		if (team == 2)
			aux= -1;
		////////
		for(int j= -1; j<= 1; j++) {
			//System.out.println(getLista().get(ub).getNfila()+ " ----" +getLista().get(ub).getNcolumna());
			k= Tablero.toKey(getLista().get(ub).getNfila() + j, getLista().get(ub).getNcolumna() + aux);
			//System.out.print( j +"***" + k);				
			if(k.equals("-"))
				System.out.print(" X ");
			else
				System.out.print(k);
			System.out.print("\n");	
		}					
	
		System.out.print("- Ingrese la posiciÛn indicada: ");
		k= sc.next();		
		if(getLista().containsKey(k) == false) {
			if(miTablero.esMina(k))
				bomba(team, ub);
			else
				getLista().get(ub).setUbicacion(k);
		}
		else {
			System.out.println("Son de diferente equipo");
		}
		System.out.print("\n");
	}
    
    private void bomba(int team, String ub) {
		System.out.println("PISASTE UNA BOMBA");
		getEjercito(team).getSoldados().remove(ub);
	}
	public static ArrayList<String> movimientosValidos(String posSoldado) {
    	System.out.println("***" + posSoldado);
        ArrayList<String> movValidas = new ArrayList<String>();
        int fil = Integer.parseInt(posSoldado.substring(0, 1));
        int col = posSoldado.substring(1).compareTo("A");
        // fil = 1
        System.out.println("2.- **" + fil + "  " + col);
        for (int i=fil-1; i<=fil+1; i++){
            if (i < 1 || i > 10) continue;
            for (int j=col-1; j<=col+1; j++){
                if (j < 1 || j > 12) continue;
                if (i == fil && j == col) continue; 
                movValidas.add(Tablero.toKey(i, j));
            }
        }
        return movValidas;
    }
    /*public void jugarPorTurnos(Ejercito ejerJug, Ejercito ejerEnem, Tablero miT){
        Scanner scan = new Scanner(System.in);
        int cont = 0;
        String pInicial = "", pMovimiento, nomReino;
        int fila; 
        String columna;
        
        for (String key : ejerJug.getSoldados().keySet()){
            if (cont == 0){
                System.out.println(key);
                pInicial = key;
                cont++;
            }
        }
        
        imprimirTablero(pInicial);
        System.out.println("\nA que posici√≥n quiere moverlo");
        System.out.println("Fila");
        fila = scan.nextInt();
        System.out.println("Columna");
        columna = scan.next();
        pMovimiento = fila+columna;
        for (String p : movimientosValidos(pInicial)){
            System.out.println(p);
        }
        while(!movimientosValidos(pInicial).contains(pMovimiento)){
            System.out.println("No es posible moverlo a esa ubicaci√≥n");
            System.out.println("A que posici√≥n quiere moverlo");
            System.out.println("Fila");
            fila = scan.nextInt();
            System.out.println("Columna");
            columna = scan.next();
            pMovimiento = fila+columna;
        }
        mover(pInicial, miT, pMovimiento, ejerJug.getSoldados());
    }*/
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

