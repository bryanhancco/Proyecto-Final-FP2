import java.util.*;
public class Ejercito {
	private HashMap<String, Soldado> soldados= new HashMap<String, Soldado>();
	private int team;
	
	public Ejercito(int c, int team) {
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
}
