package ElectricBill.arr.Repositories;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MunicipalityConsumptionStats {
    private ArchiveUtil archiveUtil;
    private String[] municipalities;
    private double[] consumptions;
    private String[] userNames;
    private Scanner scanner;

    public MunicipalityConsumptionStats(String router) throws FileNotFoundException {
        this.archiveUtil = new ArchiveUtil(router);
        this.scanner = new Scanner(System.in);
        loadData();
    }

    private void loadData() throws FileNotFoundException {
        // Cargar municipios
        Scanner municipalityScanner = archiveUtil.getArchive("municipality.txt");
        List<String> municipalityList = new ArrayList<>();
        if (municipalityScanner != null) {
            while (municipalityScanner.hasNextLine()) {
                String[] munis = municipalityScanner.nextLine().split(",");
                for (String muni : munis) {
                    if (muni != null && !muni.trim().isEmpty()) {
                        municipalityList.add(muni.trim());
                    }
                }
            }
            municipalityScanner.close();
        }
        municipalities = municipalityList.toArray(new String[0]);

        // Cargar consumos
        Scanner consumptionScanner = archiveUtil.getArchive("kilowatts.txt");
        List<Double> consumptionList = new ArrayList<>();
        if (consumptionScanner != null) {
            while (consumptionScanner.hasNextLine()) {
                String[] values = consumptionScanner.nextLine().split(",");
                double total = 0;
                for (String value : values) {
                    try {
                        total += Double.parseDouble(value.trim());
                    } catch (NumberFormatException e) {
                        // Ignorar valores no numéricos
                    }
                }
                consumptionList.add(total);
            }
            consumptionScanner.close();
        }
        consumptions = consumptionList.stream().mapToDouble(Double::doubleValue).toArray();

        // Cargar nombres de usuarios
        Scanner userScanner = archiveUtil.getArchive("user.txt");
        List<String> userNameList = new ArrayList<>();
        if (userScanner != null) {
            while (userScanner.hasNextLine()) {
                String[] names = userScanner.nextLine().split(",");
                for (String name : names) {
                    if (name != null && !name.trim().isEmpty()) {
                        userNameList.add(name.trim());
                    }
                }
            }
            userScanner.close();
        }
        userNames = userNameList.toArray(new String[0]);
    }

    public void generateMunicipalityStats() {
        try {
            String[] uniqueMunicipalities = getUniqueMunicipalities();
            
            if (uniqueMunicipalities.length == 0) {
                System.out.println("Error: No se encontraron municipios en los archivos.");
                return;
            }

            // Mostrar municipios disponibles
            System.out.println("\n=== ESTADÍSTICAS DE CONSUMO POR MUNICIPIO ===");
            System.out.println("Municipios disponibles:");
            for (int i = 0; i < uniqueMunicipalities.length; i++) {
                System.out.println((i + 1) + ". " + uniqueMunicipalities[i]);
            }
            System.out.println((uniqueMunicipalities.length + 1) + ". Todos los municipios");
            System.out.println("0. Cancelar");

            // Validar selección
            int choice = validateMunicipalityChoice(uniqueMunicipalities.length);
            
            if (choice == 0) {
                System.out.println("Operación cancelada.");
                return;
            }

            // Procesar según la selección
            if (choice == uniqueMunicipalities.length + 1) {
                // Todos los municipios
                Map<String, List<Double>> allStats = new HashMap<>();
                calculateStats(0, allStats);
                displayAllStats(allStats);
                generateAllStatsReport(allStats);
            } else {
                // Municipio específico
                String selectedMunicipality = uniqueMunicipalities[choice - 1];
                Map<String, List<Double>> stats = new HashMap<>();
                calculateStats(0, stats);
                
                if (stats.containsKey(selectedMunicipality)) {
                    displaySingleMunicipalityStats(selectedMunicipality, stats.get(selectedMunicipality));
                    generateSingleMunicipalityReport(selectedMunicipality, stats.get(selectedMunicipality));
                } else {
                    System.out.println("Error: No se encontraron datos para el municipio '" + selectedMunicipality + "'");
                }
            }

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private int validateMunicipalityChoice(int maxOptions) {
        while (true) {
            try {
                System.out.print("\nSeleccione el municipio para ver estadísticas (0 para cancelar): ");
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("Error: Debe ingresar un número.");
                    continue;
                }
                
                int choice = Integer.parseInt(input);
                
                if (choice >= 0 && choice <= maxOptions + 1) {
                    return choice;
                } else {
                    System.out.println("Error: Opción no válida. Por favor, elija entre 0 y " + (maxOptions + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
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

    // Método recursivo para calcular estadísticas
    private void calculateStats(int index, Map<String, List<Double>> stats) {
        if (index >= municipalities.length) return; // Caso base
        
        String municipio = municipalities[index];
        if (municipio != null && !municipio.isEmpty() && index < consumptions.length) {
            stats.computeIfAbsent(municipio, k -> new ArrayList<>())
                 .add(consumptions[index]);
        }
        
        calculateStats(index + 1, stats); // Llamada recursiva
    }

    private void displayAllStats(Map<String, List<Double>> stats) {
        System.out.println("\n=== ESTADÍSTICAS DE TODOS LOS MUNICIPIOS ===");
        stats.forEach((municipio, valores) -> {
            displayMunicipalityStats(municipio, valores);
        });
    }

    private void displaySingleMunicipalityStats(String municipio, List<Double> valores) {
        System.out.println("\n=== ESTADÍSTICAS DE " + municipio.toUpperCase() + " ===");
        displayMunicipalityStats(municipio, valores);
        
        // Mostrar usuarios del municipio
        System.out.println("\nUsuarios en este municipio:");
        List<String> usersInMunicipality = getUsersByMunicipality(municipio, 0, new ArrayList<>());
        if (usersInMunicipality.isEmpty()) {
            System.out.println("  No se encontraron usuarios");
        } else {
            usersInMunicipality.forEach(user -> System.out.println("  - " + user));
        }
    }

    private void displayMunicipalityStats(String municipio, List<Double> valores) {
        if (valores == null || valores.isEmpty()) {
            System.out.println(municipio + ": No hay datos de consumo");
            return;
        }

        double promedio = valores.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double maximo = valores.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double minimo = valores.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double total = valores.stream().mapToDouble(Double::doubleValue).sum();
        

    }

    // Método recursivo para obtener usuarios por municipio
    private List<String> getUsersByMunicipality(String targetMunicipality, int index, List<String> result) {
        if (index >= municipalities.length) return result; // Caso base
        
        if (municipalities[index] != null && 
            municipalities[index].equals(targetMunicipality) && 
            index < userNames.length && 
            userNames[index] != null) {
            result.add(userNames[index]);
        }
        
        return getUsersByMunicipality(targetMunicipality, index + 1, result); // Llamada recursiva
    }

    private void generateAllStatsReport(Map<String, List<Double>> stats) {
        StringBuilder content = new StringBuilder();
        content.append("ESTADÍSTICAS DE CONSUMO - TODOS LOS MUNICIPIOS\n");
        content.append("==============================================\n\n");
        
        stats.forEach((municipio, valores) -> {
            appendMunicipalityStats(content, municipio, valores);
        });
        
        archiveUtil.setCreateArchive(content.toString(), "estadisticas_todos_municipios.txt", false, false);
        System.out.println("\nReporte generado: estadisticas_todos_municipios.txt");
    }

    private void generateSingleMunicipalityReport(String municipio, List<Double> valores) {
        StringBuilder content = new StringBuilder();
        content.append("ESTADÍSTICAS DE CONSUMO - ").append(municipio.toUpperCase()).append("\n");
        content.append("==============================================\n\n");
        
        appendMunicipalityStats(content, municipio, valores);
        
        // Agregar lista de usuarios
        content.append("\nUSUARIOS EN EL MUNICIPIO:\n");
        content.append("----------------------------------------------\n");
        List<String> users = getUsersByMunicipality(municipio, 0, new ArrayList<>());
        if (users.isEmpty()) {
            content.append("No se encontraron usuarios\n");
        } else {
            users.forEach(user -> content.append("- ").append(user).append("\n"));
        }
        
        String fileName = "estadisticas_" + municipio.replace(" ", "_") + ".txt";
        archiveUtil.setCreateArchive(content.toString(), fileName, false, false);
        System.out.println("\nReporte generado: " + fileName);
    }

    private void appendMunicipalityStats(StringBuilder content, String municipio, List<Double> valores) {
        if (valores == null || valores.isEmpty()) {
            content.append(municipio).append(": No hay datos de consumo\n");
            return;
        }

        double promedio = valores.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double maximo = valores.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double minimo = valores.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double total = valores.stream().mapToDouble(Double::doubleValue).sum();
        
        content.append("MUNICIPIO: ").append(municipio).append("\n");
        content.append("----------------------------------------------\n");
        content.append("Usuarios: ").append(valores.size()).append("\n");
        content.append("Consumo total: ").append(String.format("%.2f", total)).append(" kWh\n");
        content.append("Consumo promedio: ").append(String.format("%.2f", promedio)).append(" kWh\n");
        content.append("Consumo máximo: ").append(String.format("%.2f", maximo)).append(" kWh\n");
        content.append("Consumo mínimo: ").append(String.format("%.2f", minimo)).append(" kWh\n");
        content.append("----------------------------------------------\n\n");
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}