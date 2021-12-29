
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
   
public class Cuestionario extends JFrame implements Datos{
    
    private static final int ANCHO = 300;
    private static final int ALTO = 400;
    private JPanel text1, text2, text3, sup; 
    private JButton enviar;
    private JComboBox reinoOpc, color1, color2;
    private JRadioButton azul;
    private JRadioButton rojo;
    private JRadioButton verde;
    private JRadioButton amarillo;
    private boolean enviarDatos = false;
    JButton mColor, mColor2;
    
    
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
    	JOptionPane.showMessageDialog(null, "Usando cdispobibles");
    	for(String c: colores) {
    		if(!color1.getSelectedItem().equals(c) && !color2.getSelectedItem().equals(c)) {
    			array[i]= colores[i];
    			i++;
    		}
    	}
    	return array;
    }
    
   public void createContents() {
	   sup= new JPanel(new GridLayout(1,2));
       String[] reinos = {"Inglaterra", "Sacro Imperio Romano-Germánico", "Francia", "Castilla - Aragón", "Moros"};     
       
       text1 = new JPanel(new GridLayout(2, 1));
       text1.add(new JLabel("Seleccione su color preferido"));
       color1= new JComboBox(colores);
       color1.setSelectedIndex(0);
       color1.addActionListener(new MColor());
       mColor= new JButton("");
       mColor.setBackground(Color.YELLOW);
       JPanel selecColor= new JPanel(new GridLayout(1, 2));
       selecColor.add(color1);
       selecColor.add(mColor);
       text1.add(selecColor); 
       
       text2 = new JPanel(new GridLayout(2, 1));
       text2.add(new JLabel("Seleccione su color preferido"));
       color2= new JComboBox(colores);
       color2.addActionListener(new MColor());
       color1.setSelectedIndex(0);
       mColor2= new JButton("");
       //CORREGIR COLOR DE INICIO DE LA SEGUNDA LISTA
       mColor2.setBackground(Color.RED);
       JPanel selecColor2= new JPanel(new GridLayout(1, 2));
       selecColor2.add(color2);
       selecColor2.add(mColor2);
       text2.add(selecColor2); 
       
       text3 = new JPanel(new GridLayout(2, 1));
       reinoOpc = new JComboBox(reinos);
       text3.add(new JLabel("Seleccione el reino que prefiera"));
       text3.add(reinoOpc);
       
       enviar = new JButton("Submit");
       sup.add(text1);
       sup.add(text2);
       add(sup, BorderLayout.CENTER);
       add(enviar, BorderLayout.SOUTH);
       enviar.addActionListener(new Enviar());
   }
   
   private class MColor implements ActionListener {
       public void actionPerformed(ActionEvent e) {
    	   if(e.getSource()==color1)
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
           if (color1.getSelectedIndex() != color2.getSelectedIndex()) {
                JOptionPane.showMessageDialog(null, "Datos Enviados");
                enviarDatos = true;
                setVisible(false);
                new Game();
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

