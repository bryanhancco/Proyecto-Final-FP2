public class Soldado {
	private boolean tieneEscudo;
	private String nombre, ubicacion;
	private int team;
	
	public Soldado(int team, int num, String ubicacion) {
		nombre= "Soldado" + team + "-" + num;
		this.team= team;
		this.ubicacion= ubicacion;
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
}
