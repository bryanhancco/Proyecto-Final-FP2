import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame implements Datos{
    private Tablero miTablero;
    private Ejercito ejer1;
    private Ejercito ejer2;
    private String [] datos = {"Plano","Jugador 1","Jugador2"};
    private HashMap<String, JLabel> labels= new HashMap<String, JLabel>();
    private Color[] miscolores = new Color[3];
    private static final int ANCHO = 1400;
    private static final int ALTO = 800;
    private JButton[][] buttons;
    private String texto= "";
    private JPanel turnoPanel,turnoPanel2, tablero, estadisticas, top;
    private int fAux, cAux, turno = 1;
    private boolean hacerMovimiento;
    
    public Game(Color c1, Color c2) {
        miTablero = new Tablero();
        ejer1 = new Ejercito(1, 1);
        ejer2 = new Ejercito(12, 2);
        setTitle("Run And Destroy");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        tablero= new JPanel(new GridLayout(10,12));		
        tablero.setPreferredSize(new Dimension(1050,800));
        estadisticas= new JPanel(new GridLayout(13,1));
        estadisticas.setPreferredSize(new Dimension(350,800));
        miscolores[0]= new Color(187, 178, 178);
        miscolores[1]= c1;
        miscolores[2]= c2;        
        top= new JPanel(new GridLayout(1,5));
        top.add(new JLabel(""));
        top.add(new JLabel(""));
        createContents();
        
        top.add(turnoPanel = getPanel("Turno"));
        top.add(getPanel("Minas"));
        top.add(turnoPanel2 = getPanel("Turno2"));
        turnoPanel.setBackground(miscolores[1]);
        labels.get("Turno").setText(nombre1);
        turnoPanel2.setBackground(miscolores[2]);
        turnoPanel2.setVisible(false);
        labels.get("Turno2").setText(nombre2);
        top.setPreferredSize(new Dimension(1050,50));
        add(top, BorderLayout.NORTH);
        addLabel(estadisticas);
        setVisible(true);
    }
    
   public void createContents() {     
       buttons = new JButton[10][12];
       for (int i=0; i<10; i++){
           for (int j=0; j<12; j++) {             
               buttons[i][j] = new JButton("");
               if (j == 0 || j == 11) {      
                    buttons[i][j].setBackground(miscolores[2]);
                    if(j == 0)
                        buttons[i][j].setBackground(miscolores[1]);                  
                    buttons[i][j].setForeground(Color.WHITE);
                    buttons[i][j].setText(getLista().get(Tablero.toKey(i + 1,j + 1)).getName());
               }
               else {
                    buttons[i][j].setBackground(miscolores[0]);          
               }
               buttons[i][j].addActionListener(new Acciones());
               tablero.add(buttons[i][j]);
           }
       }
       buttons[0][0].setForeground(negativo(miscolores[1]));
       buttons[0][11].setForeground(negativo(miscolores[2]));
        add(tablero, BorderLayout.CENTER);
        add(estadisticas, BorderLayout.WEST);
   }

    private class Acciones implements ActionListener {
        public void actionPerformed(ActionEvent e) {              
            for (int f= 0; f< buttons.length; f++) {
                for(int c= 0; c< buttons[0].length; c++) {
                    if(e.getSource() == buttons[f][c]) {        	
                        if (hacerMovimiento) {
                            if (buttons[f][c].getBackground().equals(Color.RED))
                        	movimiento(buttons[f][c], f, c);
                            else
                                JOptionPane.showMessageDialog(null, "Â¡Movimiento Invalido!");
                            actDatos();
                        }
                        else
                            seleccionarSoldado(f, c);
                    }
                }
            }
        }
    }
    
    public void seleccionarSoldado(int f, int c) {        
        int team = (turno+1)%2 + 1;
        Soldado selec= getLista().get(Tablero.toKey(f + 1,c + 1));
        if(selec != null && getLstTeam(team).get(0) == selec) {	   
            labels.get("Minas").setText("Minas alrededor: "+miTablero.getCuadrantes()[f][c].getNumero());
            texto = buttons[f][c].getText();
	    fAux = f;
	    cAux = c;
	    cambiarColor(f, c, Color.RED);
	    hacerMovimiento = true; 
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione el soldado disponible");  
   }     
    public void addLabel(JPanel t) {
        t.add(new JLabel("ESTADÃSTICAS", SwingConstants.CENTER));        
        t.add(new JLabel("Terreno: " + datos[0], SwingConstants.CENTER));
		t.add(getPanel("R1"));
		t.add(getPanel("R2"));
        t.add(new JLabel("Esta jugando:", SwingConstants.CENTER));
        t.add(new JLabel("Soldados Restantes:", SwingConstants.CENTER));
        t.add(getPanel("r1"));
        t.add(getPanel("r2"));
        //t.add(getPanel("Mensaje"));
		t.add(getPanel("Torre1"));
        t.add(getPanel("Torre2"));
        JButton bReinicio= new JButton("Reiniciar Juego");
        
		labels.get("R1").setText(datos[1]);
        labels.get("R2").setText(datos[2]);
	
        labels.get("R1").setBackground(miscolores[1]);
        labels.get("R1").setForeground(negativo(miscolores[1]));
        labels.get("R1").setPreferredSize(new Dimension(20,20));
        labels.get("R2").setBackground(miscolores[2]);
        labels.get("R2").setForeground(negativo(miscolores[2]));
	
        bReinicio.addActionListener(new Reiniciar());
        t.add(bReinicio);
        actDatos();
	}
    //reinicia el videojuego, FALTA COMPLETAR
    private class Reiniciar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Reiniciando");
            //createContents();
        }
    }
   //Colorea el cuadrante del string correspondiente
    public JPanel getPanel(String et) {
    	JPanel p = new JPanel(new BorderLayout());
		JLabel l = new JLabel("", SwingConstants.CENTER);
        labels.put(et, l);

        if(et.equals("R1"))
            p.setBackground(miscolores[1]);
        else if(et.equals("R2"))
            p.setBackground(miscolores[2]);
        
        p.add(l);
        return p;
    }
    public Color negativo(Color c) {
        int red = c.getRed();
        int blue = c.getBlue();
        int green = c.getGreen();
        c = new Color(255-red, 255- green, 255- blue);
        return c;
    }
	
    public void actDatos() {
        int team= (turno+1)%2 + 1;    
		labels.get("Turno").setText(datos[team]);
		if (team== 1) {
			turnoPanel.setVisible(true);
			turnoPanel2.setVisible(false);
		}
		else {
			turnoPanel2.setVisible(true);
			turnoPanel.setVisible(false);
		}
		labels.get("Turno").setForeground(negativo(miscolores[team]));
        labels.get("r1").setText(datos[1] + ": " + getLstTeam(1).size() + " soldados");
		labels.get("r2").setText(datos[2] + ": " + getLstTeam(2).size() + " soldados");
        labels.get("r1").setText(datos[1] + ": " + getLstTeam(1).size() + " soldados");
		labels.get("r2").setText(datos[2] + ": " + getLstTeam(2).size() + " soldados");
        labels.get("Torre1").setText("Vida de la torre 1: " + ejer1.getTorre().getVidaTorre());
		labels.get("Torre2").setText("Vida de la torre 2: " + ejer2.getTorre().getVidaTorre());
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
        int team = (turno+1)%2 + 1;
        buttons[fAux][cAux].setText("");
        cambiarColor(fAux, cAux, miscolores[0]);
		buttons[f][c].setBackground( buttons[fAux][cAux].getBackground());
		buttons[f][c].setForeground( buttons[fAux][cAux].getForeground());
		buttons[fAux][cAux].setBackground(new Color(187, 178, 178));
		buttons[fAux][cAux].setForeground(Color.BLACK);
		buttons[f][c].setText(texto);
		getLista().get(Tablero.toKey(fAux + 1,cAux + 1)).setUbicacion(Tablero.toKey(f + 1, c +1));	                
		getEjercito(team).moverSoldado(Tablero.toKey(fAux + 1, cAux + 1),Tablero.toKey(f + 1, c + 1));       
        if (miTablero.getCuadrantes()[f][c] instanceof Libre)
            descubrirNumeros(f, c);
        else if (miTablero.getCuadrantes()[f][c] instanceof Mina) 
            explotarMina(f, c, team);
        

        if (c == 0 || c == 11) {
            JOptionPane.showMessageDialog(null, "Torre " + team + " Atacada");
            getEjercito(team).getSoldados().remove(Tablero.toKey(f + 1, c + 1));
            boton(Tablero.toKey(f + 1, c + 1)).setBackground(miscolores[0]);
            boton(Tablero.toKey(f + 1, c + 1)).setText("");
            getEjercito(team).getTorre().torreAtacada();
            if (getEjercito(team).getTorre().getVidaTorre() == 0)
                getEjercito(team).getTorre().torreDestruida();
        }
        turno++;
        hacerMovimiento = false;        
    }
    public void cambiarColor(int f, int c, Color color) {
        int i = 1;
        if (turno % 2 == 0)
            i = -1;
        for(int j= -1; j<= 1; j++) {
        	if (f + j < 0 || f + j > 9) continue;
            String k = Tablero.toKey(f + j + 1, c  + i + 1);
            buttons[f + j][c + i].setBackground(color);
            if(!buttons[f + j][c + i].getText().equals(""))
                buttons[f + j][c + i].setBackground(new Color(183, 187, 204));
            if(getLista().containsKey(k) && color.equals(miscolores[0]))
                buttons[f + j][c + i].setBackground(miscolores[turno % 2 + 1]);
            
        }
    }
    public void explotarMina(int f, int c, int team) {
        JOptionPane.showMessageDialog(null, "Â¡Pisaste una mina!");
        miTablero.getCuadrantes()[f][c]= new Libre(f,c);
        buttons[f][c].setText("");
        buttons[f][c].setBackground(new Color(187, 178, 178));
        getEjercito(team).getSoldados().remove(Tablero.toKey(f + 1, c + 1));
        boton(getLstTeam(team).get(0).getUbicacion()).setForeground(negativo(miscolores[team]));
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
    
    public void descubrirNumeros(int f, int c) {
        miTablero.getCuadrantes()[f][c].cambiarEstado();
        if (miTablero.getCuadrantes()[f][c].getNumero() == 0) {
            for (int i=f-1; i<=f+1; i++){
                if (i < 0 || i > 9) continue;
                for (int j=c-1; j<=c+1; j++){
                    if (j <= 0 || j >= 11) continue;
                    ///****ERROR
                    if(getLista().containsKey(Tablero.toKey(i + 1, j + 1))) continue;
                    if (miTablero.getCuadrantes()[i][j] instanceof Mina || miTablero.getCuadrantes()[i][j].getEstado()) continue;   
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
        boton(ub1).setBackground(miscolores[0]);
    }
    
    // Verifica si el juego se termino
    public boolean hayJuego() {
    	return (!ejer1.getSoldados().isEmpty() && !ejer2.getSoldados().isEmpty() 
    			&& ejer1.getTorre().enPie() && ejer2.getTorre().enPie());
    }
    
    //devuelve la lista ordenda segÃÆÃâÃâ Ã¢â¬â¢ÃÆÃ¢â¬Å¡ÃâÃÂºn el equipo
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
