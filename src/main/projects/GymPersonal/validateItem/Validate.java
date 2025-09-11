package main.projects.GymPersonal.validateItem;

import main.projects.GymPersonal.helpers.ConsultData;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import static main.projects.GymPersonal.process.BookingTraining.*;


public class Validate {
    public static int valUnsoldSpots1(int[][][] gymMatrix, int classOption, int classScheduleOption){
        int count=0;
        if (gymMatrix != null) {
            for (int i = 0; i < gymMatrix[0][0].length; i++) {
                if (gymMatrix[classOption - 1][classScheduleOption - 1][i] == 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public static int valUnsoldSpots2(int[][][] gymMatrix, int x, int j){
        int count=0;
        if (gymMatrix != null) {
            for (int z = 0; z < gymMatrix[0][0].length; z++) {
                if (gymMatrix[x][j][z] == 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public static int valEnrolledUsersQuant(String fileName)throws IOException {
        String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/"+fileName;
        router = Validate.utilDirectory(router) ;
        Scanner scanner = new Scanner(new File(router));
        int quant =0;
        String pointer = null;
        while(scanner.hasNextLine()){
            pointer = scanner.nextLine();
            quant++;
        }
        return quant;
    }

    public static String valSchedule(String text) throws IOException {
        String schedule,error_msg;
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        LocalTime minHour = LocalTime.parse("6:00 AM", format);
        LocalTime maxHour = LocalTime.parse("9:00 PM", format);

        while (true){
            try {
                System.out.println(text);
                schedule = scanner.nextLine().toUpperCase(Locale.ROOT).trim();
                LocalTime inputtedHour = LocalTime.parse(schedule, format);
                if (!inputtedHour.isBefore(minHour) && !inputtedHour.isAfter(maxHour)) {
                    return schedule.replace(" ", "");
                }else {
                    error_msg =" [Hora inválida, fuera del rango permitido (6:00 AM - 9:00 PM)";
                    throw new IllegalArgumentException(error_msg);
                }
            } catch (DateTimeParseException e) {
                addError("Formato de horas inválido "+e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.print("  - ERROR: [Formato invalido, utilice el formato hh:mm PM (ej. 04:30 PM) / Verifique que la hora sea real]");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                addError("Hora introducida fuera de rango "+e+"/"+e.getMessage()+"]/"+e.getLocalizedMessage());
                System.out.println("  - ERROR: " + e.getMessage() + ". Intentelo de nuevo!]");
            }
        }
    }


    public static String valSchedule(String text, String registeredSchedule) throws IOException {
        String schedule,error_msg;
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        LocalTime minHour = LocalTime.parse("6:00 AM", format);
        LocalTime maxHour = LocalTime.parse("9:00 PM", format);

        while (true){
            try {
                System.out.println(text);
                schedule = scanner.nextLine().toUpperCase(Locale.ROOT).trim();
                LocalTime inputtedHour = LocalTime.parse(schedule, format);
                if (schedule.replace(" ", "").equals(registeredSchedule)) {
                    error_msg = "[No puedes introducir el mismo horario ("+schedule+") dos veces";
                    throw new IllegalArgumentException(error_msg);
                }
                if (!inputtedHour.isBefore(minHour) && !inputtedHour.isAfter(maxHour)) {
                    return schedule.replace(" ", "");
                }else {
                    error_msg =" [Hora inválida, fuera del rango permitido (6:00 AM - 9:00 PM)";
                    throw new IllegalArgumentException(error_msg);
                }
            } catch (DateTimeParseException e) {
                addError("Formato de horas inválido "+e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.print("  - ERROR: [Formato invalido, utilice el formato hh:mm PM (ej. 04:30 PM) / Verifique que la hora sea real]");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                addError("Hora introducida fuera de rango/repetida  "+e+"/"+e.getMessage()+"]/"+e.getLocalizedMessage());
                System.out.println("  - ERROR: " + e.getMessage() + ". Intentelo de nuevo!]");
            }
        }
    }

    public static int valInt(String text, int minValue, int maxValue) throws IOException {
        int number;
        Scanner scanner = new Scanner(System.in);

        while (true){
            try {
                System.out.println(text);
                number = scanner.nextInt();

                if (number < minValue || number > maxValue){
                    System.out.println(" - ERROR: [No se permiten valores MENORES a "+ minValue + " ni MAYORES a " + maxValue + "]");
                } else {
                    return number;
                }
            } catch (InputMismatchException e) {
                addError("No se pueden usar caracteres ni valores fuera del rango permitido"+e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.println("- Error: [No se pueden usar caracteres, palabras o letras]");
                scanner.nextLine();
            }
        }
    }

    public static int valInt(String text, int maxValue) throws IOException {
        int number;
        Scanner scanner = new Scanner(System.in);

        while (true){
            try {
                System.out.println(text);
                number = scanner.nextInt();

                if (number < 1 || number > maxValue){
                    System.out.println(" - ERROR: [No se permiten valores negativos ni valores MAYORES a " + maxValue + "]");
                } else {
                    return number;
                }
            } catch (InputMismatchException e) {
                addError("No se pueden usar caracteres "+e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.println("- Error: [No se pueden usar caracteres, palabras o letras]");
                scanner.nextLine();
            }
        }
    }


    public static boolean valString(String text, int i, int j) {
        String limit = "0123456789!#$%/()=?¡¨*[]_";

        if (i >= text.length()) return true;
        if (j >= limit.length()) return valString(text, i + 1, 0);

        if (text.charAt(i) == limit.charAt(j)) return false;

        return valString(text, i, j + 1);
    }

    public static String valSubName(String name, String text, Scanner scanner) throws IOException {
        if (!valString(name, 0, 0)) {
            System.out.println("- ERROR: [Ingrese un nombre valido. Intentelo nuevamente!]");
            System.out.println(text);
            String txt = "- MANAGE-ERROR: [Ingrese un nombre correcto]";
            addError(txt);
            String nuevoNombre = scanner.nextLine().toLowerCase().trim();
            return valSubName(nuevoNombre, text, scanner); // Aquí ocurre la recursión INDIRECTA
        } else {
            return name.toUpperCase();
        }
    }


    public static int validateId(String text, String[][]database) throws IOException {
        while (true) {
            try {
                String clientId = String.valueOf(Validate.valInt(text, 5000000, 32000000));
                int index = ConsultData.consultIndexByIdRec(database, clientId, 0);
                if (index == -1) {
                    String errorMsg = "[Cedula no registrada en base datos]";
                    throw new IllegalArgumentException(errorMsg);
                }
                return index;
            } catch (IllegalArgumentException e) {
                System.out.println("- ERROR: [Cedula no registrada, ingrese cedula de miembro inscrito]");
                addError("- ERROR de validacion de ID: " + e.getMessage());
            }

        }
    }

    public static boolean isValidRegistration(String[][] bookedClass) {
        if (Validate.isAlreadyBookedClass(bookedClass, 0)) {
            System.out.println("\n  - ERROR: [Ya estás registrado en esta clase en este mismo horario. Ingresa otro horario!]");
            return false;
        }
        if (Validate.isAlreadyBookedSchedule(bookedClass, 0)) {
            System.out.println("\n  - ERROR: [Ya esta registrado en otra clase en este horario. Ingresa otro horario que no choque!]");
            return false;
        }
        return true;
    }

    // Helper con recursividad final
    public static boolean isAlreadyBookedClass(String[][] bookedClass, int idx) {
        if (idx >= bookedClass.length) return false;
        if (bookedClass[idx][1].equals(userId) &&
                bookedClass[idx][4].equals(CLASS_LISTINGS[classOption - 1]) &&
                bookedClass[idx][5].equals(selectedSchedule)) {
            return true;
        }
        return isAlreadyBookedClass(bookedClass, idx + 1);
    }

    public static boolean isAlreadyBookedSchedule(String[][] bookedClass, int idx) {
        if (idx >= bookedClass.length) return false;
        if (bookedClass[idx][1].equals(userId) && bookedClass[idx][5].equals(selectedSchedule)) {
            return true;
        }
        return isAlreadyBookedSchedule(bookedClass, idx + 1);
    }

    public static void addError(String error) throws IOException {
        String router = Paths.get("").toRealPath()+"/src/GymPersonal/storage";
        router = utilDirectory(router)+"/ErrorSystem.log";
        useArchive(nameArchiveGenerate("ErrorSystemValues")+" ["+error+"]", router, true);
    }

    public static String utilDirectory(String router) throws IOException {
        String text;

        if (router.trim().isEmpty()) {
            text = "- MANAGE-ERROR: [Ruta no existe.]";
            addError(text);
            return null;
        }
        File directories = new File(router);
        if (!directories.exists()) {
            text = "- MANAGE-ERROR: [El directorio a guardar no existe.] ";
            addError(text);
            return null;
        }
        return router;
    }

    public static String nameArchiveGenerate(String name) {
        LocalDateTime actualDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        int rand = (int) (Math.random() * 100) + 1;
        return name+"_"+actualDateTime.format(formatter)+"_"+"Serial"+rand;
    }

    public static void useArchive(String content, String route, boolean bool) throws IOException{
        Scanner scanner = new Scanner(System.in);
        String txt;
        if (route.trim().isEmpty()) {
            txt = "MANAGE-ERROR: El directorio a guardar no existe. ";
            addError(txt);
            return;
        }
        try (BufferedWriter addArchive = new BufferedWriter(new FileWriter(route, true))) {
            if (bool) addArchive.newLine();
            if (content == null) throw new IllegalArgumentException(" El nombre del archivo es requerido. ");

            addArchive.write(content);
        } catch (IOException e) {
            addError(e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
            scanner.nextLine();
        }
    }


    public static boolean archiveExist(String fileName, String type) throws IOException {
        String router = Paths.get("").toRealPath()+"/src/GymPersonal/storage";
        router = utilDirectory(router)+"/"+fileName;
        File archive = new File(router);

        if (!archive.exists()) {
            System.out.println("\n- No existen "+ type +" de momento. Haga otra BUSQUEDA o empiece el proceso de REGISTRO u ORDEN!");
            return false;
        }
        return true;
    }


}
