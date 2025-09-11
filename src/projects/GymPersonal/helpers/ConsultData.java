package projects.GymPersonal.helpers;

import projects.GymPersonal.composables.StoreArchive;
import projects.GymPersonal.composables.StoreMain;
import projects.GymPersonal.repositories.*;
import projects.GymPersonal.validateItem.Validate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

import static projects.GymPersonal.process.BookingTraining.*;


public class ConsultData {
    public static String[] HEADERS_NUTRIT = {"nombre", "cedula", "genero", "plan", "orden"};
    public static String[] HEADERS_TRAINING = {"nombre", "cedula", "genero", "plan", "clase", "horario"};

    public static String[][] readFile(String fileName, int size, boolean isTraining) throws IOException {
        String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
        router = Validate.utilDirectory(router) +fileName;
        File file = new File(router);
        String[][] array;
        int lineCounter,f = 0;

        if (!file.exists()) {
            file.createNewFile();
            array =  new String[1][size];
            updateFileHeaders(array, isTraining);
            return array;
        }
        lineCounter= Validate.valEnrolledUsersQuant(fileName);
        if (lineCounter == 0) {
            array =  new String[1][size];
            updateFileHeaders(array, isTraining);
            return array;
        }

        String[][]booked = new String[lineCounter+1][size];
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner separate = new Scanner(line);
            separate.useDelimiter(",");
            separate.useLocale(Locale.US);
            while (separate.hasNext()) {
                for (int c = 0; c < booked[0].length; c++) {
                    booked[f][c] = separate.next();
                }
                f++;
            }
        }
        return booked;
    }

    public static void updateFileHeaders(String[][] bookedArray, boolean isOrder){
        if(bookedArray!= null) {
            if(bookedArray[0][0].equalsIgnoreCase("nombre")) return;
            if (isOrder) {
                for (int col = 0; col < bookedArray[0].length; col++) bookedArray[0][col] = HEADERS_NUTRIT[col];
            } else {
                for (int col = 0; col < bookedArray[0].length; col++) bookedArray[0][col] = HEADERS_TRAINING[col];
            }
        }
    }

    public static void updateFileHeaders(String[] bookedArray, boolean isTraining){
        if(bookedArray!= null) {
            if(bookedArray[0].equalsIgnoreCase("nombre")) return;
            if (isTraining) {
                for (int col = 0; col < bookedArray.length; col++) bookedArray[col] = HEADERS_NUTRIT[col];
            } else {
                for (int col = 0; col < bookedArray.length; col++) bookedArray[col] = HEADERS_TRAINING[col];
            }
        }
    }

    public static void readGymDatabaseFile(String[][] bidimentionalArray, String fileName) throws IOException {
        String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage";
        router = Validate.utilDirectory(router) + "/"+fileName;
        Scanner scanner = new Scanner(new File(router));
        int f=0;
        if(bidimentionalArray != null) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner separate = new Scanner(line);
                separate.useDelimiter(",");
                separate.useLocale(Locale.US);
                while (separate.hasNext()) {
                    for (int c = 0; c < bidimentionalArray[0].length; c++) {
                        bidimentionalArray[f][c] = separate.next();
                    }
                    f++;
                }
            }
            scanner.close();
        }
    }

    // Recursividad final
    public static int consultIndexByIdRec(String[][] membersDatabase, String cedula, int row) {
        if (membersDatabase == null || row >= membersDatabase.length) return -1;
        if (membersDatabase[row][1].equals(cedula)) return row;

        return consultIndexByIdRec(membersDatabase, cedula, row + 1);
    }

    public static void search() throws IOException {
        boolean shouldContinue = true;
        while(shouldContinue) {
            System.out.println("""
                    Indique que desea BUSCAR:
                    - Ingrese [1] Si desea buscar algun USUARIO que ya ha sido REGISTRADO en alguna clase el dia de hoy.
                    - Ingrese [2] Si desea buscar algun USUARIO que ya ha ORDENADO algo de nuestro "Centro Nutricional".
                    - Ingrese [3] Si desea buscar GRUPO DE USUARIOS que ya han sido REGISTRADO en alguna clase el dia de hoy.
                    - Ingrese [4] Si desea buscar GRUPO DE USUARIOS que ya han ORDENADO algo de nuestro "Centro Nutricional".
                    - Ingrese [5] Si desea SABER las METRICAS de todas las clases impartidas el dia de hoy.
                    - Ingrese [6] Si no desea realizar una BUSQUEDA y desea SALIR de este proceso.""");
            int option = Validate.valInt("Selecciona una opcion: ", 1, 6);
            switch (option) {
                case 1:
                    searchByTrainingRegistration(true);
                    break;
                case 2:
                    searchByNutritionOrder(true);
                    break;
                case 3:
                    searchByTrainingRegistration(false);
                    break;
                case 4:
                    searchByNutritionOrder(false);
                    break;
                case 5:
                    StoreMain.store();
                    break;
                case 6:
                    shouldContinue=false;
            }
        }
    }

    public static void searchByTrainingRegistration(Boolean isSingleSearch) throws IOException {
        int option;

        if (!Validate.archiveExist("BookedUsers.txt", "REGISTROS")) return;
        bookedUsersArray = ConsultData.readFile("BookedUsers.txt", 6, true);

        if(isSingleSearch == true) {
            System.out.println("""
                Indique como quiere que sea su busqueda:
                - Ingrese [1] Si desea buscar el usuario por NOMBRE.
                - Ingrese [2] Si desea buscar el usuario por NUMERO de Id(Cedula).
                - Ingrese [3] Si desea ABANDONAR esta busqueda.""");
            option = Validate.valInt("Selecciona una opcion: ", 1, 3);
            if (option == 1)
                ConsultData.consultRegistDataByName(bookedUsersArray, "no se ha registrado en clases el dia de hoy.");
            else if (option == 2)
                ConsultData.consultRegistDataById(bookedUsersArray, "no se ha registrado en clases el dia de hoy.");
        }else{
            System.out.println("""
                Indique como quiere que sea su busqueda:
                - Ingrese [1] Si desea buscar GRUPO de usuarios por GENERO.
                - Ingrese [2] Si desea buscar GRUPO de  usuarios por CLASE.
                - Ingrese [3] Si desea ABANDONAR esta busqueda.""");
            option =  Validate.valInt("Selecciona una opcion: ", 1, 3);
            if (option == 1) ConsultData.consultDataByGender(bookedUsersArray,false);
            else if (option == 2) ConsultData.consultRegistDataByClass(bookedUsersArray, "no se ha registrado en clases el dia de hoy.");
        }
    }


    public static void searchByNutritionOrder(Boolean isSingleSearch) throws IOException {
        int option;

        if(!Validate.archiveExist("BookedNutritions.txt", "ORDENES")) return;
        bookedUsersArray = ConsultData.readFile("BookedNutritions.txt", 5, false);

        if(isSingleSearch == true) {
            System.out.println("""
                    Indique como quiere que sea su busqueda:
                    - Ingrese [1] Si desea buscar el usuario por NOMBRE.
                    - Ingrese [2] Si desea buscar el usuario por NUMERO de Id(Cedula).
                    - Ingrese [3] Si desea ABANDONAR esta busqueda.""");
            option = Validate.valInt("Selecciona una opcion: ", 1, 3);
            if (option == 1)
                ConsultData.consultOrderDataByName(bookedUsersArray, "no se ha ordenado nada el dia de hoy.");
            else if (option == 2)
                ConsultData.consultOrderDataById(bookedUsersArray, "no se ha ordenado nada el dia de hoy.");
        }else{
            System.out.println("""
                Indique como quiere que sea su busqueda:
                - Ingrese [1] Si desea buscar GRUPO de usuarios por GENERO.
                - Ingrese [2] Si desea buscar GRUPO de usuarios por ITEM ordenado.
                - Ingrese [3] Si desea ABANDONAR esta busqueda.""");
            option =  Validate.valInt("Selecciona una opcion: ", 1, 3);
            if (option == 1) ConsultData.consultDataByGender(bookedUsersArray, true);
            else if (option == 2) ConsultData.consultOrderDataByItem(bookedUsersArray, "no se ha ordenado nada el dia de hoy.");
        }
    }


    public static void consultRegistDataByName(String[][] bookedArray, String msg) throws IOException {
        String text;
        int index;
        Scanner scanner = new Scanner(System.in);

        text = "- Ingrese un nombre que este registrado en este Gimnasio (Ej. Miguel Maestre):";
        System.out.println(text);
        String nameInputted = Validate.valSubName(scanner.nextLine(), text, new Scanner(System.in));

        index = consultIndexByName(bookedArray, nameInputted, 0);
        if(index==(-1)) System.out.println("El usuario ingresado "+ msg);
        addRegistrationToArray(bookedArray,index, false);
    }

    public static void consultRegistDataById(String[][] bookedArray, String msg) throws IOException {
        String text;
        int index;

        text = "- Ingrese una numero de cedula que este registrado en este Gimnasio (Ej. 30229181):";
        index = Validate.validateId(text, bookedArray);
        if(index==(-1)) System.out.println("El usuario ingresado "+ msg);
        addRegistrationToArray(bookedArray,index,true);
    }

    public static void consultOrderDataByName(String[][] bookedArray, String msg) throws IOException {
        String text;
        int index;
        Scanner scanner = new Scanner(System.in);

        text = "- Ingrese un nombre que este registrado en este Gimnasio (Ej. Miguel Maestre):";
        System.out.println(text);
        String nameInputted = Validate.valSubName(scanner.nextLine(), text, new Scanner(System.in));

        index = consultIndexByName(bookedArray, nameInputted, 0);
        if(index==(-1)) System.out.println("El usuario ingresado "+ msg);
        addOrderToArray(bookedArray,index, false);
    }



    public static void consultOrderDataById(String[][] bookedArray, String msg) throws IOException {
        String text;
        int index;

        text = "- Ingrese una numero de cedula que este registrado en este Gimnasio (Ej. 30229181):";
        index = Validate.validateId(text, bookedArray);
        if(index==(-1)) System.out.println("El usuario ingresado "+ msg);
        addOrderToArray(bookedArray,index,true);
    }


    public static void addRegistrationToArray(String[][] bookedUsers, int indexUser, boolean isId) throws IOException {
        User user=null;
        Training training=null;
        try {
            user = new User(bookedUsers, indexUser);
        } catch (IllegalArgumentException e) {
            Validate.addError(e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
            System.out.println("- ERROR: "+e.getMessage());
        }

        try {
            training = new Training(bookedUsers, indexUser);
        } catch (IllegalArgumentException e) {
            Validate.addError(e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
            System.out.println("- ERROR: "+e.getMessage());
        }
        
        if(!isId) StoreArchive.storeDetailedUserRegistration(user, training,"DetailedUserRegistByName");
        else StoreArchive.storeDetailedUserRegistration(user,training, "DetailedUserRegistById");
    }

    public static void addOrderToArray(String[][] bookedUsers, int indexUser, boolean isId) throws IOException {
        User user=null;
        Nutrition nutrition=null;
        int dataTypeIndex;

        try {
            user = new User(bookedUsers, indexUser);
        } catch (IllegalArgumentException | IOException e) {
            Validate.addError(e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
            System.out.println("- ERROR: "+e.getMessage());
        }

        try {
            nutrition = new Nutrition(bookedUsers, indexUser);
        } catch (IllegalArgumentException e) {
            Validate.addError(e+"/"+e.getMessage()+"/"+e.getLocalizedMessage());
            System.out.println("- ERROR: "+e.getMessage());
        }
        if(!isId) StoreArchive.storeDetailedUserOrder(user, nutrition,"DetailedUserOrderByName");
        else StoreArchive.storeDetailedUserOrder(user, nutrition,"DetailedUserOrderById");

    }

    public static int countStringWords(String string) {
        if (string == null || string.trim().isEmpty()) return 0;

        // split con regex \s+ para agrupar uno o más espacios
        String[] parts = string.trim().split("\\s+");
        return parts.length;
    }

    public static int consultIndexByName(String[][] bookedArray, String name, int row) {
        if (bookedArray == null || row >= bookedArray.length) return -1;
        if (bookedArray[row][0].equalsIgnoreCase(name)) return row;

        return consultIndexByName(bookedArray, name, row + 1);
    }

    public static void consultRegistDataByClass(String[][] bookedArray, String msg) throws IOException {
        String text;
        Scanner scanner = new Scanner(System.in);
        String fileToRead = "BookedUsers.txt";
        String fileToWrite = "GlobalConsultPerClass";
        String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
        router = Validate.utilDirectory(router);

        text = "- Ingrese una clase que sea impartida en este Gimnasio (Ej.Pilates):";
        System.out.println(text);
        String inputtedClass = Validate.valSubName(scanner.nextLine(), text, new Scanner(System.in));

        ArchiveUtil util = new ArchiveUtil(router);

        String[] matchingClasses = new String[100];
        int cantidad = collectMatchingClass(util.getArchive(fileToRead), inputtedClass, matchingClasses, 0);


        if (cantidad == 0) {
            System.out.println("=> No se encontraron personas inscritas en esa clase.");
            return;
        }

        StoreArchive.writeGrupalResultsFiles(fileToWrite, matchingClasses, cantidad, false);
    }

    public static int collectMatchingClass(Scanner scanner, String searchedClass, String[] results, int count) {

        if (scanner == null || !scanner.hasNextLine()) {
            if (scanner != null) scanner.close();
            return count;
        }

        String linea = scanner.nextLine();
        String[] partes = linea.split(",", -1);
        if (partes.length >= 6 && partes[4].trim().equalsIgnoreCase(searchedClass)) {
            results[count] = linea;
            count++;
        }

        return collectMatchingClass(scanner, searchedClass, results, count);
    }

    public static void consultDataByGender(String[][] bookedArray, boolean isOrder) throws IOException {
        int quantity;
        String fileToRead,fileToWrite;
        String[] matchingGenders = new String[100];;

        String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
        router = Validate.utilDirectory(router);

        if(bookedArray!= null) {
            if (!isOrder) {
                fileToRead = "BookedUsers.txt";
                quantity = gendersQuantity(router, fileToRead, matchingGenders);
                fileToWrite = "GlobalRegistConsultPerGender";
            } else {
                fileToRead = "BookedNutritions.txt";
                quantity = gendersQuantity(router, fileToRead, matchingGenders);
                fileToWrite = "GlobalOrderConsultPerGender";
            }

            if (quantity == 0) {
                System.out.println("=> No se encontraron personas de ese GENERO inscritas en el dia de hoy.");
                return;
            }
            StoreArchive.writeGrupalResultsFiles(fileToWrite, matchingGenders, quantity, true);
        }
    }

    public static int gendersQuantity(String router, String fileToRead, String[]matchingGenders) throws IOException {
        String inputtedGender = "";
        ArchiveUtil util = null;

        if(matchingGenders== null) return 0;

        Scanner scanner = new Scanner(System.in);
        String text = "- Ingrese 'M' o 'F' para realizar la busqueda por GENERO:";
        System.out.println(text);
        inputtedGender = Validate.valSubName(scanner.nextLine(), text, new Scanner(System.in));
        util = new ArchiveUtil(router);

        return collectMatchingGender(util.getArchive(fileToRead), inputtedGender, matchingGenders, 0);
    }


    public static int collectMatchingGender(Scanner scanner, String searchedGender, String[] results, int count) {

        if (scanner == null || !scanner.hasNextLine()) {
            if (scanner != null) scanner.close();
            return count;
        }

        String linea = scanner.nextLine();
        String[] partes = linea.split(",", -1);
        if (partes.length >= 5 && partes[2].trim().equalsIgnoreCase(searchedGender)) {
            results[count] = linea;
            count++;
        }

        return collectMatchingGender(scanner, searchedGender, results, count);
    }

    public static void consultOrderDataByItem(String[][] bookedArray, String msg) throws IOException {
        String text;
        Scanner scanner = new Scanner(System.in);
        String fileToRead = "BookedNutritions.txt";
        String fileToWrite = "GlobalConsultPerItem";
        String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
        router = Validate.utilDirectory(router);

        text = "- Ingrese la primera palabra de un ITEM que sea vendido en nuestro 'Centro Nutricional':";
        System.out.println(text);
        String inputtedItem = Validate.valSubName(scanner.nextLine(), text, new Scanner(System.in));

        ArchiveUtil util = new ArchiveUtil(router);

        String[] matchingItem = new String[100];
        int quantity = collectMatchingItem(util.getArchive(fileToRead), inputtedItem, matchingItem, 0);


        if (quantity == 0) {
            System.out.println("=> No se encontraron personas que hayan consumido "+inputtedItem+" el dia de hoy.");
            return;
        }

        StoreArchive.writeGrupalResultsFiles(fileToWrite, matchingItem, quantity, true);
    }

    public static int collectMatchingItem(Scanner scanner, String searchedItem, String[] results, int count) {

        if (scanner == null || !scanner.hasNextLine()) {
            if (scanner != null) scanner.close();
            return count;
        }

        String linea = scanner.nextLine();
        String[] partes = linea.split(",", -1);
        if (partes.length >= 5 &&  partes[4].trim().toLowerCase().startsWith(searchedItem.toLowerCase())) {
            results[count] = linea;
            count++;
        }

        return collectMatchingItem(scanner, searchedItem, results, count);
    }


}
