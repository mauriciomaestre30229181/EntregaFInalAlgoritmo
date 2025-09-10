package cinema.TDA;

import java.time.LocalDateTime;

public class LogEntry {
    private final String message;
    private final LocalDateTime timestamp;
    private final String context;
    
    public LogEntry(String message, String context){
        if (message == null || message.trim().isEmpty() || context == null || context.trim().isEmpty()){
        throw new IllegalArgumentException("Mensaje o contexto de registro no pueden ser nulos o vacíos");
        }
        this.message = message;
        this.context = context;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getMessage(){
        return message;
    }
    
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
    
    public String getContext(){
        return context;
    }
    
    @Override
    public String toString(){
        return String.format("[%s] [%s] %s", timestamp, context, message);
    }
}