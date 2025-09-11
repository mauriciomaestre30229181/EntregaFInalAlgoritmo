package cinema.Composable;

import cinema.Store.Logs;
import cinema.QueueStack.Stack;
import cinema.TDA.CinemaSeat;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class StoreData {
    private static final String FILE_NAME = "cinemaState.txt";
    private static final String SEARCH_FILE_FORMAT = "%s_%s_%d.txt";
    
    public static void saveState(boolean[][][] rooms) {
        if (rooms == null) {
            Logs.logException("Intento de guardar estado con rooms null", 
                new NullPointerException("rooms es null"));
            return;
        }
        
        try {
            Path filePath = Paths.get(FILE_NAME);
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                
                for (int r = 0; r < rooms.length; r++) {
                    if (rooms[r] == null) continue;
                    
                    for (int i = 0; i < rooms[r].length; i++) {
                        if (rooms[r][i] == null) continue;
                        
                        for (int j = 0; j < rooms[r][i].length; j++) {
                            writer.write(rooms[r][i][j] ? "1" : "0");
                        }
                        writer.newLine();
                    }
                    writer.write("----------");
                    writer.newLine();
                }
                
                System.out.println("Estado guardado exitosamente en " + FILE_NAME);
            }
        } catch (IOException e) {
            Logs.logException("Error al guardar el archivo", e);
            System.out.println("Error al guardar el archivo");
        }
    }
    
    public static void loadState(boolean[][][] rooms) {
        if (rooms == null) {
            Logs.logException("Intento de cargar estado con rooms null", 
                new NullPointerException("rooms es null"));
            return;
        }
        
        try {
            Path filePath = Paths.get(FILE_NAME);
            if (!Files.exists(filePath)) {
                System.out.println("No existe archivo previo para cargar");
                return;
            }
            
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                int r = 0;
                int i = 0;
                
                while ((line = reader.readLine()) != null && r < rooms.length) {
                    if (line.equals("----------")) {
                        r++;
                        i = 0;
                    } else if (rooms[r] != null && i < rooms[r].length) {
                        if (rooms[r][i] == null) {
                            rooms[r][i] = new boolean [StoreMainJava.getNumSeats()];
                        }
                        
                        for (int j = 0; j < line.length() && j < rooms[r][i].length; j++) {
                            rooms[r][i][j] = (line.charAt(j) == '1');
                        }
                        i++;
                    }
                }
                
                System.out.println("Estado cargado exitosamente de " + FILE_NAME);
            }
        } catch (IOException e) {
            Logs.logException("Error al cargar el archivo", e);
            System.out.println("Error al cargar el archivo");
        }
    }
    
    public static void saveSearchedData(Stack<CinemaSeat> dataStack) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String dateString = now.format(formatter);
        long serial = System.currentTimeMillis();
        
        String fileName = String.format(SEARCH_FILE_FORMAT, "serialesArchivos", dateString, serial);
        Path filePath = Paths.get(fileName);
        
        System.out.println("Guardando datos buscados en " + fileName);
        
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            while (!dataStack.isEmpty()){
                try{
                    CinemaSeat seat = dataStack.pop();
                    writer.write(seat.toString());
                    writer.newLine();
                } catch (NoSuchElementException e){
                    break;
                }
            }
            System.out.println("Datos guardados exitosamente en " + fileName);
        } catch (IOException e){
            Logs.logException("Error al guardar los datos buscados", e);
            throw e;
        }
    }
}