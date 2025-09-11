package JavaRace.helpers;

import JavaRace.repositories.Participante;
import java.util.Scanner;

public class Validate {

    private static boolean isTextValid(String text) {
        return text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s.,\\-$]+");
    }

    private static String getValidTextLoop(Scanner scanner, String message) {
        String text;
        while (true) {
            System.out.print(message);
            text = scanner.nextLine().trim();

            if (text.isEmpty()) {
                System.out.println("Error: El texto no puede estar vacío.");
                continue;
            }
            if (!isTextValid(text)) {
                System.out.println("Error: El texto contiene caracteres inválidos.");
                continue;
            }
            return text;
        }
    }

    public static String validarTexto(Scanner scanner, String mensaje) {
        return getValidTextLoop(scanner, mensaje);
    }

    private static int parseIntegerInput(String input) throws NumberFormatException {
        return Integer.parseInt(input);
    }

    private static int getValidIntegerLoop(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                if (input.isEmpty()) {
                    System.out.println("Error: Debe ingresar un número.");
                    continue;
                }
                int number = parseIntegerInput(input);
                if (number < 0) {
                    System.out.println("Error: El número debe ser positivo.");
                    continue;
                }
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
                errorLog.logError("Error al ingresar numero valido: " + e.getMessage());
            }
        }
    }

    public static int validarEntero(Scanner scanner, String mensaje) {
        return getValidIntegerLoop(scanner, mensaje);
    }

    private static boolean isAgeValid(int age) {
        return age >= 16 && age <= 80;
    }

    private static int getValidAgeLoop(Scanner scanner, String message) {
        while (true) {
            int age = validarEntero(scanner, message);
            if (!isAgeValid(age)) {
                System.out.println("Error: La edad debe estar entre 16 y 80 años.");
                continue;
            }
            return age;
        }
    }

    public static int validarEdad(Scanner scanner, String mensaje) {
        return getValidAgeLoop(scanner, mensaje);
    }

    private static boolean isTimeValid(double time) {
        return time > 0 && time <= 3600;
    }

    private static double getValidTimeLoop(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                if (input.isEmpty()) {
                    System.out.println("Error: Debe ingresar un tiempo.");
                    continue;
                }
                double time = Double.parseDouble(input);
                if (!isTimeValid(time)) {
                    System.out.println("Error: El tiempo debe ser mayor a 0 y menor o igual a 3600 segundos.");
                    continue;
                }
                return time;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número decimal válido.");
                errorLog.logError("Error: Debe ingresar un número decimal válido." + e.getMessage());
            }
        }
    }

    public static double validarTiempo(Scanner scanner, String mensaje) {
        return getValidTimeLoop(scanner, mensaje);
    }

    private static boolean isParticipantNumberValidRange(int number) {
        return number > 0 && number <= 25;
    }

    private static boolean participantNumberExists(int number, Participante[] participantes) {
        return findParticipantByNumberRecursively(participantes, number, 0) != null;
    }

    private static Participante findParticipantByNumberRecursively(Participante[] participantes, int number, int index) {
        if (index >= participantes.length) {
            return null; // Caso base
        }

        Participante current = participantes[index];
        if (current != null && current.getNumeroParticipante() == number) {
            return current;
        }

        return findParticipantByNumberRecursively(participantes, number, index + 1);
    }

    private static int getValidParticipantNumberLoop(Scanner scanner, String message, Participante[] participantes) {
        while (true) {
            int number = validarEntero(scanner, message);
            if (!isParticipantNumberValidRange(number)) {
                System.out.println("Error: El número debe estar entre 1 y 25.");
                continue;
            }
            if (participantNumberExists(number, participantes)) {
                System.out.println("Error: Este número de participante ya está registrado.");
                continue;
            }
            return number;
        }
    }

    public static int validarNumeroParticipante(Scanner scanner, String mensaje, Participante[] participantes) {
        return getValidParticipantNumberLoop(scanner, mensaje, participantes);
    }

    private static boolean isYearValid(int year) {
        return year >= 1950 && year <= 2025;
    }

    private static int getValidYearLoop(Scanner scanner, String message) {
        while (true) {
            int year = validarEntero(scanner, message);
            if (!isYearValid(year)) {
                System.out.println("Error: El año debe estar entre 1950 y 2025.");
                continue;
            }
            return year;
        }
    }

    public static int validarAño(Scanner scanner, String mensaje) {
        return getValidYearLoop(scanner, mensaje);
    }

    private static boolean containsDigits(String text) {
        return text.matches(".*\\d.*");
    }

    private static String getValidNameLoop(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String text = scanner.nextLine().trim();

            if (text.isEmpty()) {
                System.out.println("Error: El texto no puede estar vacío.");
                continue;
            }
            if (containsDigits(text)) {
                System.out.println("Error: El texto no puede contener números.");
                continue;
            }
            if (!isTextValid(text)) {
                System.out.println("Error: El texto contiene caracteres inválidos.");
                continue;
            }
            return text;
        }
    }

    public static String validarNombre(Scanner scanner, String mensaje) {
        return getValidNameLoop(scanner, mensaje);
    }
}