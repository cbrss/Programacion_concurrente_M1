import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

// hay que compilar javac CharacterCounter.java

public class Main {
	public static void main(String[] args) {
        /// Valido los parametros
		int total_threads = 0;
        String file_path = "";

		if (args.length >2) {
			System.out.println("Cantidad de parametros incorrecto.");
			return;
		} 
		else {
            file_path = args[0];
            try{
                total_threads = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("El 2do parametro tiene que ser un numero.");
                return;
            }
			
		}
		if (total_threads < 1) {
			System.out.println("La cantidad de threads tiene que ser igual o mayor a 1.");
			return;
		}

        long t1 = System.currentTimeMillis();

        /// Creo y ejecuto los hilos
        List<String> lines = new ArrayList<>();

        read_file(file_path, lines);
        int cant_lines = 0;
        cant_lines = lines.size();
        if (total_threads > cant_lines) {   // Si la cantidad de hilos es mayor que la cantidad de lineas del archivo, entonces limito los hilos a la cantidad de lineas
            total_threads = cant_lines;
        }
		
		Thread[] threads = new Thread[total_threads];
		CharacterCounter[] counters = new CharacterCounter[total_threads];
		
		int lines_per_thread = (cant_lines/total_threads);
        int start = 0;
        int end = 0;
		for (int i = 0; i < total_threads; i++ ) {
			start = i * lines_per_thread;
            end = (i == total_threads -1)? cant_lines : start + lines_per_thread;
            counters[i] = new CharacterCounter(lines, start, end);
            threads[i] = new Thread(counters[i]);
            threads[i].start();
            System.out.println("Hilo: " + i + " ejecutando: " + start + " hasta " + end);
		}

        /// Libero recursos de los hilos
        try{
            for (int i = 0; i < total_threads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        /// Conteo final
        int rt = 0;
        for (CharacterCounter counter : counters) {
            rt += counter.getRp();
        }

        long t2 = System.currentTimeMillis();

        long tt = (t2 - t1);

        System.out.println("Total: " + rt + " calculado en: " + tt + "ms");
	}

	private static void read_file(String name, List<String> lines) {
		File fp = null;
		Scanner sc = null;
        String line = "";

		try{
			fp = new File(name);
			sc = new Scanner(fp);
			
			while (sc.hasNextLine()) {
				line = sc.nextLine();
                if(!line.trim().isEmpty()) {
                    lines.add(line);
                }
				
			}
		} catch( Exception e){
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
}
