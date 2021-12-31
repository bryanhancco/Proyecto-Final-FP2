import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
   
public class Cuestionario extends JFrame implements Datos{    
    private static final int ANCHO = 600;
    private static final int ALTO = 400;
    private JPanel seccionI, seccionD; 
    private JButton enviar, mColor, mColor2;
    private JComboBox reinoOpc, color1, color2, modo;
    private JTextField nombre1, nombre2, reino1, reino2;
    private boolean enviarDatos = false;
    
    public Cuestionario() {
        setTitle("Seleccione su color preferido");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();    
        setVisible(true);
    }
    public void createContents() {

        seccionI = new JPanel(new GridLayout(9, 1));
        seccionI.add(new JLabel(" "));
        seccionI.add(new JLabel("Ingrese su nombre de usuario"));
        seccionI.add(nombre1 = new JTextField(10));
        seccionI.add(new JLabel("Ingrese el nombre de su reino"));
        seccionI.add(reino1 = new JTextField(10));
        seccionI.add(new JLabel("Seleccione su color preferido"));
        color1 = new JComboBox(colores);
        color1.setSelectedIndex(0);
        color1.addActionListener(new MColor());
        mColor= new JButton("");
        mColor.setBackground(Color.YELLOW);
        JPanel selecColor= new JPanel(new GridLayout(1, 2));
        selecColor.add(color1);
        selecColor.add(mColor);
        seccionI.add(selecColor); 
        
        seccionD = new JPanel(new GridLayout(9, 1));
        seccionD.add(new JLabel(" "));
        seccionD.add(new JLabel("Ingrese su nombre de usuario", SwingConstants.CENTER));
        seccionD.add(nombre2 = new JTextField(10));
        seccionD.add(new JLabel("Ingrese el nombre de su reino"));
        seccionD.add(reino2 = new JTextField(10));
        seccionD.add(new JLabel("Seleccione su color preferido"));
        color2= new JComboBox(colores);
        color2.addActionListener(new MColor());
        color1.setSelectedIndex(0);
        mColor2 = new JButton("");
        mColor2.setBackground(Color.YELLOW);
        JPanel selecColor2 = new JPanel(new GridLayout(1, 2));
        selecColor2.add(color2);
        selecColor2.add(mColor2);
        seccionD.add(selecColor2);

        enviar = new JButton("Enviar");
        JPanel sup= new JPanel(new GridLayout(1,3));
        JPanel inf= new JPanel(new GridLayout(5,3));
        inf.add(new JLabel("Modo de Juego  ", SwingConstants.RIGHT));
        modo = new JComboBox(modos);
        inf.add(modo);
        JPanel ayudaPanel= new JPanel(new GridLayout(1,5));
        JButton ayuda = new JButton("?");
        for(int i=1; i<= 4; i++) {
            if(i == 2)
        	ayudaPanel.add(ayuda);
            else
        	ayudaPanel.add(new JLabel(" "));
        }
        ayuda.addActionListener(new mostrarAyuda());
        inf.add(ayudaPanel);
        for(int i= 1; i<= 4; i++)
        	inf.add(new JLabel(""));
        inf.add(enviar);
        for(int i= 1; i<= 4; i++)
            inf.add(new JLabel(""));
        JLabel espacio = new JLabel(" ");
        espacio.setPreferredSize(new Dimension(15,20));
        sup.add(seccionI);
        sup.add(espacio);
        sup.add(seccionD);
        JPanel titulo= new JPanel();
        titulo.add(new JLabel("INGRESE DATOS DE LOS JUGADORES"));
        add(titulo,BorderLayout.NORTH);
        add(sup,BorderLayout.CENTER);
        add(new JLabel("     "),BorderLayout.WEST);
        add(new JLabel("     "),BorderLayout.EAST);
        add(inf, BorderLayout.SOUTH);
        enviar.addActionListener(new Enviar());
    }
   
    private class MColor implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == color1)
                mColor.setBackground(COLORES[color1.getSelectedIndex()]);
            else
                mColor2.setBackground(COLORES[color2.getSelectedIndex()]);
        }
    }
    private class Enviar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int ind1 = color1.getSelectedIndex();
            int ind2 = color2.getSelectedIndex();
            boolean condicion = (!(nombre1.getText().equals("")) && !(nombre2.getText().equals("")) && !(reino1.getText().equals("")) && !(reino2.getText().equals("")));
            if ((ind1 != ind2) && condicion) {
                JOptionPane.showMessageDialog(null, "Datos Enviados");
                enviarDatos = true;             
                setVisible(false);
                new Game(COLORES[ind1], COLORES[ind2], nombre1.getText(), nombre2.getText(), reino1.getText(), reino2.getText(), modo.getSelectedIndex());
            }
            else 
                JOptionPane.showMessageDialog(null, "Ingrese y/o seleccione datos correctos");           
        }
    }   
    private class mostrarAyuda implements ActionListener {
        public void actionPerformed(ActionEvent e) {            
        	JOptionPane.showMessageDialog(null, ayudas[modo.getSelectedIndex()]);           
        }
    }   
    public static void main(String args[]) {
        new Cuestionario();
    }
}
