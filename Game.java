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
    private String texto= "";
    private int fAux, cAux, turno = 1;
    private boolean hacerMovimiento;
    // c
    public Game() {
        miTablero = new Tablero();
        ejer1 = new Ejercito(1, 1);
        ejer2 = new Ejercito(12, 2);
        //JOptionPane.showMessageDialog(null, "Bienvenido, usted va a pasarlo muy bien");
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
               buttons[i][j] = new JButton("");
               if (j == 0 || j == 11) {
                    buttons[i][j].setBackground(Color.GREEN);
                    buttons[i][j].setText(getLista().get(Tablero.toKey(i + 1,j + 1)).getName());
               }
               //agregue esta condicional para que los demÃ¡s cuadrantes quedasen blancos
               else {
                    buttons[i][j].setBackground(Color.WHITE);
                    if (miTablero.getCuadrantes()[i][j].tieneMina)
                         buttons[i][j].setText("*");
                    //else if (miTablero.getCuadrantes()[i][j] instanceof Libre && miTablero.getCuadrantes()[i][j].getNumero() != 0) {
                      //  if (j == 0 || j == 11) continue;
                        //buttons[i][j].setText(""+miTablero.getCuadrantes()[i][j].getNumero());
                    //}
               }
               buttons[i][j].addActionListener(new acciones());
               add(buttons[i][j]);
           }
       }
   }
   
   public void Mensaje() {
       JOptionPane.showMessageDialog(null, "Soldados restantes en el EjÃ©rcito NÂ°1: " + getLstTeam(1).size()
			+ "\nVida de la Torre 1: " + getEjercito(1).getTorre().getVidaTorre()
			+ "\nSoldados restantes en el EjÃ©rcito NÂ°2: " + getLstTeam(2).size()
			+ "\nVida de la Torre 2: " + getEjercito(2).getTorre().getVidaTorre()
			+ "\nTURNO DEL JUGADOR NÃ‚Â° 1" 
			+ "\n" + getLstTeam(1).get(0).getNombre());
   }
 //elimine el otro listener, este hace ambas funciones (en base al valor que tenga hacerMovimiento)
    private class acciones implements ActionListener {
        public void actionPerformed(ActionEvent e) {              
            for (int f= 0; f< buttons.length; f++) {
                for(int c= 0; c< buttons[0].length; c++) {
                    if(e.getSource() == buttons[f][c]) {
                        if (hacerMovimiento)
                            movimiento(buttons[f][c], f, c);
                        else
                            seleccionarSoldado(f, c);
                    }
                }
            }
        }
    }
    public void seleccionarSoldado(int f, int c) {
        //solucion temporal, se puede mejorar
    	int team= (turno+1)%2 + 1;
    	Soldado selec= getLista().get(Tablero.toKey(f + 1,c + 1));
        if(selec != null && getLstTeam(team).get(0) == selec) {
	       texto= buttons[f][c].getText();
	        fAux= f;
	        cAux= c;
	        cambiarColor(f,c, Color.RED);
	        JOptionPane.showMessageDialog(null, "¡Tiene " + miTablero.getCuadrantes()[f][c].getNumero()+ " minas cerca!");
	        hacerMovimiento = true; 
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione el soldado disponible");  
   }    
    public JButton boton(String ub) {
    	int large = ub.length();
        String columna = ub.substring(large - 1);
        int col = columna.compareTo("A");
        int fila = Integer.parseInt(ub.substring(0, ub.length() - 1)) -1;
    	return buttons[fila][col];
    }    
    //cambia la ubicacion del soldado, tanto en el tablero, como en el HashMap
    public void movimiento(JButton b, int f, int c) {
    	int team= (turno+1)%2 + 1;
        buttons[fAux][cAux].setText("");
        buttons[f][c].setText(texto);
        getLista().get(Tablero.toKey(fAux + 1,cAux + 1)).setUbicacion(Tablero.toKey(f + 1, c +1));
                //Definir Color
        cambiarColor(fAux, cAux, new Color(255,255,255));
        getEjercito(team).moverSoldado(Tablero.toKey(fAux + 1, cAux + 1),Tablero.toKey(f + 1, c + 1));       
        
        if (miTablero.getCuadrantes()[f][c] instanceof Mina) {
        	JOptionPane.showMessageDialog(null, "¡Pisaste una mina!");
        	miTablero.getCuadrantes()[f][c]= new Libre(f,c);
        	buttons[f][c].setText("");
        	getEjercito(team).getSoldados().remove(Tablero.toKey(f + 1, c + 1));
        	//el siguiente ciclo reducira el numero correspondiente, de todos los
                //casilleros que circunden a la posicion de la mina
                for (int i=f-1; i<=f+1; i++){
                    if (i < 0 || i > 9) continue;
                    for (int j=c-1; j<=c+1; j++){
                        if (j < 0 || j > 11) continue;
                        if (i == f && j == c) continue;
                        if (miTablero.getCuadrantes()[i][j] instanceof Libre){
                            Libre cNum = (Libre) miTablero.getCuadrantes()[i][j];
                            cNum.disminuirCantidad();
                            if (miTablero.getCuadrantes()[i][j].getNumero() == 0)
                                buttons[i][j].setText("");
                            else
                                buttons[i][j].setText(""+miTablero.getCuadrantes()[i][j].getNumero());
                        }
                    }
                }
            }
        else 
            descubrirNumeros(f, c);
        /*else if(c == 11 && turno % 2 != 0)
            JOptionPane.showMessageDialog(null, "Torre 2 Atacada");
        else if(c== 0 && turno % 2 == 0)
            JOptionPane.showMessageDialog(null, "Torre 1 Atacada");*/
        Mensaje();
        turno++;
        hacerMovimiento = false;        
    }
    public void descubrirNumeros(int f, int c) {
        if (miTablero.getCuadrantes()[f][c].getNumero() == 0) {
            for (int i=f-1; i<=f+1; i++){
                if (i < 0 || i > 9) continue;
                for (int j=c-1; j<=c+1; j++){
                    if (j < 0 || j > 11) continue;
                    if (i == f && j == c) continue;
                    if (miTablero.getCuadrantes()[i][j] instanceof Libre){
                        if (miTablero.getCuadrantes()[i][j].getNumero() == 0 )
                            descubrirNumeros(i, j);
                        else
                            buttons[i][j].setText(""+miTablero.getCuadrantes()[i][j].getNumero());
                    }                 
                }
            }
        }
    }
    public void cambiarColor(int f, int c, Color color) {
		int i= 1;
		if (turno % 2== 0)
	            i= -1;
		if (f > 0)
	            buttons[f-1][c+i].setBackground(color);
		buttons[f][c+i].setBackground(color);
        //agregue este condicional para los cuadrantes que se ubiquen en la ultima columna
        if (f < 9)
            buttons[f+1][c+i].setBackground(color);
    }   
    public boolean verificar(int f, int c) {
        int i= 1;
	if (turno % 2== 0)
           i= -1;
        return Math.abs(f - fAux)<= 1 && c == cAux + i;
   }
    //Inicia todo el juego
    public void iniciarJuego() {
        while(hayJuego()) {        	
            mover(1);
            if(hayJuego())
            mover(2);
        }
        System.out.println("\n---> Â¡JUEGO TERMINADO!");
    }
    
    // Verifica si el juego se termino
    public boolean hayJuego() {
    	return (!ejer1.getSoldados().isEmpty() && !ejer2.getSoldados().isEmpty() 
    			&& ejer1.getTorre().enPie() && ejer2.getTorre().enPie());
    }
    
    //devuelve la lista ordenda segÃƒÂºn el equipo
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
        //muestra las posiciones vÃ¡lidas para el movimiento del soldado
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
            System.out.println("- Â¡Tiene " + miTablero.getCuadrante(ub).getNumero() + " minas alrededor!");
            System.out.print("---> Ingrese la posiciÃ³n indicada: ");
            k = sc.next();
            if(movValidos.contains(k)) break;
            System.out.print("---> Â¡Movimiento no permitido!\n\n");
	}
	    
	if(!getLista().containsKey(k)) {    	
            getEjercito(team).moverSoldado(ub, k);    	
	    if(miTablero.esMina(k))
	        ActivarMina(team, k);        
	}
	else 
            lucha(ub, k);
		    
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
        	System.out.println("---> Â¡La torre 2 ha sido atacada!");
        	getEjercito(2).getTorre().torreAtacada();        	
        }
        else {

        	System.out.println("---> Â¡La torre 1 ha sido atacada!");
        	getEjercito(1).getTorre().torreAtacada();
        }
    }
}
