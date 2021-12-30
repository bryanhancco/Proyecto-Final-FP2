import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Menu extends JFrame {
    private static final int ANCHO = 800;
    private static final int ALTO = 600;
    private JPanel botonesCentro, estadisticas;
    private JButton iniciarJuego;
    private DatosJugadores d;
    private JButton continuarJuego;
    private JButton puntajes;
    private JButton guia;
    private JButton regresar;
    private JTextArea textGuia;
    private boolean viendoPuntajes;
    private Jugador jug1, jug2;
    
    public Menu() {
        setTitle("Bienvenido a Run And Destroy");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();    
        setVisible(true);
    }      
    public void createContents() {
        d = new DatosJugadores();
        botonesCentro = new JPanel(new GridLayout(4, 1));
        iniciarJuego = new JButton("Nuevo Juego");
        continuarJuego = new JButton("Continuar Juego");
        puntajes = new JButton("Ver Puntajes");
        guia = new JButton("Como jugar");
        regresar = new JButton("Regresar");
        textGuia = new JTextArea(
                "Sea por la modalidad que sea, el objetivo del jugador\n"+
                "es el de destruir la torre de su enemigo, de manera que\n"+
                "tendrá que seleccionar la posicion a la que desea moverse.\n\n"+
                "Si pisa una mina morira.\n\n"+
                "Si llega a la torre, su soldado morira, pero disminuira la\n"+
                "vida de la torre.\n\n"+
                "Si es atacado posiblemente morira, como tambien puede sobrevivir."        
        );
        
        botonesCentro.add(iniciarJuego);
        botonesCentro.add(continuarJuego);
        botonesCentro.add(puntajes);
        botonesCentro.add(guia);
        
        iniciarJuego.addActionListener(new OpcionesPrincipales());
        continuarJuego.addActionListener(new OpcionesPrincipales());
        puntajes.addActionListener(new OpcionesPrincipales());
        guia.addActionListener(new OpcionesPrincipales());
        regresar.addActionListener(new Regresar());
        
        add(botonesCentro, BorderLayout.CENTER);
      
    }
    private class OpcionesPrincipales implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            botonesCentro.setVisible(false);
            viendoPuntajes = false;
            if (e.getSource() == iniciarJuego) {
                setVisible(false);
                new Cuestionario();
            }
            else if (e.getSource() == continuarJuego) {
                //jug1 = d.obtenerDatosJugador()[0];
                //jug2 = d.obtenerDatosJugador()[1];
            }
            else if (e.getSource() == puntajes) {
                viendoPuntajes = true;
                jug1 = DatosJugadores.obtenerDatosJugador()[0];
                jug2 = DatosJugadores.obtenerDatosJugador()[1];
                estadisticas = new JPanel(new GridLayout(0, 5));
                estadisticas.add(new JLabel("Nombre del jugador"));              
                estadisticas.add(new JLabel("Nombre del reino"));               
                estadisticas.add(new JLabel("Ha Ganado"));                
                estadisticas.add(new JLabel("Turnos realizados"));              
                estadisticas.add(new JLabel("Soldados muertos"));
                //Datos del jugador 1
                estadisticas.add(new JLabel(jug1.getNombre()));
                estadisticas.add(new JLabel(jug1.getReino()));
                estadisticas.add(new JLabel(""+jug1.getEstado()));
                estadisticas.add(new JLabel(""));
                estadisticas.add(new JLabel(""));
                //Datos del jugador 1
                estadisticas.add(new JLabel(jug2.getNombre()));
                estadisticas.add(new JLabel(jug2.getReino()));
                estadisticas.add(new JLabel(""+jug2.getEstado()));
                estadisticas.add(new JLabel(""));
                estadisticas.add(new JLabel(""));
                
                add(estadisticas, BorderLayout.CENTER);
                add(regresar, BorderLayout.SOUTH);
                estadisticas.setVisible(true);                
                regresar.setVisible(true);
            }
            else {              
                add(textGuia, BorderLayout.CENTER);
                add(regresar, BorderLayout.SOUTH);
                textGuia.setVisible(true);                
                regresar.setVisible(true);              
            }
        }
    }
    private class Regresar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (viendoPuntajes) 
                estadisticas.setVisible(false);           
            else 
                textGuia.setVisible(false);              
            regresar.setVisible(false);
            botonesCentro.setVisible(true);
        }
    }
    public static void main(String args[]) {
        new Menu();
    }
    public void obtenerDatos() {
        jug1 = d.obtenerDatosJugador()[0];
        jug2 = d.obtenerDatosJugador()[1];
    }
}

