package src.main.projects.src_ProyectoButacas.validateItem;


import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;


public class Validate{

    public static int valUnsoldSeats1(int[][][] cinemaMatrix, int movieOption, int showTimeOption){
        int count=0;
        if (cinemaMatrix != null) {
            for (int i = 0; i < cinemaMatrix[0][0].length; i++) {
                if (cinemaMatrix[movieOption - 1][showTimeOption - 1][i] == 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

     public static int valUnsoldSeats2(int[][][] cinemaMatrix, int x, int j){
        int count=0;
        if (cinemaMatrix != null) {
            for (int z = 0; z < cinemaMatrix[0][0].length; z++) {
                if (cinemaMatrix[x][j][z] == 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public static String valSchedule(String text) throws IOException {
        String schedule;
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        LocalTime minHour = LocalTime.parse("12:00 PM", format);
        LocalTime maxHour = LocalTime.parse("10:30 PM", format);

        while (true){
            try {
                System.out.println(text);
                schedule = scanner.nextLine().toUpperCase(Locale.ROOT).trim();

                LocalTime inputtedHour = LocalTime.parse(schedule, format);
                if (!inputtedHour.isBefore(minHour) && !inputtedHour.isAfter(maxHour) && schedule.endsWith("PM")) {
                    return schedule.replace(" ", "");
                }else {
                    System.out.println("  - ERROR: [Hora inválida, fuera del rango permitido (12:00 PM - 10:30 PM)]");
                }

            } catch (DateTimeParseException e) {
                addError("Formato de horas inválido "+e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.print("  - ERROR: [Formato invalido, utilice el formato hh:mm PM (ej. 04:30 PM) / Verifique que la hora sea real]");
   
            }

        }
    }

    public static int valInt(String text, int maxValue, int minValue) throws IOException {
        int size;
        Scanner scanner = new Scanner(System.in);

        while (true){
            try {
                System.out.println(text);
                size = scanner.nextInt();

                if (size < minValue || size > maxValue){
                    System.out.println(" - ERROR: [No se permiten valores MENORES a "+ minValue + " ni MAYORES a " + maxValue + "]");
                } else {
                    return size;
                }
            } catch (InputMismatchException e) {
                addError("No se pueden usar caracteres "+e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.println("- Error: [No se pueden usar caracteres, palabras o letras]");
                scanner.nextLine();
            }
        }
    }

    public static boolean valString(String text){
        String limit="0123456789!#$%/()=?¡¨*[]_";
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < limit.length(); j++) {
                if (text.charAt(i)==limit.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String valString(String option, String text){
        Scanner scanner=new Scanner(System.in);
        System.out.println(text);
        option=scanner.nextLine().trim();
        if (!valString(option)) {
            System.out.println("ERROR - [No se permiten caracteres especiales o numeros]");
            
            return valString(option, text);
        }
        return option;
    }

    public static int valInt(String text, int maxValue) throws IOException {
        int size;
        Scanner scanner = new Scanner(System.in);

        while (true){
            try {
                System.out.println(text);
                size = scanner.nextInt();

                if (size < 1 || size > maxValue){
                    System.out.println(" - ERROR: [No se permiten valores negativos ni valores MAYORES a " + maxValue + "]");
                } else {
                    return size;
                }
            } catch (InputMismatchException e) {
                addError("No se pueden usar caracteres "+e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
                System.out.println("- Error: [No se pueden usar caracteres, palabras o letras]");
                scanner.nextLine();
            }
        }
    }

    public static void addError(String error) throws IOException {
        ArchiveUtil storage=new ArchiveUtil(Paths.get("").toRealPath().toString()+"/src/cine/storage/");
        storeArchive.storeErrors(error, storage);
    }

    public static String nameArchiveGenerate(String name) {
        LocalDateTime actualDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        int rand = (int) (Math.random() * 100) + 1;
        return name+"_"+actualDateTime.format(formatter)+"_"+"Serial"+rand;
    }

    public static String valOptionCompare(String text,String option, String compare1,String compare2){
        Scanner enter=new Scanner(System.in);

        if(!valString(option)){
            System.out.println("ERROR - [No se permiten caracteres especiales o numeros]");
            System.out.println(text);
            option=enter.nextLine().trim().toLowerCase();
            return valOptionCompare(text, option, compare1, compare2);
        }
        if (!option.equals(compare1) && !option.equals(compare2)) {
            System.out.println("ERROR - [Opcion invalida]");
            System.out.println(text);
            option=enter.nextLine().trim().toLowerCase();
            return valOptionCompare(text, option, compare1, compare2);
        } else {
            return option;
        }
    }
}