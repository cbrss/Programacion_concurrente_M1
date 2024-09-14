public class Bath implements Runnable {


    private String genero;

    public Bath(String genero ){
        this.genero = genero;

    }

    public void usarBano() {
        Main.P("acceso_bano");
        Main.incrementarContador();
        System.out.println("El hilo " + this.genero + " está usando el baño. cantidad: " + Main.getContador());

        try {
            System.out.println("entro");
            Thread.sleep(2000); // Simula el tiempo que el hilo está usando el baño
            System.out.println("termino ejecutar sleep");
        } catch (InterruptedException e) {
            System.out.println("entro");
            e.printStackTrace();
        } finally{
            System.out.println("El hilo " + this.genero + " termino de usar el baño. cantidad: " + Main.getContador());
            Main.decrementarContador();
            Main.V("acceso_bano");
        }

        

    }



    @Override
    public void run() {
        usarBano();
    }
}

