package JavaRace.process;

import JavaRace.config.Storage;
import JavaRace.helpers.Validate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class BusquedaService {

    public void iniciarBusqueda(Scanner scanner) {
        System.out.println("\n=== BÚSQUEDA EN REPORTES DE CARRERA ===");
        String termino = Validate.validarTexto(scanner, "Ingrese el término de búsqueda (e.g., nombre de piloto o patrocinador): ");

        File storageDir = new File(Storage.getStoragePath());
        File[] reportes = storageDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (reportes == null || reportes.length == 0) {
            System.out.println("No se encontraron reportes en el directorio de almacenamiento.");
            return;
        }

        System.out.println("\n--- Búsqueda con Recursión de Cola (Tail Recursion) ---");
        System.out.println("Muestra todas las líneas que contienen el término.");
        for (File reporte : reportes) {
            System.out.println("Buscando en: " + reporte.getName());
            buscarRecursivoFinal(reporte.toPath(), termino);
        }

        System.out.println("\n--- Búsqueda con Recursión de Pila (Non-Tail Recursion) ---");
        System.out.println("Encuentra la primera ocurrencia y muestra las 5 líneas siguientes.");
        boolean encontrado = false;
        for (File reporte : reportes) {
            if (!encontrado) {
                System.out.println("Buscando en: " + reporte.getName());
                encontrado = buscarRecursivoNoFinal(reporte.toPath(), termino);
            }
        }
    }

    // Implementación de Recursión Final (Tail Recursion)
    private void buscarRecursivoFinal(Path filePath, String termino) {
        try {
            String[] lineas = Files.readAllLines(filePath).toArray(new String[0]);
            encontrarYMostrarLineas(lineas, 0, termino.toLowerCase());
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + filePath + ": " + e.getMessage());
        }
    }

    private void encontrarYMostrarLineas(String[] lineas, int index, String termino) {
        if (index >= lineas.length) {
            return; // Caso base
        }
        if (lineas[index].toLowerCase().contains(termino)) {
            System.out.println("  -> " + lineas[index]);
        }
        encontrarYMostrarLineas(lineas, index + 1, termino); // La llamada recursiva es la última operación
    }

    // Implementación de Recursión NO Final (Non-Tail Recursion)
    private boolean buscarRecursivoNoFinal(Path filePath, String termino) {
        try {
            String[] lineas = Files.readAllLines(filePath).toArray(new String[0]);
            int lineaEncontrada = encontrarPrimeraOcurrencia(lineas, 0, termino.toLowerCase());

            if (lineaEncontrada != -1) {
                System.out.println("  -> Ocurrencia encontrada en la línea " + (lineaEncontrada + 1));
                // Imprimir contexto después de la llamada recursiva (característica de no final)
                imprimirContexto(lineas, lineaEncontrada);
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + filePath + ": " + e.getMessage());
        }
        return false;
    }

    private void imprimirContexto(String[] lineas, int startIndex) {
        System.out.println("Contexto (líneas siguientes):");
        for (int i = 0; i < 5 && (startIndex + i) < lineas.length; i++) {
            System.out.println("     " + lineas[startIndex + i]);
        }
    }

    private int encontrarPrimeraOcurrencia(String[] lineas, int index, String termino) {
        if (index >= lineas.length) {
            return -1; // Caso base: no encontrado
        }

        // Se hace una operación (la comparación) antes de la llamada recursiva
        if (lineas[index].toLowerCase().contains(termino)) {
            return index; // Encontrado
        }

        // El valor de retorno depende del resultado de la llamada anidada.
        // Esto hace que la pila de llamadas deba mantenerse.
        return encontrarPrimeraOcurrencia(lineas, index + 1, termino);
    }
}