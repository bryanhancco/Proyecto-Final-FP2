import java.util.*;
public class Game {
    private Tablero miTablero;
    private Ejercito ejer1;
    private Ejercito ejer2;
    private String [] datos = {"Plano","Jugador 1","Jugador2"};
    
    //constructor
    public Game() {
        miTablero = new Tablero();
        ejer1 = new Ejercito(1, 1);
        ejer2 = new Ejercito(12, 2);
    }
    
    //Inicia todo el juego
    public void iniciarJuego() {
        while(hayJuego()) {        	
            mover(1);
            if(hayJuego())
            mover(2);
        }
        System.out.println("\n---> ¡JUEGO TERMINADO!");
    }
    
    // Verifica si el juego se termino
    public boolean hayJuego() {
    	return (!ejer1.getSoldados().isEmpty() && !ejer2.getSoldados().isEmpty() 
    			&& ejer1.getTorre().enPie() && ejer2.getTorre().enPie());
    }
    
    //Imprime el tablero de juego
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
    
    /// TABLERO CON FORMATO    
	public void mostrarTablero() {
		String pos1= getLstTeam(1).get(0).getUbicacion();
		String pos2= getLstTeam(2).get(0).getUbicacion();
		System.out.print("////////////////////////////////////////////////////////////"
				+ "////////// \n\t\t\tMOSTRANDO EL TABLERO\n"
				+ "\n\t    A   B   C   D   E   F   G   H   I   J   K   L\n"
				+ "\t   -------------------------------------------------\n");
		String k;
		for(int f= 1; f<= 10; f++) {
			if (f< 10)
				System.out.print("\t");
			else
				System.out.print("       ");
			System.out.print(f + " |");
			for(int c=1; c<= 12; c++) {
				k= Tablero.toKey(f, c);
				if (miTablero.esMina(k))
					System.out.print(" * ");
				else if (k.equals(pos1))
					System.out.print(" & ");
				else if (k.equals(pos2))
					System.out.print(" ¥ ");				
				else
					System.out.print("   ");
				System.out.print("|");
			}
			System.out.print("\n\t  -------------------------------------------------\n");
		}
		System.out.println("\n---> Reino N°1: " + datos[1] + " (&)"
				+ "\n---> Reino N°2: " + datos[2] + " (¥)");
	}
    
    //devuelve la lista ordenda segÃºn el equipo
    public ArrayList<Soldado> getLstTeam(int team){
        if (team == 1)
            return ejer1.getLstOrdenada();
        else
            return ejer2.getLstOrdenada();
    }
    
    //devuelve la lista completa de soldados de ambos ejercitos
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
    
    //permite mover al soldado de al soldado 
    public void mover(int team) {
		Scanner sc= new Scanner(System.in);
		mostrarTablero();
		String ub, k;
		System.out.println("\n- Soldados restantes en el Ejército N°1: " + getLstTeam(1).size()
				+ "\n- Vida de la Torre 1: " + getEjercito(1).getTorre().getVidaTorre()
				+ "\n\n- Soldados restantes en el Ejército N°2: " + getLstTeam(2).size()
				+ "\n- Vida de la Torre 2: " + getEjercito(2).getTorre().getVidaTorre()
				+ "\nTURNO DEL JUGADOR NÂ°" + team 
				+ "\n***" + getLstTeam(team).get(0).getNombre() + "  ");
		ub = getLstTeam(team).get(0).getUbicacion(); 
		int aux = 1;
		ArrayList<String> movValidos = new ArrayList<String>(); 
        if (team == 2)
            aux = -1;
        //muestra las posiciones válidas para el movimiento del soldado
        for(int j = -1; j <= 1; j++) {
        	System.out.print("\t\t\t");
            k = Tablero.toKey(getLista().get(ub).getNfila() + j, getLista().get(ub).getNcolumna() + aux);				
                if(k.equals("-"))
                    System.out.print(" X");
                else {
                	movValidos.add(k);
                    System.out.print(k);
                }
            System.out.print("\n");	
        }
        
	    while(true){
			System.out.println("- ¡Tiene " + miTablero.getCuadrante(ub).getNumero() + " minas alrededor!");
			System.out.print("---> Ingrese la posición indicada: ");
		    k = sc.next();
		    if(movValidos.contains(k))
		    	break;
		    System.out.print("---> ¡Movimiento no permitido!\n\n");
	    }
	    
	    if(!getLista().containsKey(k)) {    	
	    	getEjercito(team).moverSoldado(ub, k);    	
	        if(miTablero.esMina(k))
	            ActivarMina(team, k);        
		}
		else {
			lucha(ub, k);
		}
	    
	    if (getLista().containsKey(k)) { 
		    if(getLista().get(k).getNcolumna() == 12 || getLista().get(k).getNcolumna() == 1) {
		    	if(getLista().get(k).getNcolumna() == 12 && team ==1)
		    		atacarTorre(team);
		    	if(getLista().get(k).getNcolumna() == 1 && team ==2)
		    		atacarTorre(team);
		    	getEjercito(team).retirarSoldado(k);
		    	int team2 = 2;
		    	if (team == 2)
		    		team2= 1;
		        if(getEjercito(team2).getTorre().getVidaTorre() == 0)
		        	getEjercito(team2).getTorre().torreDestruida();
		    }
	    }
		System.out.print("\n");
    }
    
    //imprime un mensaje y elimina al soldado que piso una mina
    public void ActivarMina(int team, String ub) {
        System.out.println("PISASTE UNA BOMBA");
        getEjercito(team).retirarSoldado(ub);
        miTablero.quitarMina(ub);
    }
    
    public void lucha(String ub1, String ub2) {
    	int num= (int)(Math.random() * 2);
    	int team1= getLista().get(ub1).getTeam();
    	int team2= getLista().get(ub2).getTeam();
    	if (num == 1) {
    		System.out.println("---> Gana " + getLista().get(ub1).getNombre());
    		getEjercito(team2).retirarSoldado(ub2);
    		getEjercito(team1).moverSoldado(ub1, ub2);
    	}
    	else {
    		System.out.println("---> Gana " + getLista().get(ub2).getNombre());
    		getEjercito(team1).retirarSoldado(ub1);    		
    	}
    }
    
    public void atacarTorre(int team) {
        if (team== 1) {
        	System.out.println("---> ¡La torre 2 ha sido atacada!");
        	getEjercito(2).getTorre().torreAtacada();        	
        }
        else {

        	System.out.println("---> ¡La torre 1 ha sido atacada!");
        	getEjercito(1).getTorre().torreAtacada();
        }
    }
}
