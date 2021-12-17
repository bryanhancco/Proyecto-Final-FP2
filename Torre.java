public class Torre {
    private int vidaTorre;
    private boolean torreEnPie;
    
    public Torre() {
        vidaTorre = 5;
        torreEnPie = true;
    }
    public void torreAtacada() {
        vidaTorre--;
    }
    public void torreDestruida() {
        System.out.println("La torre ha sido destruida");
        torreEnPie = false;
    }
    public int getVidaTorre() {
        return vidaTorre;
    }
}
