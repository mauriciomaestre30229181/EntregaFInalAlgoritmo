package cinema.TDA;

public class CinemaSeat {
    private int roomNumber;
    private int row;
    private int seatNumber;
    private boolean isOccupied;
    
    public CinemaSeat(int roomNumber, int row, int seatNumber, boolean isOccupied){
        if (roomNumber < 1 || row < 0 || seatNumber < 0){
            throw new IllegalArgumentException("Datos de asiento inválidos");
        }
        this.roomNumber = roomNumber;
        this.row = row;
        this.seatNumber = seatNumber;
        this.isOccupied = isOccupied;
    }
    
    public int getRoomNumber(){
        return roomNumber;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getSeatNumber(){
        return seatNumber;
    }
    
    public boolean isOccupied(){
        return isOccupied;
    }
    
    public void setOccupied(boolean occupied){
        this.isOccupied = occupied;
    }
    
    @Override
    public String toString(){
        String status = isOccupied ? "Ocupado" : "Disponible";
        return String.format("Asiento en Sala %d, Fila %c, Asiento %d: %s",
                roomNumber, (char)('A' + row), seatNumber + 1, status);
    }
}