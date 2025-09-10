package cinema.Process;

import cinema.Store.Logs;
import cinema.Composable.StoreData;
import cinema.TDA.CinemaSeat;
import cinema.QueueStack.Stack;
import cinema.QueueStack.Queue;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ProcessManager {
    
    public static void processAndStore(Queue<CinemaSeat> foundSeats, boolean isOccupied)throws IOException {
        System.out.println("Encolando los asientos encontrados...");
        
        Stack<CinemaSeat> stack = new Stack<>();
        System.out.println("\nDesecnolando la cola y apilando los asientos...");
        
        while (!foundSeats.isEmpty()){
            try{
                CinemaSeat seatFromQueue = foundSeats.dequeue();
                System.out.println("Desencolado: " + seatFromQueue);
                stack.push(seatFromQueue);
            } catch (NoSuchElementException e){
                break;
            }
        }
        
        System.out.println("\nTodos los elementos han sido apilados. Tamaño de la pila: " + stack.getSize());
        
        System.out.println("\nDesapilando los asientos para guardarlos en un nuevo archivo...");
        try {
            StoreData.saveSearchedData(stack);  
        } catch (IOException e){
            Logs.logException("Error al guardar los datos procesados", e);
            System.out.println("Error al guardar los datos procesados");
            throw e;
        }
        
        System.out.println("\nProceso de encolar, apilar y guardar completado");
    }
}