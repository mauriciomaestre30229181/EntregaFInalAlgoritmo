package cinema.TDA;

public class Ticket {
    private int roomNumber;
    private int row;
    private int seat;
    
    public Ticket(int roomNumber, int row, int seat){
        
        if (roomNumber < 1 || row < 0 || seat < 0) {
            throw new IllegalArgumentException("Datos de ticket inválidos");
        }
        this.roomNumber = roomNumber;
        this.row = row;
        this.seat = seat;
        
    }
    
    public int getRoomNumber(){
        return roomNumber;
    }
    
    public int getSeat(){
        return seat;
    }
    
    public void setRoomNumber(int roomNumber){
        if (roomNumber < 1){
            throw new IllegalArgumentException("El número de sala debe ser mayor a 0");
        }
        this.roomNumber = roomNumber;
    }
    
    public void setRow(int row){
        if (row < 0){
            throw new IllegalArgumentException("La fila no puede ser negativa");
        }
        this.row = row;
    }
    
    public void setSeat(int seat){
        if (seat < 0){
            throw new IllegalArgumentException("El asiento no puede ser negativo");            
        }
        this.seat = seat;
    }
    
    @Override
    public String toString(){
        return String.format("Ticket en sala: %d, Fila: %c, Asiento: %d",
                roomNumber, (char)('A' + row), seat + 1);
    }
}