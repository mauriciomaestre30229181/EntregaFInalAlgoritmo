package cinema.Helper;

import cinema.Composable.StoreMainJava;

public class ConsultData {
    
    public static void calculateOccupancy() {
        boolean [][][] cinemaRooms = StoreMainJava.getCinemaRooms();
        int numRooms = StoreMainJava.getNumRooms();
        int numRows = StoreMainJava.getNumRows();
        int numSeats = StoreMainJava.getNumSeats();
        
        System.out.println("\nOcupación por sala:");
        for (int r = 0; r < numRooms; r++) {
            int occupied = 0;
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numSeats; j++) {
                    if (cinemaRooms[r][i][j]) occupied++;
                }
            }
            double percentage = (occupied * 100.0) / (numRows * numSeats);
            System.out.printf("Sala %d: %.2f%% ocupada\t", r+1, percentage);
        }
        System.out.println();
    }
    
    public static void showAllRooms() {
        boolean [][][] cinemaRooms = StoreMainJava.getCinemaRooms();
        int numRooms = StoreMainJava.getNumRooms();
        int numRows = StoreMainJava.getNumRows();
        int numSeats = StoreMainJava.getNumSeats();
        
        System.out.println("\nEstado de las salas:");
        for (int room = 0; room < numRooms; room++) {
            System.out.println("\nSala " + (room + 1) + ":");
            System.out.print("    ");
            for (int seat = 1; seat <= numSeats; seat++) {
                System.out.print(seat + "  ");
            }
            System.out.println();
            
            for (int row = 0; row < numRows; row++) {
                System.out.print((char)('A' + row) + "  ");
                for (int seat = 0; seat < numSeats; seat++) {
                    System.out.print(cinemaRooms[room][row][seat] ? "[X] " : "[ ] ");
                }
                System.out.println();
            }
        }
    }
    
    public static void calculateOccupancyTotal() {
        boolean [][][] cinemaRooms = StoreMainJava.getCinemaRooms();
        int numRooms = StoreMainJava.getNumRooms();
        int numRows = StoreMainJava.getNumRows();
        int numSeats = StoreMainJava.getNumSeats();
        
        int totalSeats = numRooms * numRows * numSeats;
        int occupiedSeats = 0;
        
        for (int r = 0; r < numRooms; r++) {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numSeats; j++) {
                    if (cinemaRooms[r][i][j]) occupiedSeats++;
                }
            }
        }
        double percentage = (occupiedSeats * 100.0) / totalSeats;
        System.out.printf("\nOcupación total: %.2f%% (%d/%d asientos)\n", percentage, occupiedSeats, totalSeats);
    }
}