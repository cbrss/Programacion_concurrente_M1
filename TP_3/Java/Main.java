import java.util.Scanner;
import java.util.concurrent.Semaphore;

// error al ejecutar dos veces H

public class Main {
    
    private static final int CAP_BANO = 1;
    static Semaphore sem_acceso_bano = new Semaphore(CAP_BANO);;
    private static int contador = 0;
    
    public static void main(String[] args) {

        //Main.sem_acceso_bano = new Semaphore(CAP_BANO);

        Scanner scanner = new Scanner(System.in);

        Thread acceso_hombre;
        Thread acceso_mujer;

        while (true) {
            System.out.println("'H' , 'M' o 'salir':");
            String input = scanner.nextLine();
            


            if (input.equals("H")) {
          
                acceso_hombre = new Thread(new Bath("H"));
                acceso_hombre.start();
   
            } else if (input.equals("M")) {

                acceso_mujer = new Thread(new Bath("M"));
                acceso_mujer.start();
 
            } else if (input.equals("salir")) {
                break;
            } else {
                System.out.println("Entrada no válida.");
            }
        }

        scanner.close();
    }

    public static synchronized int getContador(){
        return contador;
    }
    public static synchronized void incrementarContador(){
        contador++;
    }
    public static synchronized void decrementarContador(){
        contador--;
    }

    public static synchronized void P(String name){
        
        try {
            sem_acceso_bano.acquire();
        } catch(InterruptedException e){
            System.out.println(e);
        }
        
    }

    public static synchronized void V(String name){
        sem_acceso_bano.release();
    }

}


