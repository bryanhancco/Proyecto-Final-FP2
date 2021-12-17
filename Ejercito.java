import java.util.*;
public class Ejercito {
    private HashMap<String, Soldado> soldados= new HashMap<String, Soldado>();
    private Torre miTorre;
    private int team;
	
    public Ejercito(int c, int team) {
        miTorre = new Torre();
        this.team= team;
	String ub;
	for(int f= 0; f< 10; f++) {
            ub= Tablero.toKey(f+1, c);
            soldados.put(ub, new Soldado(team, f + 1, ub));
	}
    }
	
    public int getTeam(){
        return team;
    }
    public Torre getTorre() {
        return miTorre;
    }
    public HashMap<String, Soldado> getSoldados() {
        return soldados;
    }
    
    public ArrayList<Soldado> getLstOrdenada() {
        ArrayList<Soldado> lst= new ArrayList<Soldado>(); 
	for (Soldado s: soldados.values())
            lst.add(s);
            for(int i= 0; i< lst.size()- 1; i++)
		for(int j= 0; j< lst.size()- 1; j++)
                    if(lst.get(j).getNumero() > lst.get(j + 1).getNumero()){
			Soldado aux= lst.get(j);
			lst.set(j, lst.get(j + 1));
			lst.set(j + 1, aux);
                    }
	return lst;		
    }
    
    public void retirarSoldado(String k) {
    	System.out.println("Retirando " + k + " : " + soldados.get(k).getNombre());
    	soldados.remove(k);
    }
    
    public void moverSoldado(String k1, String k2) {
    	soldados.get(k1).setUbicacion(k2);
    	soldados.put(k2, soldados.remove(k1));
    }
}
