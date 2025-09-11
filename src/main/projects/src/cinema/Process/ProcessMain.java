package cinema.Process;

import cinema.Validate.Validate;
import cinema.Composable.StoreData;
import cinema.Composable.StoreMainJava;
import cinema.Helper.ConsultData;

public class ProcessMain {
    private static final int NUM_ROOMS = 4;
    private static final int NUM_ROWS = 5;
    private static final int NUM_SEATS = 6;
    
    public static void initializeSystem() {
        StoreMainJava.initializeSystem(NUM_ROOMS, NUM_ROWS, NUM_SEATS); 
    }
    
    public static void loadPreviousData(){
        System.out.println("\n¿Desea cargar los datos anteriores? [s/n]");
        if (Validate.readYesNo()) {
            StoreData.loadState(StoreMainJava.getCinemaRooms());
            System.out.println("Datos anteriores cargados correctamente");
        } else {
            System.out.println("Iniciando con salas vacías");
        }
    }
    
    public static void runMainMenu() {
        int option;
        do {
            displayMainMenu();
            option = Validate.readInteger("Seleccione una opción: ");
            
            switch (option) {
                case 1:
                    ProcessReservation.reserveSeat();
                    break;
                case 2:
                    ProcessReservation.cancelReservation();
                    break;
                case 3:
                    ProcessSearch.searchSeats();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente");
            }
        } while (option != 4);
    }
    
    public static void saveBeforeExit(){
        System.out.println("\n¿Desea guardar el estado actual? [s/n]");
        if (Validate.readYesNo()) {
            StoreData.saveState(StoreMainJava.getCinemaRooms());   
        } else {
            System.out.println("Los cambios no se guardarán");
        }
    }
    
    public static void cleanUp(){
        StoreMainJava.cleanUp();       
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== CINE - SISTEMA DE RESERVAS ===");
        ConsultData.calculateOccupancy();
        ConsultData.showAllRooms();
        ConsultData.calculateOccupancyTotal();
        System.out.println("\n1. Reservar Asiento");
        System.out.println("2. Cancelar Reservación");
        System.out.println("3. Buscar Asientos");
        System.out.println("4. Salir");
    }
}