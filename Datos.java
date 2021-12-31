import java.awt.*;
interface Datos {
	Color c1 = null;
	Color c2 = null;
	String nombre1= "", nombre2= "";

    String[] modos= {"Clásico","Turbo","Realista"};
	final Color[] COLORES= {Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE
			, Color.MAGENTA, Color.PINK, Color.ORANGE};
	final String[] colores= {"Amarillo", "Rojo", "Verde", "Azul"
			, "Magenta", "Rosa", "Naranja"};
	String[] consejos= {"Observa el indicador de número de minas antes de mover",
			"Este atento, puede haber escudos cerca de usted",
			"Procure llegar a la torre del enemigo",
			"Si consigue un escudo, la proxima mina no le afectara",
			"Dependiendo del modo, necesitara más soldados para ganar",
			"Los números le indican cuantas minas tiene alrededor\n ese cuadrante",
			"Sea cauteloso, evite las luchas con otros soldados"};
	
	String[] ayudas= 
		{"Gana el que lleve cinco soldados a la \nfortaleza enemiga",
			"Similar al clásico pero solo es necesario \nllevar un soldado",
			"Los soldados ya no reaparecen y se \npuede perder por falta de unidades"};
}
