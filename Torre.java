import javax.swing.JOptionPane;

public class Torre {
    private int vidaTorre;
    private boolean torreEnPie;
    
    public Torre() {
        vidaTorre = 1;
        torreEnPie = true;
    }
    public void torreAtacada() {
        vidaTorre--;
    }
    
    public void torreDestruida() {
        JOptionPane.showMessageDialog(null, "---> La torre ha sido destruida ");
        torreEnPie = false;
    }
    public int getVidaTorre() {
        return vidaTorre;
    }
    
    public boolean enPie() {
    	return torreEnPie;
    }
}
