import java.util.*;
public class RunAndDestroy {
	public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String conf;
        Game juego = new Game();
        do {
        	juego.imprimirTablero();
        	System.out.print("---> ¿Desea iniciar otro juego?(S/N): ");
        	conf= sc.next();
        }
        while(conf.equals("S"));
		
	}
}
