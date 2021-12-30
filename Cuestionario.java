import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
   
public class Cuestionario extends JFrame implements Datos{    
    private static final int ANCHO = 500;
    private static final int ALTO = 400;
    private JPanel seccionI, seccionD; 
    private JButton enviar;
    private JComboBox reinoOpc, color1, color2;
    private JRadioButton azul;
    private JRadioButton rojo;
    private JRadioButton verde;
    private JRadioButton amarillo;
    private JTextField nombre1, nombre2, reino1, reino2;
    private boolean enviarDatos = false;
    JButton mColor, mColor2;
    private Jugador jug1, jug2;
    
    public Cuestionario() {
        setTitle("Seleccione su color preferido");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();    
        setVisible(true);
    }
    
    public String[] cDisponibles(){
        String[] array= new String[COLORES.length - 2];
        if(color1 == null && color2== null)
            return colores;
        int i= 0;
        JOptionPane.showMessageDialog(null, "Usando cdisponibles");
        for(String c: colores) {
            if(!color1.getSelectedItem().equals(c) && !color2.getSelectedItem().equals(c)) {
                array[i]= colores[i];
                i++;
            }
        }
        return array;
    }
    
    public void createContents() {
        //sup= new JPanel(new GridLayout(1,2)); 

        seccionI = new JPanel(new GridLayout(6, 1));
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
        
        seccionD = new JPanel(new GridLayout(6, 1));
        seccionD.add(new JLabel("Ingrese su nombre de usuario"));
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
        //sup.add(text1);
        //sup.add(text2);
        //add(sup, BorderLayout.CENTER);
        add(seccionI, BorderLayout.EAST);
        add(seccionD, BorderLayout.WEST);
        add(enviar, BorderLayout.SOUTH);
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

    public Color getColor() {
        if (azul.isSelected()) 
            return Color.BLUE;
        else if (rojo.isSelected()) 
            return Color.RED;
        else if (verde.isSelected()) 
            return Color.GREEN;
        else if (amarillo.isSelected()) 
            return Color.YELLOW;
        return Color.BLACK;
    }
    public String getReino() {
        return reinoOpc.getSelectedItem().toString();
    }
    public boolean getEstado() {
        return enviarDatos;
    }
    private class Enviar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int ind1 = color1.getSelectedIndex();
            int ind2 = color2.getSelectedIndex();
            boolean condicion = (!(nombre1.getText().equals("")) && !(nombre2.getText().equals("")) && !(reino1.getText().equals("")) && !(reino2.getText().equals("")));
            if ((ind1 != ind2) && condicion) {
                JOptionPane.showMessageDialog(null, "Datos Enviados");
                enviarDatos = true;
                jug1 = new Jugador(COLORES[ind2], nombre1.getText(), reino1.getText());
                jug2 = new Jugador(COLORES[ind1], nombre2.getText(), reino2.getText());
                DatosJugadores.actualizarDatos(jug1, jug2);
                setVisible(false);
                new Game(COLORES[ind2], COLORES[ind1]);
            }
            else 
                JOptionPane.showMessageDialog(null, "Ingrese y/o seleccione datos correctos");
            
        }
    }
    public void ingresarJugadores(String nJ, String nR, Color c) {
        Jugador jug = new Jugador(c, nJ, nR);
    }
    public JTextField generarColores(Color c) {
        JTextField color = new JTextField(3);
        color.setBackground(c);
        color.setEditable(false);
        return color;
    }
    public static void main(String args[]) {
        new Cuestionario();
    }
}

