package cinema.Process;

import cinema.Validate.Validate;
import cinema.Composable.StoreMainJava;

public class ProcessReservation {
    
    public static void reserveSeat(){
        boolean [][][] cinemaRooms = StoreMainJava.getCinemaRooms();
        int room = Validate.getValidRoom("Ingrese el número de sala (1-4): ");
        int[] seatPos = Validate.getValidSeat("Ingrese el número de asiento (ej. A2 o 2A): ");
        int row = seatPos[0];
        int seat = seatPos[1];
        
        if (!cinemaRooms[room][row][seat]) {
            cinemaRooms[room][row][seat] = true;
            System.out.println("Asiento reservado exitosamente");
        } else {
            System.out.println("El asiento ya está ocupado");
        }
    }
    
    public static void cancelReservation(){
        boolean [][][] cinemaRooms = StoreMainJava.getCinemaRooms();
        int room = Validate.getValidRoom("Ingrese el número de sala (1-4): ");
        int[] seatPos = Validate.getValidSeat("Ingrese el número de asiento a cancelar (ej. A2 o 2A): ");
        int row = seatPos[0];
        int seat = seatPos[1];
        
        if (cinemaRooms[room][row][seat]) {
            cinemaRooms[room][row][seat] = false;
            System.out.println("Reserva cancelada exitosamente");
        } else {
            System.out.println("El asiento ya estaba disponible");
        }
    }
}