package cinema.Store;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logs {
    private static final String LOG_FILE = "cinemaErrors.log";
    
    public static void errorLog(String message) {
        if (message == null) {
            message = "Mensaje de error null";
        }
        
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = "[" + time + "] ERROR: " + message + "\n";
        
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry);
        } catch (IOException e) {
            System.out.println("No se pudo escribir en el archivo de log: " + e.getMessage());
        }
    }
    
    public static void logException(String context, Exception e) {
        if (context == null) context = "Contexto no proporcionado";
        if (e == null) {
            errorLog(context + " - Excepción null");
            return;
        }
        
        errorLog(context + " - " + e.getClass().getSimpleName() + ": " + e.getMessage());
        
        if (e instanceof NullPointerException || e instanceof IOException) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            errorLog("Stack Trace:\n" + sw.toString());
        }
    }
}