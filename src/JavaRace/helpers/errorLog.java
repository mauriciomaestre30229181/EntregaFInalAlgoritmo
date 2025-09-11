package JavaRace.helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import JavaRace.config.Storage;


public class errorLog {
    private static final String LOG_FILE = "errores.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logError(String errorMessage) {
        try {
            // Aseguramos que el directorio existe
            Storage.ensureStorageDirectoryExists();

            // Obtenemos la ruta completa del archivo de log
            String logPath = Storage.getStoragePath() + System.getProperty("file.separator") + LOG_FILE;

            // Escribimos en el archivo
            try (PrintWriter writer = new PrintWriter(new FileWriter(logPath, true))) {
                String timestamp = LocalDateTime.now().format(FORMATTER);
                writer.printf("[%s] ERROR: %s%n", timestamp, errorMessage);
            }
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el archivo de log: " + e.getMessage());
            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
                String timestamp = LocalDateTime.now().format(FORMATTER);
                writer.printf("[FALLBACK][%s] ERROR (no storage): %s%n", timestamp, errorMessage);
            } catch (IOException ex) {
                System.err.println("Error crítico: no se pudo escribir en ningún archivo de log: " + ex.getMessage());
            }
        }
    }
}