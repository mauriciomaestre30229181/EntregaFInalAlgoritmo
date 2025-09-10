package cinema.Composable;

public class StoreMainJava {
    private static boolean[][][] cinemaRooms;
    private static int numRooms;
    private static int numRows;
    private static int numSeats;
    
    public static void initializeSystem(int rooms, int rows, int seats){
        numRooms = rooms;
        numRows = rows;
        numSeats = seats;
        cinemaRooms = new boolean[numRooms][numRows][numSeats];
        System.out.println("Sistema de reservas de cine inicializado correctamente");
    }
    
    public static boolean[][][] getCinemaRooms(){
    return cinemaRooms;
    }
    
    public static int getNumRooms(){
    return numRooms;
    }
    
    public static int getNumSeats(){
    return numSeats;
    }
    
    public static int getNumRows(){
    return numRows;
    }
    
    public static void cleanUp() {
        cinemaRooms = null;
        System.out.println("Recursos liberados. Sistema cerrado");
    }
}