import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
    private static final int ANCHO = 1200;
    private static final int ALTO = 600;
    private JPanel botonesCentro, estadisticas, textGuia;
    private JButton iniciarJuego;
    private JButton creditos;
    private JButton puntajes;
    private JButton guia;
    private JButton regresar;
    private JLabel imagen;
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
        creditos = new JButton("Cr√©ditos");
        puntajes = new JButton("Ver Resultados");
        guia = new JButton("Como jugar");
        regresar = new JButton("Regresar");
        JTextArea instrucciones = new JTextArea( 
        "---> Los cuadrados pintados de rojo indican los posibles movimientos.\n"
        + "---> No se puede retroceder.\n"
        + "---> El objetivo es destruir la torre anterior, la cual contar· con tres o uno de vida, seg˙n el modo de juego.\n"
        + "---> Cada vez que un soldado llega al otro extremo se le baja una vida a la torre de dicho equipo, seguido de esto el soldado quedar· eliminado.\n"
        + "---> El n˙mero del cuadrante representa la cantidad de minas que tiene alrededor de sÌ.\n"
        + "---> Los escudos permiten que el usuario evite morir en caso de que haya pisado una mina, no es ˙til cuando se enfrenta a otro soldado.\n"
        + "---> Hay un indicador de turnos en la parte superior del tablero.\n"
        + "---> Puede consultar con panel ubicado al lado izquierdo del tablero que proporciona todo sobre el estado actual de la partida\n"
        + "---> Preste atenciÛn al indicador de minas que est· en la parte superior, eso le indica las minas que est·n prÛximas, para que en base a esto pueda tomar una mejor decisiÛn\n"
        + "---> Preste atenciÛn al indicador de escudo prÛximo, le facilitar· encontrar uno\n"
        + "---> Los escudos desaparecen despuÈs de dar con una mina.\n"
        + "---> Solo se podr· mover el primer soldado del ejÈrcito, los dem·s se ir·n activando conforme queden eliminados los soldados mÛviles.\n"
        + "---> Al encontrarse con un soldado del equipo contrario, se ejecutar· una batalla en la que el ganador se seleccionar· al azar.\n"
        + "--->Si necesita alguna ayuda, en los crÈditos puede encontrar nuestros correos.\n");
        instrucciones.setEditable(false);
        textGuia= new JPanel(new BorderLayout());
        textGuia.add(instrucciones, BorderLayout.CENTER);
        textGuia.add(new JLabel(" "), BorderLayout.SOUTH);
        textGuia.add(new JLabel(" ---"), BorderLayout.EAST);
        textGuia.add(new JLabel(" ---"), BorderLayout.WEST);
        
        botonesCentro.add(iniciarJuego);      
        botonesCentro.add(puntajes);
        botonesCentro.add(guia);
        botonesCentro.add(creditos);
        
        iniciarJuego.addActionListener(new OpcionesPrincipales());
        creditos.addActionListener(new Creditos());
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
            else if (e.getSource() == puntajes) {
                estadisticas = new JPanel(new GridLayout(0, 6));
                viendoPuntajes = true;
                jug1 = DatosJugadores.obtenerDatosJugador()[0];
                jug2 = DatosJugadores.obtenerDatosJugador()[1];
                estadisticas.add(new JLabel("Nombre del jugador", SwingConstants.CENTER));              
                estadisticas.add(new JLabel("Nombre del reino", SwingConstants.CENTER));               
                estadisticas.add(new JLabel("Ha Ganado", SwingConstants.CENTER));                
                estadisticas.add(new JLabel("Turnos realizados", SwingConstants.CENTER));              
                estadisticas.add(new JLabel("Soldados restantes", SwingConstants.CENTER));
                estadisticas.add(new JLabel("Puntaje Final", SwingConstants.CENTER));
                //Datos del jugador 1
                estadisticas.add(new JLabel(jug1.getNombre(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(jug1.getReino(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(jug1.getEstado(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(""+jug1.cantTurnos(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(""+jug1.getCantSoldados(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(""+jug1.puntajeFinal(), SwingConstants.CENTER));
                //Datos del jugador 2
                estadisticas.add(new JLabel(jug2.getNombre(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(jug2.getReino(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(jug2.getEstado(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(""+jug2.cantTurnos(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(""+jug2.getCantSoldados(), SwingConstants.CENTER));
                estadisticas.add(new JLabel(""+jug2.puntajeFinal(), SwingConstants.CENTER));
                
                
                add(estadisticas, BorderLayout.CENTER);
                add(regresar, BorderLayout.SOUTH);
                estadisticas.setVisible(true);                
                regresar.setVisible(true);
            }
            else {
            	textGuia.add(new JLabel("__INSTRUCCIONES:"), BorderLayout.NORTH);
                add(textGuia, BorderLayout.CENTER);
                add(regresar, BorderLayout.SOUTH);
                textGuia.setVisible(true);                
                regresar.setVisible(true);              
            }
        }
    }
    private class Creditos implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, ""
                        + "Run And Destroy v1.0\n\n"
                        + "Curso: Fundamentos de Programaci√≥n 2\n\n"
                        + "Desarrolladores:\n"
                        + " - Anthony Aco Tito (aacot@unsa.edu.pe)\n"
                        + " - Bryan Hancco Condori (bhanccoco@unsa.edu.pe)\n\n"
                        + "Agradecimientos a: Marco Aedo (profesor encargado)\n\n"
                        + "En caso de alg√∫n error cont√°ctese con cualquiera de nosotros\n"
                        + "Deseamos que le guste el juego :)");
        }
    }
    private class Regresar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (viendoPuntajes)
                estadisticas.setVisible(false);            
            else 
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
