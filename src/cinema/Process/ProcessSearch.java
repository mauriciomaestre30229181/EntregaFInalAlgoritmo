package cinema.Process;

import cinema.Validate.Validate;
import cinema.Composable.StoreMainJava;
import cinema.Helper.SearchManager;

public class ProcessSearch {
    
    public static void searchSeats() {
        boolean [][][] cinemaRooms = StoreMainJava.getCinemaRooms();
        boolean repeat;
        do {
            int roomIndex = Validate.getValidRoom("Ingrese número de sala (1-4): ");
            System.out.println("\n1. Buscar asientos ocupados");
            System.out.println("2. Buscar asientos disponibles");
            int option = Validate.readInteger("Seleccione opción: ");
            
            switch (option) {
                case 1 -> {
                    System.out.println("\nAsientos ocupados en Sala " + (roomIndex + 1) + ":");
                    SearchManager.searchSeats(cinemaRooms, roomIndex, true);
                }
                case 2 -> {
                    System.out.println("\nAsientos disponibles en Sala " + (roomIndex + 1) + ":");
                    SearchManager.searchSeats(cinemaRooms, roomIndex, false);
                }
                default -> System.out.println("Opción no válida.");
            }
            
            repeat = Validate.searchAgainPrompt();
        } while (repeat);
    }
}