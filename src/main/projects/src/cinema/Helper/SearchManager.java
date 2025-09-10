package cinema.Helper;

import cinema.Process.ProcessManager;
import cinema.QueueStack.Queue;
import cinema.TDA.CinemaSeat;
import cinema.Store.Logs;

public class SearchManager {
    
    public static void searchSeats(boolean[][][] rooms, int roomNumber, boolean isOccupied) {
        System.out.println("\nBuscando asientos " + (isOccupied ? "ocupados..." : "disponibles..."));
        
        Queue<CinemaSeat> foundSeats = new Queue<>();
        
        if(isOccupied){
            findOccupiedSeatsFinal(rooms, roomNumber, 0, 0, foundSeats);
        } else{
            findAvailableSeatsNonFinal(rooms, roomNumber, 0, 0, foundSeats);
        }
        
        if (foundSeats.getSize() > 0){
            System.out.println("\nBusqueda completada. Se encontraron " + foundSeats.getSize() + " asientos");
            try {
                ProcessManager.processAndStore(foundSeats, isOccupied);
            } catch( Exception e){
                Logs.logException("Error al procesar y guardar los datos", e);
            }
        } else {
            System.out.println("No se encontraron asientos " + (isOccupied ? "ocupados." : "disponibles"));
        }
    }
    
    public static void findOccupiedSeatsFinal(boolean[][][] rooms,int roomIndex, int rowIndex, int seatIndex, Queue<CinemaSeat> foundSeats){
        if (roomIndex >= rooms.length || rooms[roomIndex] == null || rowIndex >= rooms [roomIndex].length){
            return;
        }
        
        if (seatIndex >= rooms[roomIndex][rowIndex].length){
            findOccupiedSeatsFinal(rooms, roomIndex, rowIndex + 1, 0, foundSeats);
            return;
        }
        
        if(rooms[roomIndex][rowIndex][seatIndex]){
            CinemaSeat seat = new CinemaSeat(roomIndex + 1, rowIndex, seatIndex + 1, true);
            foundSeats.enqueue(seat);
        }
        
        findOccupiedSeatsFinal(rooms,roomIndex, rowIndex, seatIndex + 1, foundSeats);
    }
    
    public static void findAvailableSeatsNonFinal(boolean[][][] rooms,int roomIndex, int rowIndex, int seatIndex, Queue<CinemaSeat> foundSeats) {
        if (roomIndex >= rooms.length || rooms[roomIndex] == null || rowIndex >= rooms[roomIndex].length){
            return;
        }
        if (seatIndex >= rooms[roomIndex][rowIndex].length){
            findAvailableSeatsNonFinal(rooms, roomIndex, rowIndex + 1, 0, foundSeats);
            return;
        }
        
        if (!rooms[roomIndex][rowIndex][seatIndex]) {
            CinemaSeat seat = new CinemaSeat(roomIndex + 1, rowIndex, seatIndex + 1, false);
            foundSeats.enqueue(seat);
        }
        
        findAvailableSeatsNonFinal(rooms, roomIndex, rowIndex, seatIndex + 1, foundSeats);
    }
}