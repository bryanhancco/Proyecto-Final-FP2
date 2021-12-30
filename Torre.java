
public class Torre {
    private int vidaTorre;
    private boolean torreEnPie;
    
    public Torre() {
        vidaTorre = 1;
        torreEnPie = true;
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
