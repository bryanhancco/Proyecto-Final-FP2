import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Game extends JFrame{
    private Tablero miTablero;
    private Ejercito ejer1;
    private Ejercito ejer2;
    private String [] datos = {"Plano","Jugador 1","Jugador2"};
    private Color[] colores = new Color[3];
    private static final int ANCHO = 960;
    private static final int ALTO = 800;
    private JButton[][] buttons;
    private String texto= "";
    private int fAux, cAux, turno = 1;
    private boolean hacerMovimiento;
    
    public Game() {
        miTablero = new Tablero();
        ejer1 = new Ejercito(1, 1);
        ejer2 = new Ejercito(12, 2);
        //JOptionPane.showMessageDialog(null, "Bienvenido, usted va a pasarlo muy bien");
        setTitle("Videojuego");
        setSize(ANCHO, ALTO);
        setLayout(new GridLayout(10,12));
        setDefaultCloseOperation(EXIT_ON_CLOSE);              
        colores[0]= new Color(187, 178, 178);
        colores[1]= new Color(206,54,45);
        colores[2]= new Color(80,200,60);
        createContents();  
        setVisible(true);
    }
    
   public void createContents() {     
       buttons = new JButton[10][12];
       for (int i=0; i<10; i++){
           for (int j=0; j<12; j++) {             
               buttons[i][j] = new JButton("");
               if (j == 0 || j == 11) {      
                    buttons[i][j].setBackground(colores[2]);
                    if(j == 0)
                        buttons[i][j].setBackground(colores[1]);                  
                    buttons[i][j].setForeground(Color.WHITE);
                    buttons[i][j].setText(getLista().get(Tablero.toKey(i + 1,j + 1)).getName());
               }
               //agregue esta condicional para que los demÃƒÂ¡s cuadrantes quedasen blancos
               else {
                    buttons[i][j].setBackground(colores[0]);
                    /*if (miTablero.getCuadrantes()[i][j].tieneMina)
                         buttons[i][j].setText("*");*/
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
       JOptionPane.showMessageDialog(null, "Soldados restantes en el Ejercito Nro1: " + getLstTeam(1).size()
			+ "\nVida de la Torre 1: " + getEjercito(1).getTorre().getVidaTorre()
			+ "\nSoldados restantes en el Ejercito Nro2: " + getLstTeam(2).size()
			+ "\nVida de la Torre 2: " + getEjercito(2).getTorre().getVidaTorre()
			+ "\nTURNO DEL JUGADOR NÃƒâ€šÃ‚Â° 1" 
			+ "\n" + getLstTeam(1).get(0).getNombre());
   }
   
    private class acciones implements ActionListener {
        public void actionPerformed(ActionEvent e) {              
            for (int f= 0; f< buttons.length; f++) {
                for(int c= 0; c< buttons[0].length; c++) {
                    if(e.getSource() == buttons[f][c]) {        	
                        if (hacerMovimiento) {
                        	if (buttons[f][c].getBackground().equals(Color.RED))
                        		movimiento(buttons[f][c], f, c);
                        	else
                        		JOptionPane.showMessageDialog(null, "�Movimiento Invalido!");
                        }
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
	        JOptionPane.showMessageDialog(null, "Â¡Tiene " + miTablero.getCuadrantes()[f][c].getNumero()+ " minas cerca!");
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
    	String ub= Tablero.toKey(f + 1, c + 1);
    	String ubAux= Tablero.toKey(fAux + 1, cAux + 1);
    	//if(getLista().containsKey(ub) && lucha(ubAux, ub))
    	buttons[fAux][cAux].setText("");
	//Definir Color
    	cambiarColor(fAux, cAux, colores[0]);

		buttons[f][c].setBackground( buttons[fAux][cAux].getBackground());
		buttons[fAux][cAux].setBackground(new Color(187, 178, 178));
		buttons[f][c].setText(texto);
		getLista().get(Tablero.toKey(fAux + 1,cAux + 1)).setUbicacion(Tablero.toKey(f + 1, c +1));
	                
		getEjercito(team).moverSoldado(Tablero.toKey(fAux + 1, cAux + 1),Tablero.toKey(f + 1, c + 1));       
	        
        if (miTablero.getCuadrantes()[f][c] instanceof Mina) {
        	JOptionPane.showMessageDialog(null, "Â¡Pisaste una mina!");
        	miTablero.getCuadrantes()[f][c]= new Libre(f,c);
        	buttons[f][c].setText("");
        	buttons[f][c].setBackground(new Color(187, 178, 178));
        	getEjercito(team).getSoldados().remove(Tablero.toKey(f + 1, c + 1));
        	boton(getLstTeam(team).get(0).getUbicacion()).setForeground(Color.BLACK);
                for (int i=f-1; i<=f+1; i++){
                    if (i < 0 || i > 9) continue;
                    for (int j=c-1; j<=c+1; j++){
                        if (j < 0 || j > 11) continue;
                        if (i == f && j == c) continue;
                        if (miTablero.getCuadrantes()[i][j] instanceof Libre){
                            Libre cNum = (Libre) miTablero.getCuadrantes()[i][j];
                            cNum.disminuirCantidad();
                            if(getLista().containsKey(Tablero.toKey(i + 1, j + 1))) continue;
                            if (cNum.getNumero() <= 0) {
                                buttons[i][j].setText("");
                                buttons[i][j].setBackground(new Color(187, 178, 178));
                            }
                            else {
                                buttons[i][j].setText(""+miTablero.getCuadrantes()[i][j].getNumero());
                                buttons[i][j].setBackground(new Color(183, 187, 204));
                            }
                        }
                    }
                }
            }
        else if(c == 11 && turno % 2 != 0) {
            JOptionPane.showMessageDialog(null, "Torre 2 Atacada");
            getEjercito(team).getSoldados().remove(Tablero.toKey(f + 1, c + 1));
            boton(Tablero.toKey(f + 1, c + 1)).setBackground(Color.WHITE);
            boton(Tablero.toKey(f + 1, c + 1)).setText("");
            getEjercito(2).getTorre().torreAtacada();
            if (getEjercito(2).getTorre().getVidaTorre() == 0)
            	getEjercito(2).getTorre().torreDestruida();
        }
        else if(c == 0 && turno % 2 == 0) {
            JOptionPane.showMessageDialog(null, "Torre 1 Atacada");
            getEjercito(team).getSoldados().remove(Tablero.toKey(f + 1, c + 1));            
            boton(Tablero.toKey(f + 1, c + 1)).setBackground(Color.WHITE);
            boton(Tablero.toKey(f + 1, c + 1)).setText("");
            getEjercito(1).getTorre().torreAtacada();
            if (getEjercito(1).getTorre().getVidaTorre() == 0)
            	getEjercito(1).getTorre().torreDestruida();
        }
        else if (miTablero.getCuadrantes()[f][c] instanceof Libre)
            descubrirNumeros(f, c);
        Mensaje();
        turno++;
        hacerMovimiento = false;        
    }
    public void descubrirNumeros(int f, int c) {
        miTablero.getCuadrantes()[f][c].cambiarEstado();
        if (miTablero.getCuadrantes()[f][c].getNumero() == 0) {
            for (int i=f-1; i<=f+1; i++){
                if (i < 0 || i > 9) continue;
                for (int j=c-1; j<=c+1; j++){
                    if (j < 0 || j > 11) continue;
                    if(getLista().containsKey(Tablero.toKey(i + 1, j + 1))) continue;
                    if (miTablero.getCuadrantes()[i][j] instanceof Mina || !miTablero.getCuadrantes()[i][j].getEstado()) continue;   
                    if (miTablero.getCuadrantes()[i][j].getNumero() > 0 ) {
                        buttons[i][j].setText(""+miTablero.getCuadrantes()[i][j].getNumero());     
                        miTablero.getCuadrantes()[i][j].cambiarEstado();
                        buttons[i][j].setBackground(new Color(183, 187, 204));
                    }
                    else 
                        descubrirNumeros(i, j);
                }
            }
        }           
    }
    
    public void cambiarColor(int f, int c, Color color) {
        int i= 1;
        if (turno % 2== 0)
            i= -1;
        for(int j= -1; j<= 1; j++) {
        	if (f + j < 0 || f + j > 9) continue;
        	String k= Tablero.toKey(f + j + 1, c  + i + 1);
        	buttons[f + j][c + i].setBackground(color);
        	if(getLista().containsKey(k) && color.equals(colores[0]))
        		buttons[f + j][c + i].setBackground(colores[turno % 2 + 1]);
        }
    }
    
    public void lucha(String ub1, String ub2) {
        int num= (int)(Math.random() * 2);
        int team1= getLista().get(ub1).getTeam();
        int team2= getLista().get(ub2).getTeam();
        if (num == 1) {
            JOptionPane.showMessageDialog(null, "---> Gana " + getLista().get(ub1).getNombre());
            getEjercito(team2).retirarSoldado(ub2);
            getEjercito(team1).moverSoldado(ub1, ub2);
            boton(ub2).setText(boton(ub1).getText());
            boton(ub2).setBackground(boton(ub1).getBackground());
        }
        else {
        	JOptionPane.showMessageDialog(null, "---> Gana " + getLista().get(ub2).getNombre());
            getEjercito(team1).retirarSoldado(ub1);
        }
        boton(ub1).setText("");
        boton(ub1).setBackground(colores[0]);
    }
    
    // Verifica si el juego se termino
    public boolean hayJuego() {
    	return (!ejer1.getSoldados().isEmpty() && !ejer2.getSoldados().isEmpty() 
    			&& ejer1.getTorre().enPie() && ejer2.getTorre().enPie());
    }
    
    //devuelve la lista ordenda segÃƒÆ’Ã‚Âºn el equipo
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
}
