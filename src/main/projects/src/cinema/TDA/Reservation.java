package cinema.TDA;

import java.util.LinkedList;
import java.util.List;

public class Reservation {
    private int reservationId;
    private Client client;
    private List<Ticket> tickets;
    
    public Reservation(int reservationId, Client client){
        if (reservationId <= 0 || client == null){
            throw new IllegalArgumentException("Datos de reserva inválidos");
        }
        this.reservationId = reservationId;
        this.client = client;
        this.tickets = new LinkedList <>();
    }
    
    public int getReservationId(){
        return reservationId;
    }
    
    public Client getClient(){
        return client;
    }
    
    public List<Ticket> getTickets(){
        return tickets;
    }
    
    public void addTciket(Ticket ticket){
        if (ticket != null){
            this.tickets.add(ticket);
        }
    }
    
    public boolean removeTicket(Ticket ticket){
        return this.tickets.remove(ticket);
    }
    
    @Override
    public String toString(){
        return "Reserva #" + reservationId + " para " + client.getClientId() +
                " con " + tickets.size() + " boletos";
    }
}