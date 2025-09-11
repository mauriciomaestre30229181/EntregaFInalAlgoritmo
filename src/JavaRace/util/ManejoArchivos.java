package JavaRace.util;

import JavaRace.helpers.errorLog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import JavaRace.repositories.Participante;
import JavaRace.repositories.TiempoCarrera;
import JavaRace.config.Storage;

public class ManejoArchivos {
    private static final String NOMBRE_ARCHIVO_BASE = "resultados_carrera";
    private static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private static void asegurarDirectorioExiste() throws IOException {
        Storage.ensureStorageDirectoryExists();
    }



    private static void escribirEncabezado(BufferedWriter escritor) throws IOException {
        escritor.write("                   |             Inicio del programa                 |");
        escritor.newLine();
        escritor.write("Posición           |              Piloto/Tiempo/Estado                    |    Vehículo/Patrocinador");
        escritor.newLine();
        escribirGuiones(escritor, 100);
        escritor.newLine();
    }

    private static void escribirGuiones(BufferedWriter escritor, int count) throws IOException {
        if (count <= 0) {
            return;
        }
        escritor.write('-');
        escribirGuiones(escritor, count - 1);
    }

    private static void escribirLineaParticipante(BufferedWriter writer,
                                                  Participante p,
                                                  TiempoCarrera t,
                                                  int[] posiciones) throws IOException {
        if (p == null || t == null || posiciones == null) {
            System.err.println("Intento de escribir participante nulo");
            return;
        }

        if (p.getNumeroParticipante() <= 0 || p.getNumeroParticipante() >= posiciones.length) {
            System.err.println("Número de participante inválido: " + p.getNumeroParticipante());
            return;
        }

        int posicion = posiciones[p.getNumeroParticipante()];
        if (posicion <= 0) {
            System.err.println("Participante " + p.getNumeroParticipante() + " no tiene posición válida");
            return;
        }
        try {
            String linea = String.format("%dº lugar        | %-15s | %-8.2f seg | %-9s | %-10s | %-15s",
                    posicion,
                    p.getNombrePiloto(),
                    t.getTiempoTotal(),
                    determinarEstado(t.getTiempoTotal()),
                    p.getMarcaVehiculo(),
                    p.getNombrePatrocinador());

            writer.write(linea);
            writer.newLine();
        } catch (Exception e) {
            System.err.println("Error formateando línea para participante " + p.getNumeroParticipante());
            throw e;
        }
    }
    private static String determinarEstado(double tiempoTotal) {
        return (tiempoTotal < 180.0) ? "Excelente" :
                (tiempoTotal < 240.0) ? "Bueno" : "Regular";
    }
    private static void escribirPie(BufferedWriter escritor) throws IOException {
        escribirGuiones(escritor, 100);
        escritor.newLine();
        escritor.write("                   |             Fin del programa                 |");
        escritor.newLine();
    }
    private static String generarNombreArchivo(String base, String formatPattern) {
        LocalDateTime fechaActual = LocalDateTime.now();
        int numeroSerial = (int) (Math.random() * 1000) + 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

        return String.format("%s_%s_Serial%d.txt",
                base, fechaActual.format(formatter), numeroSerial);
    }
    public static void guardarDatos(Participante[] participantes, int[] posiciones, String storagePath) throws IOException {

        if (participantes == null || posiciones == null) {
            throw new IOException("Datos de participantes o posiciones son nulos");
        }
        String nombreArchivo = generarNombreArchivo(NOMBRE_ARCHIVO_BASE, "yyyy-MM-dd_HH-mm-ss");
        Path filePath = Paths.get(storagePath, nombreArchivo);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            escribirEncabezado(writer);


            for (int pos = 1; pos <= participantes.length; pos++) {

                for (Participante p : participantes) {
                    if (p != null &&
                            p.getNumeroParticipante() > 0 &&
                            p.getNumeroParticipante() < posiciones.length &&
                            posiciones[p.getNumeroParticipante()] == pos &&
                            p.getTiempoCarrera().isPresent()) {

                        escribirLineaParticipante(writer, p, p.getTiempoCarrera().get(), posiciones);
                        break;
                    }
                }
            }

            escribirPie(writer);
            System.out.println("Archivo guardado en: " + filePath.toAbsolutePath());
        }
    }

    public static void guardarConsulta(String content, String baseFileName) throws IOException {
        asegurarDirectorioExiste();
        // Formato: serialesArchivos_fechaActual_serialUnico.txt
        String nombreArchivo = generarNombreArchivo(baseFileName, "yyyy-MM-dd");
        Path fullPath = Paths.get(Storage.getStoragePath(), nombreArchivo);
        try (BufferedWriter escritor = Files.newBufferedWriter(fullPath, StandardCharsets.UTF_8)) {
            escritor.write(content);
            System.out.println("Reporte de consulta guardado en: " + fullPath.toAbsolutePath());
        } catch (IOException e) {
            String errorMessage = "Error al guardar el reporte de consulta: " + e.getMessage();
            System.err.println(errorMessage);
            errorLog.logError(errorMessage);
            throw e;
        }
    }

    public static void guardarReporteGenerico(String content, String baseFileName) throws IOException {
        asegurarDirectorioExiste();
        String nombreArchivo = generarNombreArchivo(baseFileName, "yyyy-MM-dd_HH-mm-ss");
        String fullPath = Storage.getStoragePath() + "\\" + nombreArchivo;
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fullPath))) {
            escritor.write(content);
            System.out.println("Reporte guardado como: " + fullPath);
        } catch (IOException e) {
            System.err.println("Error al guardar el reporte genérico: " + e.getMessage());
            errorLog.logError("Error al guardar el reporte genérico: " + e.getMessage());
            throw e;
        }
    }
}