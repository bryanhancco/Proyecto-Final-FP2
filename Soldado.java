import java.util.*;
public class Soldado {
	private boolean tieneEscudo;
	private String nombre, ubicacion;
	private int team;
	
	public Soldado(int team, int num, String ubicacion) {
		nombre= "Soldado" + team + "-" + num;
		this.team= team;
		this.ubicacion= ubicacion;
	}
	
	public void setUbicacion(String ub) {
		ubicacion= ub;
	}
	
	public void setEscudo() {
		tieneEscudo= true;
	}
	
	public void perderEscudo() {
		tieneEscudo= false;
	}
	
	public boolean tieneEscudo() {
		return tieneEscudo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getTeam() {
		return team;
	}
	
	public int getNfila() {
		return Integer.parseInt(ubicacion.substring(0, ubicacion.length() - 1));
	}
	
	public int getNcolumna() {
		String letras= "ABCDEFGHIJKL";
		int large= ubicacion.length();
		String columna = ubicacion.substring(large - 1, large);
		return letras.indexOf(columna) + 1;		
	}
	
	public int getNumero() {
		int i= nombre.indexOf("-") + 1;
		int num = Integer.parseInt(nombre.substring(i, nombre.length()));
		return num;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
	
	public String lucha(Soldado s) {
		int r= (int)(Math.random()* 2);
		if(r== 1)
			return getUbicacion();
		else
			return s.getUbicacion();
	}
	
	public void mover (HashMap<String, Soldado> map, String ub) {
		if(map.containsKey(ub) && map.get(ub).getTeam() == team) {
			System.out.print("Movimiento no permitido");
		}
		else if(map.containsKey(ub))
			lucha(map.get(ub));
		else
			setUbicacion(ub);
	}
}
