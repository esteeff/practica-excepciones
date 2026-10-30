import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProcesadorCalificaciones {

    public static void main(String[] args) {
      
        try {
            procesarArchivo("calificaciones.txt");
        } catch (IOException e) {
            System.err.println("No fue posible procesar el archivo.");
        }
    }

    public static void procesarArchivo(String nombreArchivo) throws IOException {
        
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                
                if (linea.isBlank()) {
                    System.err.println("Argumento inválido: La línea no puede estar vacía.");
                    continue; 
                }

                try {
                    int calificacion = Integer.parseInt(linea.trim());
                    validarCalificacion(calificacion);
                    System.out.println("Calificación válida: " + calificacion);
                    
                } catch (NumberFormatException e) {
                    System.err.println("Formato numérico inválido: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.err.println("Argumento inválido: " + e.getMessage());
                }
            }
        }
    }

    public static void validarCalificacion(int calificacion) {
        if (calificacion < 0 || calificacion > 100) {
            throw new IllegalArgumentException("Calificación fuera de rango: " + calificacion);
        }
    }
}