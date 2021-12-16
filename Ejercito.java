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
}
