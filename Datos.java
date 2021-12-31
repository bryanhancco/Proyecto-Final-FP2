import java.awt.*;
interface Datos {
	Color c1 = null;
	Color c2 = null;
	String nombre1= "", nombre2= "";

    String[] modos= {"Clásico","Turbo","Realista"};
	final Color[] COLORES= {Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE
			, Color.MAGENTA, Color.PINK, Color.ORANGE};
	final String[] colores= {"Amarrillo", "Rojo", "Green", "Blue"
			, "Magenta", "Rosa", "Naranja"};
	String[] consejos= {"Observa indicador de númerode minas antes de mover",
			"Consejo2",
			"Consejo3",
			"Consejo4",
			"Consejo5",
			"Consejo6",
			"Consejo7"};
	
	String[] ayudas= 
		{"Gana el que lleve cinco soldados a la \nfortaleza enemiga",
			"Similar al clásico pero solo es necesario \nllevar un soldado",
			"Los soldados ya no reaparecen y se \npuede perder por falta de unidades"};
}
