import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
    private static final int ANCHO = 1300;
    private static final int ALTO = 700;
    private JPanel botonesCentro, estadisticas, textoCreditos;
    private JButton iniciarJuego;
    private JButton creditos;
    private JButton puntajes;
    private JButton guia;
    private JButton regresar;
    private JLabel imagen;
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
        imagen = new JLabel("", SwingConstants.CENTER);
        botonesCentro = new JPanel(new GridLayout(2, 2));
        iniciarJuego = new JButton("Nuevo Juego");
        creditos = new JButton("Créditos");
        puntajes = new JButton("Ver Resultados");
        guia = new JButton("Como jugar");
        regresar = new JButton("Regresar");
        textGuia = new JTextArea(
                "Sea por la modalidad que sea, el objetivo del jugador"+
                "es el de destruir la torre de su enemigo, de manera que"+
                "tendrÃ¡ que seleccionar la posicion a la que desea moverse."+
                "Si pisa una mina morira\n"+
                "Si llega a la torre, su soldado morira, pero disminuira la"+
                "vida de la torre\n"+
                "Si es atacado posiblemente morira, como tambien puede sobrevivir."        
        );
        
        botonesCentro.add(iniciarJuego);      
        botonesCentro.add(puntajes);
        botonesCentro.add(guia);
        botonesCentro.add(creditos);
        
        iniciarJuego.addActionListener(new OpcionesPrincipales());
        creditos.addActionListener(new OpcionesPrincipales());
        puntajes.addActionListener(new OpcionesPrincipales());
        guia.addActionListener(new OpcionesPrincipales());
        regresar.addActionListener(new Regresar());
        botonesCentro.setPreferredSize(new Dimension(ANCHO, 150));
        imagen.setIcon(new ImageIcon("runanddestroy.jpeg"));
        add(imagen, BorderLayout.CENTER);
        add(botonesCentro, BorderLayout.SOUTH);      
    }
    
    private class OpcionesPrincipales implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            imagen.setVisible(false);
            botonesCentro.setVisible(false);
            viendoPuntajes = false;          
            if (e.getSource() == iniciarJuego) {
                setVisible(false);
                new Cuestionario();
            }
            else if (e.getSource() == creditos) {
                add(creditos, BorderLayout.CENTER);
                add(regresar, BorderLayout.SOUTH);
                creditos.setVisible(true);                
                regresar.setVisible(true);
            }
            else if (e.getSource() == puntajes) {
                viendoPuntajes = true;
                jug1 = DatosJugadores.obtenerDatosJugador()[0];
                jug2 = DatosJugadores.obtenerDatosJugador()[1];
                estadisticas = new JPanel(new GridLayout(0, 6));
                estadisticas.add(new JLabel("Nombre del jugador"));              
                estadisticas.add(new JLabel("Nombre del reino"));               
                estadisticas.add(new JLabel("Ha Ganado"));                
                estadisticas.add(new JLabel("Turnos realizados"));              
                estadisticas.add(new JLabel("Soldados restantes"));
                estadisticas.add(new JLabel("Puntaje Final"));
                //Datos del jugador 1
                estadisticas.add(new JLabel(jug1.getNombre()));
                estadisticas.add(new JLabel(jug1.getReino()));
                estadisticas.add(new JLabel(jug1.getEstado()));
                estadisticas.add(new JLabel(""+jug1.cantTurnos()));
                estadisticas.add(new JLabel(""+jug1.getCantSoldados()));
                estadisticas.add(new JLabel(""+jug1.puntajeFinal()));
                //Datos del jugador 2
                estadisticas.add(new JLabel(jug2.getNombre()));
                estadisticas.add(new JLabel(jug2.getReino()));
                estadisticas.add(new JLabel(jug2.getEstado()));
                estadisticas.add(new JLabel(""+jug2.cantTurnos()));
                estadisticas.add(new JLabel(""+jug2.getCantSoldados()));
                estadisticas.add(new JLabel(""+jug2.puntajeFinal()));
                
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
            creditos.setVisible(false);
            estadisticas.setVisible(false);           
            textGuia.setVisible(false);              
            regresar.setVisible(false);
            imagen.setVisible(true);
            botonesCentro.setVisible(true);
        }
    }
    
    public static void main(String args[]) {
        new Menu();
    }
}
