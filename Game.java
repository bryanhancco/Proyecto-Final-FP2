import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Game extends JFrame {
    private Tablero miTablero;
    private Ejercito ejer1;
    private Ejercito ejer2;
    private String [] datos = {"Plano","Jugador 1","Jugador2"};
    private static final int ANCHO = 960;
    private static final int ALTO = 800;
    private JButton[][] buttons;
    
    public Game() {
        miTablero = new Tablero();
        ejer1 = new Ejercito(1, 1);
        ejer2 = new Ejercito(12, 2);
        JOptionPane.showMessageDialog(null, "Bienvenido, usted va a pasarlo muy bien");
        setTitle("Videojuego");
        setSize(ANCHO, ALTO);
        setLayout(new GridLayout(10,12));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        setVisible(true);        
    }
   public void createContents() {     
       buttons = new JButton[10][12];
       for (int i=0; i<10; i++){
           for (int j=0; j<12; j++) {             
               buttons[i][j] = new JButton();
               if (j == 0 || j == 11) {
                   buttons[i][j].setBackground(Color.GREEN);
               }
               add(buttons[i][j]);
               buttons[i][j].addActionListener(new mensajeInicial());
           }
       }              
   }
   private class mensajeInicial implements ActionListener {
       public void actionPerformed(ActionEvent e) {              
           JOptionPane.showMessageDialog(null, "\n- Soldados restantes en el Ejército N°1: " + getLstTeam(1).size()
				+ "\n- Vida de la Torre 1: " + getEjercito(1).getTorre().getVidaTorre()
				+ "\n\n- Soldados restantes en el Ejército N°2: " + getLstTeam(2).size()
				+ "\n- Vida de la Torre 2: " + getEjercito(2).getTorre().getVidaTorre()
				+ "\nTURNO DEL JUGADOR NÂ° 1" 
				+ "\n***" + getLstTeam(1).get(0).getNombre());
           
       }
   }  
   private class hacerMovimiento implements ActionListener {
       public void actionPerformed(ActionEvent e) {              

       }
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
		String ub, k;
		System.out.println();
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
