
import java.util.Scanner;
public class RunAndDestroy {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String conf;
        do {
            Game juego = new Game();
            juego.iniciarJuego();
            System.out.print("---> ¿Desea iniciar otro juego?(S/N): ");
            conf= sc.next();
        }
        while(conf.equals("S"));
    }
}
