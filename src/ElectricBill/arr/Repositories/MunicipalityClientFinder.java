package ElectricBill.arr.Repositories;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MunicipalityClientFinder {
    private ArchiveUtil archiveUtil;
    private String[] userNames;
    private String[] municipalities;
    private Scanner scanner;

    public MunicipalityClientFinder(String router) throws FileNotFoundException {
        this.archiveUtil = new ArchiveUtil(router);
        this.scanner = new Scanner(System.in);
        loadData();
    }

    private void loadData() throws FileNotFoundException {
        // Cargar nombres de usuarios
        Scanner userScanner = archiveUtil.getArchive("user.txt");
        if (userScanner != null) {
            List<String> userList = new ArrayList<>();
            while (userScanner.hasNextLine()) {
                String[] lineUsers = userScanner.nextLine().split(",");
                for (String user : lineUsers) {
                    userList.add(user.trim());
                }
            }
            userScanner.close();
            userNames = userList.toArray(new String[0]);
        }

        // Cargar municipios
        Scanner municipalityScanner = archiveUtil.getArchive("municipality.txt");
        if (municipalityScanner != null) {
            List<String> municipalityList = new ArrayList<>();
            while (municipalityScanner.hasNextLine()) {
                String[] lineMunicipalities = municipalityScanner.nextLine().split(",");
                for (String municipality : lineMunicipalities) {
                    municipalityList.add(municipality.trim());
                }
            }
            municipalityScanner.close();
            municipalities = municipalityList.toArray(new String[0]);
        }
    }

    // Método principal que pregunta al usuario
    public void generateMunicipalityReport() {
        String[] uniqueMunicipalities = getUniqueMunicipalities();
        
        if (uniqueMunicipalities.length == 0) {
            System.out.println("No se encontraron municipios en los archivos.");
            return;
        }

        // Mostrar municipios disponibles
        System.out.println("\n=== MUNICIPIOS DISPONIBLES ===");
        for (int i = 0; i < uniqueMunicipalities.length; i++) {
            System.out.println((i + 1) + ". " + uniqueMunicipalities[i]);
        }
        
        System.out.println((uniqueMunicipalities.length + 1) + ". Todos los municipios");
        System.out.println("0. Salir");

        // Pedir selección al usuario
        int choice = getMunicipalityChoice(uniqueMunicipalities.length);
        
        if (choice == 0) {
            System.out.println("Operación cancelada.");
            return;
        }

        // Generar reporte según la selección
        if (choice == uniqueMunicipalities.length + 1) {
            // Todos los municipios
            for (String municipality : uniqueMunicipalities) {
                generateMunicipalityFile(municipality);
            }
            System.out.println("Reportes de todos los municipios generados exitosamente.");
        } else {
            // Municipio específico
            String selectedMunicipality = uniqueMunicipalities[choice - 1];
            generateMunicipalityFile(selectedMunicipality);
            System.out.println("Reporte para '" + selectedMunicipality + "' generado exitosamente.");
        }
    }

    private int getMunicipalityChoice(int maxOptions) {
        while (true) {
            try {
                System.out.print("\nSeleccione el municipio (0 para salir): ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                if (choice >= 0 && choice <= maxOptions + 1) {
                    return choice;
                } else {
                    System.out.println("Opción no válida. Por favor, elija entre 0 y " + (maxOptions + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private String[] getUniqueMunicipalities() {
        List<String> uniqueList = new ArrayList<>();
        for (String municipality : municipalities) {
            if (municipality != null && !municipality.isEmpty() && !uniqueList.contains(municipality)) {
                uniqueList.add(municipality);
            }
        }
        return uniqueList.toArray(new String[0]);
    }

    private void generateMunicipalityFile(String municipality) {
        List<String> clients = findClientsByMunicipality(municipality, 0, new ArrayList<>());
        
        if (!clients.isEmpty()) {
            String content = buildFileContent(municipality, clients);
            String fileName = municipality.replace(" ", "_") + "_report.txt";
            
            archiveUtil.setCreateArchive(content, fileName, false, false);
        } else {
            System.out.println("No se encontraron clientes para el municipio: " + municipality);
        }
    }

    // Método recursivo que busca clientes por municipio
    private List<String> findClientsByMunicipality(String targetMunicipality, int index, List<String> result) {
        // Caso base: hemos revisado todos los índices
        if (index >= municipalities.length) {
            return result;
        }

        // Si encontramos el municipio objetivo en esta posición
        if (municipalities[index] != null && 
            municipalities[index].equals(targetMunicipality) && 
            index < userNames.length && 
            userNames[index] != null) {
            result.add(userNames[index]);
        }

        // Llamada recursiva para el siguiente índice
        return findClientsByMunicipality(targetMunicipality, index + 1, result);
    }

    private String buildFileContent(String municipality, List<String> clients) {
        StringBuilder content = new StringBuilder();
        content.append("Municipio: ").append(municipality).append("\n");
        content.append("Cantidad de clientes en el municipio: ").append(clients.size()).append("\n");
        content.append("----------------------------------------------\n");
        content.append("Nombre de los clientes\n");
        
        for (String client : clients) {
            content.append("- ").append(client).append("\n");
        }
        
        return content.toString();
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}