import java.util.concurrent.Semaphore;

public class Bath implements Runnable {

    // ERROR NO VUELVEN A SUBIR EL VALOR DEL SEMAFORO SEM_ACCESO_BANO
    // solucion: no hacerlo estatico, posible deadlock al usar syncro

    // ERROR: ejecutar H-H-M-M con cap=2
    // al no usar semaforo y solo el while, hace que solo ejecute 1 M
    // TODO: solucion: el while tiene que ser un semaforo pero nose 
    private static final int TIME_SLEEP = 3000;
    private static final int CAP_BANO = 2;


    public static int contador_mujeres = 0;
    public static int contador_hombres = 0;
    
    public static String genero_actual = "S";
    
    public static Semaphore sem_genero_h = new Semaphore(1);
    public static Semaphore sem_genero_m = new Semaphore(1);
    public static Semaphore sem_acceso_bano = new Semaphore(CAP_BANO);

    private String genero;
    private int nombre;

    public Bath(String genero, int nombre ){
        this.genero = genero;
        this.nombre = nombre;

    }

    public void usarBano() {



        /// esto es el semaforo de genero, metodo con sync talvez
        /// si ejecuto H y despues M, M no entra
        while (true){
            
           
            if (genero_actual.equals("S")) {
                System.out.println("Entro por default");
                break;
            }
            else if (genero_actual.equals("H") && this.genero.equals("H") ) {
                System.out.println("turno hombre");
                break;
            } else if (genero_actual.equals("M") && this.genero.equals("M")){
                System.out.println("turno mujer");
                break;
            }
            try {
                System.out.println("Esperando: " + this.nombre);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        P("acceso_bano");
        System.out.println("entrando bano: " + this.nombre);

                           ////

        if (this.genero.equals("H")) {
            incrementar_contador_hombres();
        } else {
            incrementar_contador_mujeres();
        }

        Main.actualizar_pantalla(this.genero);
        try {
            Thread.sleep(TIME_SLEEP); // Simula el tiempo que el hilo esta usando el baño ////

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.genero.equals("H")) {
            decrementar_contador_hombres();
    
        } else {
            decrementar_contador_mujeres();
        }

        System.out.println("Salio del bano: " + this.nombre);
        Main.actualizar_pantalla(this.genero);
        V("acceso_bano");
    }

    @Override
    public void run() {
        usarBano();
    }

    public void P(String name){
        try {
            if (name.equals("sem_genero_h")) {
                sem_genero_h.acquire();
            }
            else if (name.equals("sem_genero_m")){
                sem_genero_m.acquire();
            } else{
                sem_acceso_bano.acquire();
            }
            
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        
    }

    public void V(String name){
        if (name.equals("sem_genero_h")) {
            sem_genero_h.release();
        }
        else if (name.equals("sem_genero_m")){
            sem_genero_m.release();
        } else{
            sem_acceso_bano.release();
        }
    }

    public static synchronized int get_contador_hombres(){
        return contador_hombres;
    }
    public static synchronized void incrementar_contador_hombres(){
        contador_hombres++;
        set_genero("H");
        
    }
    // syncro solo se hace a nivel objeto, pero si se utiliza static, entonces es a nivel clase
    public static void decrementar_contador_hombres(){
        contador_hombres--;
        
        if (contador_hombres == 0){
          //  V("sem_genero_h");
            set_genero("S");
        }
    }

    public static synchronized int get_contador_mujeres(){
        return contador_mujeres;
    }
    public static synchronized void incrementar_contador_mujeres(){
        contador_mujeres++;
        set_genero("M");
    }
    public static synchronized void decrementar_contador_mujeres(){
        contador_mujeres--;
        if (contador_mujeres == 0){
           // V("sem_genero_m");
            set_genero("S");
        }
    }
    
    public static synchronized void set_genero(String genero) {
        genero_actual = genero;
    }
    public static synchronized String get_genero() {
        return genero_actual;
    }
}

