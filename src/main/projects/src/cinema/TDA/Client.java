package cinema.TDA;

public class Client {
    private String clientId;
    
    public Client (String clientId){
        if (clientId == null || clientId.trim().isEmpty()){
            throw new IllegalArgumentException ("El ID del cliente no puede ser nulo o vacío");
        }
        this.clientId = clientId;
    }
    
    public String getClientId(){
        return clientId;
    }
    
    public void setClientId(String clientId){
        if (clientId == null || clientId.trim().isEmpty()){
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo o vacío");
        }
        this.clientId = clientId;
    }
    
    @Override
    public String toString(){
        return "Cliente ID: " + clientId;
    }
}