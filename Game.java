import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame implements Datos{
    private Tablero miTablero;
    private Ejercito ejer1, ejer2;
    private String [] datos = new String[3];
    private HashMap<String, JLabel> labels;
    private Color[] misColores = new Color[3];
    private static final int ANCHO = 1400;
    private static final int ALTO = 800;
    private JButton[][] botonesAccion;
    private String texto = "";
    private JPanel turnoPanel,turnoPanel2, tablero, estadisticas, top;
    private int fAux, cAux, turno = 1;
    private boolean hacerMovimiento, hayEscudoCerca;
    private Jugador[] jugadores;
    
    public Game(Color c1, Color c2, String n1, String n2, String r1, String r2) {
        //declarando variables
        labels = new HashMap<String, JLabel>();
        jugadores = new Jugador[2];
        jugadores[0] = new Jugador(c1, n1, r1);
        jugadores[1] = new Jugador(c2, n2, r2);
        DatosJugadores.actualizarDatos(jugadores[0], jugadores[1]);
        datos[0] = "Plano";
        datos[1] = n1;
        datos[2] = n2;
        miTablero = new Tablero();
        ejer1 = new Ejercito(1, 1);
        ejer2 = new Ejercito(12, 2);
        misColores[0] = new Color(187, 178, 178);
        misColores[1] = c1;
        misColores[2] = c2;  
        
        //Agregando la parte gráfica del juego
        setTitle("Run And Destroy");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout());   
        createContents();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);    
    }
    //creando el contenido para los botones
    public void createContents() {     
        //creando y editando los paneles
        tablero = new JPanel(new GridLayout(10, 12));		
        tablero.setPreferredSize(new Dimension(1050, 800));
        estadisticas = new JPanel(new GridLayout(0, 1));
        estadisticas.setPreferredSize(new Dimension(350, 800));   
        top = new JPanel(new GridLayout(1, 0));
        top.setPreferredSize(new Dimension(1050, 50));
        
        //creando los botones del tablero
        botonesAccion = new JButton[10][12];
        for (int i=0; i<10; i++){
            for (int j=0; j<12; j++) {             
                botonesAccion[i][j] = new JButton("");
                if (j == 0 || j == 11) {      
                    botonesAccion[i][j].setBackground(misColores[2]);
                    if(j == 0)
                        botonesAccion[i][j].setBackground(misColores[1]);                  
                    botonesAccion[i][j].setForeground(Color.WHITE);
                    botonesAccion[i][j].setText(getLista().get(Tablero.toKey(i + 1,j + 1)).getName());
                }
                else {
                    botonesAccion[i][j].setBackground(misColores[0]);     
                    if (miTablero.getCuadrantes()[i][j].tieneEscudo())
                        botonesAccion[i][j].setBackground(new Color(92, 120, 233));                                          
                }
                botonesAccion[i][j].addActionListener(new Acciones());
                tablero.add(botonesAccion[i][j]);
            }
        }
        botonesAccion[0][0].setForeground(negativo(misColores[1]));
        botonesAccion[0][11].setForeground(negativo(misColores[2]));
        
        //añadiendo los elementos a top
        top.add(new JLabel(""));
        top.add(getPanel("Escudo"));
        top.add(turnoPanel = getPanel("Turno"));
        top.add(getPanel("Minas"));
        top.add(turnoPanel2 = getPanel("Turno2"));
        
        labels.get("Turno").setText(datos[1]);
        labels.get("Turno2").setText(datos[2]);
        
        turnoPanel.setBackground(misColores[1]);     
        turnoPanel2.setBackground(misColores[2]);
        turnoPanel2.setVisible(false);
       
        add(top, BorderLayout.NORTH);
        add(tablero, BorderLayout.CENTER);
        add(estadisticas, BorderLayout.WEST);
        addLabel(estadisticas);
   }
   //permite seleccionar unidades y tambien moverlas a lo largo del tablero
    private class Acciones implements ActionListener {
        public void actionPerformed(ActionEvent e) {              
            for (int f = 0; f< botonesAccion.length; f++) {
                for(int c = 0; c< botonesAccion[0].length; c++) {
                    if(e.getSource() == botonesAccion[f][c]) {        	
                        if (hacerMovimiento) {
                            if (botonesAccion[f][c].getBackground().equals(Color.RED))
                        	movimiento(botonesAccion[f][c], f, c);
                            else
                                JOptionPane.showMessageDialog(null, "¡Movimiento Inválido!");                        
                            actDatos();
                        }
                        else
                            seleccionarSoldado(f, c);
                    }
                }
            }
        }
    }
    //permite seleccionar al soldado del ejercito correspondiente
    public void seleccionarSoldado(int f, int c) {        
        int team = (turno + 1) % 2 + 1;
        jugadores[team - 1].aumentarTurno();
        DatosJugadores.actualizarDatos(jugadores[0], jugadores[1]);
        Soldado selec= getLista().get(Tablero.toKey(f + 1, c + 1));
        if(selec != null && getLstTeam(team).get(0) == selec) {	   
            labels.get("Minas").setText("Minas alrededor: " + miTablero.getCuadrantes()[f][c].getNumero());  
            texto = botonesAccion[f][c].getText();
	    fAux = f;
	    cAux = c;
	    cambiarColor(f, c, Color.RED);
            if (hayEscudoCerca) 
                labels.get("Escudo").setText("Hay un escudo cerca");  
            
	    hacerMovimiento = true; 
        }
        else
            JOptionPane.showMessageDialog(null, "¡Seleccione a un soldado!");  
    }     
    //muestra los mensajes de la barra lateral izquierda
    public void addLabel(JPanel t) {
        t.add(new JLabel("ESTADÍSTICAS", SwingConstants.CENTER));        
        t.add(new JLabel("Terreno: " + datos[0], SwingConstants.CENTER));
	t.add(getPanel("R1"));
	t.add(getPanel("R2"));
        t.add(new JLabel("Soldados Restantes:", SwingConstants.CENTER));
        t.add(getPanel("r1"));
        t.add(getPanel("r2"));
        //t.add(getPanel("Mensaje"));
	t.add(getPanel("Torre1"));      
        t.add(getPanel("Torre2"));
        JButton bReinicio = new JButton("Regresar");
        
	labels.get("R1").setText(datos[1]);
        labels.get("R2").setText(datos[2]);
	
        labels.get("R1").setBackground(misColores[1]);
        labels.get("R1").setForeground(negativo(misColores[1]));
        labels.get("R1").setPreferredSize(new Dimension(20,20));
        labels.get("R2").setBackground(misColores[2]);
        labels.get("R2").setForeground(negativo(misColores[2]));
	
        bReinicio.addActionListener(new Salir());
        t.add(bReinicio);
        actDatos();
    }
    //Listener que permite regresar al menú
    private class Salir implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new Menu();
        }
    }
   //
    public JPanel getPanel(String et) {
        JPanel p = new JPanel(new BorderLayout());
	JLabel l = new JLabel("", SwingConstants.CENTER);
        labels.put(et, l);

        if(et.equals("R1"))
            p.setBackground(misColores[1]);
        else if(et.equals("R2"))
            p.setBackground(misColores[2]);       
        p.add(l);
        return p;
    }
    public Color negativo(Color c) {
        int red = c.getRed();
        int blue = c.getBlue();
        int green = c.getGreen();
        c = new Color(255 - red, 255 - green, 255 - blue);
        return c;
    }
    //actualiza los datos mostrados en la barra lateral izquierda
    public void actDatos() {
        int team = (turno + 1) % 2 + 1;    
	labels.get("Turno").setText(datos[team]);
	if (team == 1) {
            turnoPanel.setVisible(true);
            turnoPanel2.setVisible(false);
            }
	else {
            turnoPanel2.setVisible(true);
            turnoPanel.setVisible(false);
	}
	labels.get("Turno").setForeground(negativo(misColores[team]));
        labels.get("r1").setText(datos[1] + ": " + getLstTeam(1).size() + " soldados");
	labels.get("r2").setText(datos[2] + ": " + getLstTeam(2).size() + " soldados");
        labels.get("Torre1").setText("Torre 1: " + ejer1.getTorre().getVidaTorre());
	labels.get("Torre2").setText("Torre 2: " + ejer2.getTorre().getVidaTorre());
    }   
    //obtiene el boton correspondiente segun la ubicación
    public JButton boton(String ub) {
        int large = ub.length();
        String columna = ub.substring(large - 1);
        int col = columna.compareTo("A");
        int fila = Integer.parseInt(ub.substring(0, ub.length() - 1)) -1;
        return botonesAccion[fila][col];
    }    
    //cambia la ubicacion del soldado, tanto en el tablero, como en el HashMap
    public void movimiento(JButton b, int f, int c) {
        hayEscudoCerca = false;
        labels.get("Escudo").setText("");  
        int team = (turno + 1) % 2 + 1;
        cambiarColor(fAux, cAux, misColores[0]);
        if (miTablero.getCuadrantes()[f][c] instanceof Libre) {    
            if(getLista().containsKey(Tablero.toKey(f + 1, c + 1)))              
                lucha(Tablero.toKey(fAux + 1, cAux + 1), Tablero.toKey(f + 1, c + 1));            
            else {                
                accionMovimiento(f, c, team);
                if (miTablero.getCuadrantes()[f][c].tieneEscudo()) {
                    getLista().get(Tablero.toKey(f +1 , c + 1)).setEscudo();
                    JOptionPane.showMessageDialog(null, "¡Su soldado tiene un escudo!");
                } 
                descubrirNumeros(f, c);    
            }
        }
        else if (miTablero.getCuadrantes()[f][c] instanceof Mina) {
            accionMovimiento(f, c, team);
            explotarMina(f, c, team);        
        }
        if (c == 0 || c == 11) 
            atacarTorre(f, c, team);
             
        turno++;
        hacerMovimiento = false;        
    }
    //se ejecuta tras iniciarse el movimiento
    public void atacarTorre(int f, int c, int team) {
        JOptionPane.showMessageDialog(null, "Torre " + team + " Atacada");
        getEjercito(team).getSoldados().remove(Tablero.toKey(f + 1, c + 1));
        boton(Tablero.toKey(f + 1, c + 1)).setBackground(misColores[0]);
        boton(Tablero.toKey(f + 1, c + 1)).setText("");
        getEjercito(team).getTorre().torreAtacada();
        if (!getEjercito(team).getTorre().enPie()) {
            jugadores[Math.abs(team-2)].haGanado();
            DatosJugadores.actualizarDatos(jugadores[0], jugadores[1]);
            setVisible(false);
            new Menu();
        }
    }
    public void accionMovimiento(int f, int c, int team) {
        botonesAccion[fAux][cAux].setText("");               
        botonesAccion[f][c].setBackground(botonesAccion[fAux][cAux].getBackground());
        botonesAccion[f][c].setForeground(botonesAccion[fAux][cAux].getForeground());
        botonesAccion[fAux][cAux].setBackground(new Color(187, 178, 178));
        botonesAccion[fAux][cAux].setForeground(Color.BLACK);
        botonesAccion[f][c].setText(texto); 
        getLista().get(Tablero.toKey(fAux + 1,cAux + 1)).setUbicacion(Tablero.toKey(f + 1, c +1));	                
        getEjercito(team).moverSoldado(Tablero.toKey(fAux + 1, cAux + 1),Tablero.toKey(f + 1, c + 1));
    }
    //cambia el color de las posiciones seleccionables
    public void cambiarColor(int f, int c, Color color) {
        int i = 1;
        if (turno % 2 == 0)
            i = -1;
        for(int j= -1; j<= 1; j++) {
            if (f + j < 0 || f + j > 9) continue;
            String k = Tablero.toKey(f + j + 1, c  + i + 1);
            if (miTablero.getCuadrantes()[f + j][c + i].tieneEscudo()) {
                hayEscudoCerca = true;
            }
            botonesAccion[f + j][c + i].setBackground(color);
            if(!botonesAccion[f + j][c + i].getText().equals("") && hacerMovimiento)
                botonesAccion[f + j][c + i].setBackground(new Color(183, 187, 204));
            if(getLista().containsKey(k) && color.equals(misColores[0]))
                botonesAccion[f + j][c + i].setBackground(misColores[turno % 2 + 1]);
        }
    }
    //metodo elimina al jugador y "esclarece" las posiciones circundantes
    public void explotarMina(int f, int c, int team) {
        JOptionPane.showMessageDialog(null, "¡Pisaste una mina!");
        miTablero.getCuadrantes()[f][c] = new Libre(f, c);
        if (getLista().get(Tablero.toKey(f + 1, c + 1)).tieneEscudo()) 
            JOptionPane.showMessageDialog(null, "Ha perdido su escudo");      
        else {          
            botonesAccion[f][c].setText("");
            botonesAccion[f][c].setBackground(new Color(187, 178, 178));
            getEjercito(team).getSoldados().remove(Tablero.toKey(f + 1, c + 1));
            boton(getLstTeam(team).get(0).getUbicacion()).setForeground(negativo(misColores[team]));
            for (int i = f - 1; i <= f + 1; i++){
                if (i < 0 || i > 9) continue;
                for (int j = c - 1; j <= c + 1; j++){
                    if (j < 0 || j > 11) continue;
                    if (i == f && j == c) continue;
                    if (miTablero.getCuadrantes()[i][j] instanceof Libre){
                        Libre cNum = (Libre) miTablero.getCuadrantes()[i][j];
                        cNum.disminuirCantidad();
                        if(getLista().containsKey(Tablero.toKey(i + 1, j + 1))) continue;
                        if (cNum.getNumero() <= 0) {
                            botonesAccion[i][j].setText("");
                            botonesAccion[i][j].setBackground(new Color(187, 178, 178));
                        }
                        else {
                            botonesAccion[i][j].setText(""+miTablero.getCuadrantes()[i][j].getNumero());
                            botonesAccion[i][j].setBackground(new Color(183, 187, 204));
                        }
                    }
                }   
            }
        }
    }    
    //descubre los numeros que se ubican alrededor de una mina
    public void descubrirNumeros(int f, int c) {
        miTablero.getCuadrantes()[f][c].cambiarEstado();
        if (miTablero.getCuadrantes()[f][c].getNumero() == 0) {
            for (int i = f - 1; i <= f + 1; i++){
                if (i < 0 || i > 9) continue;
                for (int j = c - 1; j <= c + 1; j++){
                    if (j <= 0 || j >= 11) continue;   
                    if (miTablero.getCuadrantes()[i][j].tieneEscudo())
                        hayEscudoCerca = true;
                    if(getLista().containsKey(Tablero.toKey(i + 1, j + 1))) continue;
                    if (miTablero.getCuadrantes()[i][j] instanceof Mina || !miTablero.getCuadrantes()[i][j].getEstado()) continue;   
                    if (miTablero.getCuadrantes()[i][j].getNumero() > 0 ) {
                        botonesAccion[i][j].setText(""+miTablero.getCuadrantes()[i][j].getNumero());     
                        miTablero.getCuadrantes()[i][j].cambiarEstado();
                        botonesAccion[i][j].setBackground(new Color(183, 187, 204));
                    }
                    else 
                        descubrirNumeros(i, j);
                }
            }
        }
        else {
            if ((cAux != 0 && cAux != 11)) {
                botonesAccion[fAux][cAux].setText(""+miTablero.getCuadrantes()[fAux][cAux].getNumero());     
                miTablero.getCuadrantes()[fAux][cAux].cambiarEstado();
                botonesAccion[fAux][cAux].setBackground(new Color(183, 187, 204));      
            }
        }
    }   
    //elimina al soldado que pierda tras una lucha
    public void lucha(String ub1, String ub2) {
        int num = (int)(Math.random() * 2);
        int team1 = getLista().get(ub1).getTeam();
        int team2 = getLista().get(ub2).getTeam();
        if (num == 0) {
            jugadores[1].eliminarSoldado();
            getEjercito(team2).retirarSoldado(ub2);
            getEjercito(team1).moverSoldado(ub1, ub2);
            boton(ub2).setText(boton(ub1).getText());
            boton(ub2).setBackground(boton(ub1).getBackground());
        }
        else {
            jugadores[0].eliminarSoldado();
            getEjercito(team1).retirarSoldado(ub1); 
        }
        boton(ub1).setText("");
        boton(ub1).setBackground(misColores[0]);
    }

    //devuelve la lista ordenada según el equipo
    public ArrayList<Soldado> getLstTeam(int team){
        if (team == 1)
            return ejer1.getLstOrdenada();
        else
            return ejer2.getLstOrdenada();
    }
    
    //devuelve la lista completa de soldados de ambos ejercitos
    public HashMap<String, Soldado> getLista(){
        HashMap<String, Soldado> lst = new HashMap<String, Soldado>();
        for (Soldado s: ejer1.getLstOrdenada())
            lst.put(s.getUbicacion(), s);
        for (Soldado s: ejer2.getLstOrdenada())
            lst.put(s.getUbicacion(), s);
        return lst;
    }
    //devuelve el ejercito según el equipo al que corresponda
    public Ejercito getEjercito(int team) {
        if (team == 1)
            return ejer1;
        else
            return ejer2;
    }
}
