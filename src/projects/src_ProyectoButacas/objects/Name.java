package src_ProyectoButacas.objects;

public class Name{
    private String client;
    private String serialNumber;

    public Name(String client, String serialNumber){
        this.utilValName(client);
        this.serialNumber=(serialNumber.isEmpty()) ? null : serialNumber;
    }

    public void setSerial(String serialNumber){
        this.serialNumber=(serialNumber.isEmpty()) ? null : serialNumber;
    }

    public String getSerial(){
        return this.serialNumber;
    }

    public void setName(String client){
        this.utilValName(client);
    }

    public String getName(){
        return this.client;
    }

    private void utilValName(String client){
        if (client.isEmpty()){
            throw new IllegalArgumentException(" El nombre del cliente no puede estar vacio. ");
        }

        if (!utilValString(client)) {
            throw new IllegalArgumentException(" El nombre del cliente no puede contener numeros o caracteres especiales. ");
        }else{
            this.client=client;
        }
    }

    private boolean utilValString(String client){
        String limit="0123456789!#$%/()=?¡¨*[]_";
            
        for (int i = 0; i < client.length(); i++) {
            for (int j = 0; j < limit.length(); j++) {
                if (client.charAt(i)==limit.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }
}