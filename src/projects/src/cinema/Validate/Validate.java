package cinema.Validate;

import cinema.Store.Logs;
import java.util.Scanner;

public class Validate {
    private static final Scanner input = new Scanner(System.in);
    
    public static boolean readYesNo() {
        while (true) {
            System.out.print("Ingrese [s] para si o [n] para no: ");
            String response = input.nextLine().trim().toLowerCase();
            
            if (response.equals("s")) {
                return true;
            } else if (response.equals("n")) {
                return false;
            } else {
                Logs.errorLog("Opción incorrecta ingresada: " + response);
                System.out.println("Error: Ingrese [s] para si o [n] para no");
            }
        }
    }
    
    public static int getValidRoom(String msg) {
        int room;
        do {
            room = readInteger(msg);
            if (room < 1 || room > 4) {
                Logs.errorLog("Sala incorrecta ingresada: " + room);
                System.out.println("Sala incorrecta. Intente entre 1 y 4");
            }
        } while (room < 1 || room > 4);
        return room - 1;
    }
    
    public static int[] getValidSeat(String msg) {
        while (true) {
            System.out.print(msg);
            String seatInput = input.nextLine().toUpperCase();
            
            try {
                if (seatInput == null || seatInput.trim().isEmpty()) {
                    throw new Exception("Entrada vacía");
                }
                
                char row;
                int seat;
                
                if (seatInput.matches("[A-E]\\d")) {
                    row = seatInput.charAt(0);
                    seat = Integer.parseInt(seatInput.substring(1));
                } else if (seatInput.matches("\\d[A-E]")) {
                    row = seatInput.charAt(1);
                    seat = Integer.parseInt(seatInput.substring(0, 1));
                } else {
                    Logs.errorLog("Formato incorrecto de asiento");
                    throw new Exception("Formato incorrecto, intente A2 o 2A");
                }
                
                if (seat < 1 || seat > 6) {
                    Logs.errorLog("Número de asiento fuera del rango");
                    throw new Exception("Número de asiento debe ser entre 1 y 6");
                }
                
                if (row < 'A' || row > 'E') {
                    Logs.errorLog("Fila incorrecta");
                    throw new Exception("Fila debe ser entre A y E");
                }
                
                return new int[]{row - 'A', seat - 1};
                
            } catch (Exception e) {
                Logs.errorLog("Error en entrada de asiento: " + e.getMessage());
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    public static int readInteger(String msg) {
        if (msg == null) {
            Logs.errorLog("Mensaje null en readInteger");
            return readInteger("Ingrese un número válido: ");
        }
        
        while (true) {
            try {
                System.out.print(msg);
                String inputStr = input.nextLine();
                if (inputStr == null || inputStr.trim().isEmpty()) {
                    throw new NumberFormatException("Entrada vacía");
                }
                return Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                Logs.errorLog("Número incorrecto ingresado: " + e.getMessage());
                System.out.println("Error: Ingrese un número válido");
            }
        }
    }
    
    public static boolean searchAgainPrompt() {
        System.out.print("\n¿Desea realizar otra búsqueda? (s/n): ");
        String response = input.nextLine().trim().toLowerCase();
        
        if (response.equals("s")) {
            return true;
        } else if (response.equals("n")) {
            return false;
        } else {
            Logs.errorLog("Opción inválida en searchAgainPrompt: " + response);
            System.out.println("Entrada no válida. Por favor ingrese 's' para si o 'n' para no");
            return searchAgainPrompt(); 
        }
    }
}