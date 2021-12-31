import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
    private static final int ANCHO = 1200;
    private static final int ALTO = 600;
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
                //Linea 1
                "Sea por la modalidad que sea, el objetivo del jugador "+
                "es el de destruir la torre de su enemigo, de manera que "+
                "tendrá que seleccionar la posicion a la que desea moverse.\n"+
                //Linea 2
                "Si pisa una mina morira\n"+
                "Si llega a la torre, su soldado morira, pero disminuira la "+
                "vida de la torre\n"+
                "Si es atacado posiblemente morira, como tambien puede sobrevivir." +
                //Linea 3
                "REGLAS BÁSICAS DEL BUSCAMINAS\n"+
                "Los cuadrantes que presente el buscaminas representan "
        );
        textGuia.setEditable(false);
        
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
                        + "Curso: Fundamentos de Programación 2\n\n"
                        + "Desarrolladores:\n"
                        + " - Anthony Aco Tito (aacot@unsa.edu.pe)\n"
                        + " - Bryan Hancco Condori (bhanccoco@unsa.edu.pe)\n\n"
                        + "Agradecimientos a: Marco Aedo (profesor encargado)\n\n"
                        + "En caso de algún error contáctese con cualquiera de nosotros\n"
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
