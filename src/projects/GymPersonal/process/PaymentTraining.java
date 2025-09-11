package projects.GymPersonal.process;

import projects.GymPersonal.repositories.ArchiveUtil;
import projects.GymPersonal.validateItem.Validate;
import projects.GymPersonal.composables.StoreArchive;

import java.io.IOException;
import java.nio.file.Paths;

import static main.projects.GymPersonal.process.BookingTraining.*;

public class PaymentTraining {
    static int availableCreds, creditCostPerClass,pricePerCredit;

    public static void paymentClassFlow(String[][] database, int[][][] gymMatrix) throws IOException {
        String selectedClass=CLASS_LISTINGS[classOption - 1];
        if(database != null && gymMatrix != null) {
            availableCreds = Integer.parseInt(database[userDatabaseIndex][4]);
            creditCostPerClass = getClassCreditCost(CLASS_LISTINGS, selectedClass);
            if (availableCreds >= creditCostPerClass) { // Tiene suficientes créditos: se descuentan
                applyTrainingCreditDiscount(database,gymMatrix);
            } else { // No tiene crédito: calculamos deuda
                handleTrainingDebt(database,gymMatrix);
            }
        }
    }

    public static int getUnitCreditTrainingPrice(String[][] database){
        int credPrice=0;
        if(database != null) {
            switch (database[userDatabaseIndex][3]) {
                case "PED":
                    credPrice = 5;
                    break;
                case "PMG":
                    credPrice = 3;
                    break;
                case "PME", "PMT":
                    credPrice = 2;
                    break;
            }
        }
        return credPrice;
    }

    public static void internalFinalProcess(String[][] database,int[][][] gymMatrix) throws IOException {
        int cycleDuration;
        // Reconstruir línea y guardar
        if(database != null && gymMatrix != null) {
            String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
            ArchiveUtil util = new ArchiveUtil(router);
            StoreArchive.rewriteDataBase(util, "GymMembersDatabase", database);
            /*String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage";
            router = Validate.utilDirectory(router) + "/GymMembersDatabase.txt";
            StoreArchive.rewriteDataBase(router, database);*/
            int k = (gymMatrix[0][0].length - availableSpots);
            cycleDuration = 1 + k;
            for (; k < cycleDuration; k++) {
                gymMatrix[classOption - 1][scheduleOption - 1][k] = pricePerCredit * creditCostPerClass;
            }
            String[][] newRegistration = addRegistrationToArray(bookedUsersArray);
            StoreArchive.storeRegistrationToFile(newRegistration, "BookedUsers.txt");
        }
    }

    public static int getClassCreditCost(String[] classList, String selectedClass) {
        if (selectedClass.equals(classList[8]) || selectedClass.equals(classList[0])) {
            return 3;
        }
        return 2;
    }


    public static boolean confirm(String prompt, int max, String msg) throws IOException {
        int opt = Validate.valInt(prompt, max);
        if(opt==2) {
            System.out.println("Operación cancelada"+msg);
            return false;
        }
        return opt == 1;
    }

    public static void applyTrainingCreditDiscount(String[][] database, int[][][] gymMatrix) throws IOException {
        String text;
        int newBalance;
        if (database != null && gymMatrix != null) {
            pricePerCredit = getUnitCreditTrainingPrice(database);
            System.out.println("\n==> La clase [" + CLASS_LISTINGS[classOption - 1] + "] tiene un costo de [*" + creditCostPerClass + " Creditos] <==");
            System.out.println("\nCréditos disponibles actualmente: *" + availableCreds);
            System.out.println("- Ingrese [1], SI ACEPTA que se le deduzcan [*" + creditCostPerClass + " Creditos] de su billetera de creditos disponibles." +
                    "\n- Ingrese [2], si NO DESEA pagar los creditos necesarios y desea ABANDONAR el proceso de reserva.");
            if (!confirm("- Ingrese el numero de la opcion escogida por el cliente: ",2,". No asiste a la clase.")) return;
            newBalance = availableCreds - creditCostPerClass;
            database[userDatabaseIndex][4] = String.valueOf(newBalance);
            // Resetear la deuda
            System.out.printf("\n¡Clase exitosamente cobrada (-%d créditos)! Nuevo saldo: [*%s Creditos]%n", creditCostPerClass, database[userDatabaseIndex][4]);
            internalFinalProcess(database,gymMatrix);
        }
    }

    public static void handleTrainingDebt(String[][] database, int[][][] gymMatrix) throws IOException {
        if(database!=null&& gymMatrix != null){
            pricePerCredit = getUnitCreditTrainingPrice(database);
            float debt = (creditCostPerClass - availableCreds) * pricePerCredit;
            System.out.printf("¡Créditos insuficientes! (%d Créditos disponibles vs %d Creditos necesarios) " +
                    "Se generaria deuda de %f$ a pagar al renovar su plan", availableCreds, creditCostPerClass, debt);
            System.out.println("- Ingrese [1], SI desea asistir y ASUMIR la deuda.\n- Ingrese [2], si NO desea asistir NI ASUMIR la deuda y desea cancelar le proceso.");
            if (confirm("- Ingrese el numero de la opcion escogida por el cliente: ", 2, ". No asiste a la clase.")) {
                database[userDatabaseIndex][7] = String.valueOf(Integer.parseInt(database[userDatabaseIndex][7])+debt);
                System.out.printf("Deuda registrada: %f$. Puede asistir.%n", debt);
                internalFinalProcess(database,gymMatrix);
            }
        }
    }



}
