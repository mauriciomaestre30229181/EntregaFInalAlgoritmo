package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {

    private static final String LOG_DIRECTORY = "src/logs/";
    private static final String LOG_FILE_NAME = "Logs.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String msg) {
        verifyLogPath();
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] INFO: " + msg + "\n";
        FileManager.writeInFile(LOG_DIRECTORY + LOG_FILE_NAME, logEntry);
    }

    public static void logWarn(String msg) {
        verifyLogPath();
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] WARN: " + msg + "\n";
        FileManager.writeInFile(LOG_DIRECTORY + LOG_FILE_NAME, logEntry);
    }

    public static void logError(String msg) {
        verifyLogPath();
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] ERROR: " + msg + "\n";
        FileManager.writeInFile(LOG_DIRECTORY + LOG_FILE_NAME, logEntry);
    }

    private static void verifyLogPath () {
        if (!Files.exists(Paths.get(LOG_DIRECTORY))) {
            try {
                Files.createDirectory(Paths.get(LOG_DIRECTORY));
            } catch (IOException e) {
                LoggerUtil.logError("Error al crear directorio de logs: " + e.getMessage());
            }
        }
    }
}