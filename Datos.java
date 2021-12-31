import java.awt.*;
interface Datos {
	
    String[] modos = {"Clásico","Turbo","Realista"};
    Color[] COLORES = {Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE
			, Color.MAGENTA, Color.PINK, Color.ORANGE};
    String[] colores = {"Amarillo", "Rojo", "Verde", "Azul"
			, "Magenta", "Rosa", "Naranja"};
    String[] consejos = {"Observa el indicador de número de minas antes de mover",
			"Este atento, puede haber escudos cerca de usted",
			"Procure llegar a la torre del enemigo",
			"Si consigue un escudo, la proxima mina no le afectara",
			"Dependiendo del modo, necesitara más soldados para ganar",
			"Los números le indican cuantas minas tiene alrededor\n ese cuadrante",
			"Sea cauteloso, evite las luchas con otros soldados"};
	
    String[] ayudas =   {"Gana el que lleve cinco soldados a la \nfortaleza enemiga",
			"Similar al clásico pero solo es necesario \nllevar un soldado",
			"Ya no aparecen escudos y es necesario\nllevar un soldado"};
}
