package src_restaurant.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

import src_restaurant.models.Diner;
import src_restaurant.models.Table;

public class Validate {

    public static int scanValidInteger(Scanner scanner, String msg, int limit) {
        while (true) {
            try {
                System.out.print(msg);
                String enteredValue = scanner.nextLine();
                int value = Integer.parseInt(enteredValue);

                if (value > 0) {
                    if (limit > 0) {
                        if (value < 1 || value > limit) {
                            String error = "Cantidad invalida. Debe ser entre 1 y " + limit;
                            LoggerUtil.logWarn(error);
                            System.out.println(error + "\n");
                        } else {
                            return value;
                        }
                    } else { // No tiene limite
                        return value;
                    }
                } else {
                    String error = "Cantidad invalida. Debe ser mayor a 0";
                    LoggerUtil.logWarn(error);
                    System.out.println(error + "\n");
                }
            } catch (NumberFormatException e) {
                String error = "Error: Debe ingresar un numero entero valido.";
                LoggerUtil.logError(error);
                System.out.println(error + "\n");
            }
        }
    }

    public static double scanValidDouble(Scanner scanner, String msg, int limit) {
        while (true) {
            try {
                System.out.print(msg);
                String enteredValue = scanner.nextLine();
                double value = Double.parseDouble(enteredValue);

                if (value > 0) {
                    if (limit > 0) {
                        if (value < 1 || value > limit) {
                            String error = "Cantidad invalida. Debe ser entre 1 y " + limit;
                            LoggerUtil.logWarn(error);
                            System.out.println(error + "\n");
                        } else {
                            return value;
                        }
                    } else { // No tiene limite
                        return value;
                    }
                } else {
                    String error = "Cantidad invalida. Debe ser mayor a 0";
                    LoggerUtil.logWarn(error);
                    System.out.println(error + "\n");
                }
            } catch (NumberFormatException e) {
                String error = "Error: Debe ingresar un numero decimal valido.";
                LoggerUtil.logError(error);
                System.out.println(error + "\n");
            }
        }
    }

    public static String scanValidString(Scanner scanner, String msg) {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                String error = "Error: El valor no puede estar vacio.";
                LoggerUtil.logWarn(error);
                System.out.println(error + "\n");
                continue;
            }

            if (hasSpecChars(input)) {
                String error = "Error: El valor solo puede contener letras y espacios.";
                LoggerUtil.logWarn(error);
                System.out.println(error + "\n");
                continue;
            }

            return input;
        }
    }

    public static String checkDirectory(String route) throws IOException {
        String txt = "";

        if (route == null || route.trim().isEmpty()) {
            txt = "Manage-Error: Ruta esta vacia.";
            IllegalArgumentException e = new IllegalArgumentException(txt);
            LoggerUtil.logError(e.getMessage() + " -> " + e.getLocalizedMessage());
            throw e;
        }

        File directory = new File(route);
        if (!directory.exists()) {
            txt = "Manage-Error: El directorio a guardar no existe.";
            NoSuchFileException e = new NoSuchFileException(txt);
            LoggerUtil.logError(e.getMessage() + " -> " + e.getLocalizedMessage());
            throw e;
        }

        return route;
    }

    public static boolean isValidArr(int[] data) {
        return data != null && data.length > 0;
    }

    public static boolean isValidArr(double[] data) {
        return data != null && data.length > 0;
    }

    public static boolean isValidArr(String[] data) {
        return data != null && data.length > 0;
    }

    public static boolean isValidArr(boolean[] data) {
        return data != null && data.length > 0;
    }

    private static boolean hasSpecChars(String value) {
        String invalids = "0123456789.,!#$%/()=?¡¨*[]_+-{}";

        for (int i = 0; i < value.length(); i++) {
            for (int j = 0; j < invalids.length(); j++) {
                if (value.charAt(i) == invalids.charAt(j)){
                    return true;
                }
            }
        }

        return false;
    }

    // NEW FUNCTIONS
    public static boolean isValidArr(Table[] data) {
        return data != null && data.length > 0;
    }

    public static boolean isValidArr(Diner[] data) {
        return data != null && data.length > 0;
    }
}