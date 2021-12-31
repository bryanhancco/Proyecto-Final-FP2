

public class Torre {
    private int vidaTorre;
    private boolean torreEnPie;
    
    public Torre() {
        vidaTorre = 3;
        torreEnPie = true;
    }
    public void setRealista() {
        vidaTorre = 1;
    }
    public void torreAtacada() {
        vidaTorre--;
        if (vidaTorre == 0 )
            torreEnPie = false;       
    }
    public int getVidaTorre() {
        return vidaTorre;
    }   
    public boolean enPie() {
    	return torreEnPie;
    }
}
