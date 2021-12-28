
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
   
public class Cuestionario extends JFrame {
    
    private static final int ANCHO = 300;
    private static final int ALTO = 400;
    private JPanel text1; 
    private JPanel text2; 
    private JButton enviar;
    private JComboBox reinoOpc;
    private JRadioButton azul;
    private JRadioButton rojo;
    private JRadioButton verde;
    private JRadioButton amarillo;
    private boolean enviarDatos = false;
    
    
    public Cuestionario() {
        setTitle("Seleccione su color preferido");
        setSize(ANCHO, ALTO);
        setLayout(new GridLayout(3, 1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();    
        setVisible(true);
    }
   public void createContents() {    
       String[] reinos = {"Inglaterra", "Sacro Imperio Romano-Germánico", "Francia", "Castilla - Aragón", "Moros"};     
       
       text1 = new JPanel(new GridLayout(5, 1));
       text1.add(new JLabel("Seleccione su color preferido"));
       ButtonGroup rb = new ButtonGroup();      
       azul = new JRadioButton("Azul");
       rb.add(azul);
       rojo = new JRadioButton("Rojo");
       rb.add(rojo);
       verde = new JRadioButton("Verde");
       rb.add(verde);
       amarillo = new JRadioButton("Amarillo");
       rb.add(amarillo);
       
       JPanel tex = new JPanel(new FlowLayout(FlowLayout.LEFT));
       tex.add(azul);
       tex.add(generarColores(Color.BLUE));
       text1.add(tex);
       tex = new JPanel(new FlowLayout(FlowLayout.LEFT));
       tex.add(rojo);
       tex.add(generarColores(Color.RED));
       text1.add(tex);
       tex = new JPanel(new FlowLayout(FlowLayout.LEFT));
       tex.add(verde);
       tex.add(generarColores(Color.GREEN));
       text1.add(tex);
       tex = new JPanel(new FlowLayout(FlowLayout.LEFT));
       tex.add(amarillo);
       tex.add(generarColores(Color.YELLOW));
       text1.add(tex);
       
       text2 = new JPanel(new GridLayout(2, 1));
       reinoOpc = new JComboBox(reinos);
       text2.add(new JLabel("Seleccione el reino que prefiera"));
       text2.add(reinoOpc);
       
       enviar = new JButton("Submit");
       add(text1);
       add(text2);
       add(enviar);
       enviar.addActionListener(new Listener());
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
   private class Listener implements ActionListener {
       public void actionPerformed(ActionEvent e) {
           if (azul.isSelected() || rojo.isSelected() || verde.isSelected() || amarillo.isSelected()) {
                JOptionPane.showMessageDialog(null, "Datos Enviados");
                enviarDatos = true;
                setVisible(false);                 
           }
           else {
               JOptionPane.showMessageDialog(null, "Alguno de esos datos ya ha sido seleccionado, seleccione otro");
           }
       }
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
