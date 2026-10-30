import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProcesadorCalificaciones {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java ProcesadorCalificaciones <archivo>");
            return;
        }

        String nombreArchivo = args[0];

        try {
            double promedio = calcularPromedio(nombreArchivo);
            System.out.printf("Promedio de valores válidos: %.2f%n", promedio);
        } catch (IOException e) {
            System.err.println("No fue posible procesar el archivo: " + e.getMessage());
        } catch (SinDatosValidosException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static double calcularPromedio(String archivo) throws IOException, SinDatosValidosException {
        double suma = 0;
        int contador = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                
                if (linea.isBlank()) {
                    System.err.println("Dato ignorado: línea vacía.");
                    continue;
                }

                try {
                    int calificacion = Integer.parseInt(linea.trim());
                    validarCalificacion(calificacion);
                    
                    System.out.println("Calificación válida: " + calificacion);
                    suma += calificacion;
                    contador++;

                } catch (NumberFormatException e) {
                    System.err.println("Dato ignorado: " + linea);
                } catch (CalificacionInvalidaException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
      
        if (contador == 0) {
            throw new SinDatosValidosException("El archivo no contiene ninguna calificación válida.");
        }
        
        return suma / contador;
    }

    public static void validarCalificacion(int calificacion) throws CalificacionInvalidaException {
        if (calificacion < 0 || calificacion > 100) {
            throw new CalificacionInvalidaException("Calificación fuera de rango: " + calificacion);
        }
    }
}