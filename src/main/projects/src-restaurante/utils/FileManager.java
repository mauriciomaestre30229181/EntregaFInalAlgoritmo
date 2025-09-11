package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

    private static final String INVOICE_DIRECTORY = "src/data/";
    private static final String INVOICE_FILE_NAME = "Invoice";
    private static final String RESULTS_DIRECTORY = "src/results/";
    private static final String RESULTS_FILE_NAME = "Result";

    public static String getInvoiceDirectory () {
        verifyFilePath(INVOICE_DIRECTORY);
        return INVOICE_DIRECTORY;
    }

    public static String getResultsDirectory () {
        verifyFilePath(RESULTS_DIRECTORY);
        return RESULTS_DIRECTORY;
    }

    public static String getInvoiceFileName () {
        verifyFilePath(INVOICE_DIRECTORY);
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        int rand = (int) (Math.random() * 1000) + 1;

        return INVOICE_DIRECTORY + INVOICE_FILE_NAME + "_" + current.format(formatter) + "_" + "Serial" + rand + ".txt";
    }

    public static String getResultFileName (String criteria, String name) {
        verifyFilePath(RESULTS_DIRECTORY);
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        int rand = (int) (Math.random() * 1000) + 1;

        return RESULTS_DIRECTORY + RESULTS_FILE_NAME + "_" + criteria + "_" + name + "_" + current.format(formatter) + "_" + "Serial" + rand + ".txt";
    }

    public static void writeInFile (String path, String content) {
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(content);
        } catch (IOException e) {
            LoggerUtil.logError("Error al escribir en archivo " + path + ": " + e.getMessage());
        }
    }

    public static File[] getFilesInDirectory (String path) {
        File directory = new File(path);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.startsWith(INVOICE_FILE_NAME) && name.endsWith(".txt"));
            return files;
        }

        return null;
    }

    public static void listFiles (File[] invoices) {
        System.out.println("\n=== FACTURAS ===");
        for (int i = 0; i < invoices.length; i++) {
            System.out.printf("%d. %s\n", i + 1, invoices[i].getName());
        }
    }

    public static String getTextFromFile (File file) {
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder content = new StringBuilder();

                while ((line = reader.readLine()) != null)
                    content.append(line).append(System.lineSeparator());

                return content.toString();
            } catch (IOException e) {
                String fileName = file.getName();
                LoggerUtil.logError("Error al extraer contenido del archivo " + fileName + ": " + e.getMessage());
            }
        }

        return "";
    }

    private static void verifyFilePath (String path) {
        if (!Files.exists(Paths.get(path))) {
            try {
                Files.createDirectory(Paths.get(path));
            } catch (IOException e) {
                LoggerUtil.logError("Error al crear directorio de comandas: " + e.getMessage());
            }
        }
    }
}