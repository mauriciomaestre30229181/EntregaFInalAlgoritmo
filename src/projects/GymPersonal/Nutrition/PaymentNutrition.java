package projects.GymPersonal.Nutrition;

import projects.GymPersonal.composables.StoreArchive;
import projects.GymPersonal.repositories.ArchiveUtil;
import projects.GymPersonal.validateItem.Validate;

import java.io.IOException;
import java.nio.file.Paths;

import static main.projects.GymPersonal.Nutrition.BookingNutrition.*;
import static main.projects.GymPersonal.process.BookingTraining.*;
import static main.projects.GymPersonal.process.PaymentTraining.confirm;


public class PaymentNutrition {
    static int availableNutritionCreds, pricePerNutritCredit;

    public static void paymentNutritionFlow(String[][] database, int[] nutritionArray) throws IOException {
        if(database != null && nutritionArray != null) {
            availableNutritionCreds = Integer.parseInt(database[userDatabaseIndex][6]);
            if (selectedItem.equals(selectedNutritionType[0]) || selectedItem.equals(selectedNutritionType[1])) creditCostPerItem +=1;
            if (availableNutritionCreds >= creditCostPerItem) { // Tiene suficientes créditos: se descuentan
                applyNutritCreditDiscount(database,nutritionArray);
            } else { // No tiene crédito: calculamos deuda
                handleNutritionDebt(database,nutritionArray);
            }
        }
    }

    public static int getUnitCreditNutritionPrice(String[][] database){
        int credPrice=0;
        if(database != null) {
            switch (database[userDatabaseIndex][5]) {
                case "PCD":
                    credPrice = 4;
                    break;
                case "PCS":
                    credPrice = 3;
                    break;
                case "PCM":
                    credPrice = 2;
                    break;
                case "NP":
                    break;
            }
        }
        return credPrice;
    }

    public static void internalFinalNutritFlow(String[][] database,int[] nutritionArray) throws IOException {
        // Reconstruir línea y guardar
        if(database != null && nutritionArray != null) {
            String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage/";
            ArchiveUtil util = new ArchiveUtil(router);
            StoreArchive.rewriteDataBase(util, "GymMembersDatabase", database);
            /*String router = Paths.get("").toRealPath() + "/src/GymPersonal/storage";
            router = Validate.utilDirectory(router) + "/GymMembersDatabase.txt";
            StoreArchive.rewriteDataBase(router, database);*/
            for (int i = 0; i < nutritionArray.length; i++) {
                nutritionArray[i] =  pricePerNutritCredit * creditCostPerItem;
            }
            String[][] newRegistration = addRegistrationToNutritArray(bookedNutritionArray);
            StoreArchive.storeRegistrationToFile(newRegistration, "BookedNutritions.txt");
        }
    }

    public static void applyNutritCreditDiscount(String[][] database, int[] nutritionArray) throws IOException {
        String text;
        int newBalance;
        if (database != null && nutritionArray != null) {
            System.out.println("\n==> [" + selectedItem + "] tiene un costo de [*" + creditCostPerItem + " Creditos] <==");
            System.out.println("\nCréditos de nutricion disponibles actualmente: " + availableNutritionCreds);
            text = "- Ingrese [1], SI ACEPTA que se le deduzcan [*" + creditCostPerItem + " Creditos] de su billetera de creditos disponibles." +
                    "\n- Ingrese [2], si NO DESEA pagar los creditos necesarios y desea ABANDONAR el proceso de reserva de item nutricional.";
            if (!confirm(text,2,". No compra ni reserva el item.")) return;
            newBalance = availableNutritionCreds - creditCostPerItem;
            database[userDatabaseIndex][6] = String.valueOf(newBalance);
            // Resetear la deuda
            System.out.printf("¡Item nutricional exitosamente cobrado (-%d créditos)! Nuevo saldo: [*%s Creditos]%n", creditCostPerItem, database[userDatabaseIndex][6]);
            internalFinalNutritFlow(database, nutritionArray);
        }
    }

    public static void handleNutritionDebt(String[][] database, int[] nutritionArray) throws IOException {
        if(database!=null&& nutritionArray != null){
            pricePerNutritCredit = getUnitCreditNutritionPrice(database);
            if(pricePerNutritCredit==0){
                System.out.println("¡Créditos insuficientes!. Remitase a caja para registrar otro plan");
                return;
            }
            float debt = (creditCostPerItem - availableNutritionCreds) * pricePerNutritCredit;
            System.out.printf("¡Créditos insuficientes! (%d Créditos disponibles vs %d Creditos necesarios)" +
                    " Se generaria deuda de %f$ a pagar al renovar su plan", availableNutritionCreds, creditCostPerItem, debt);
            String text = "- Ingrese [1], SI desea asistir y ASUMIR la deuda.\n- Ingrese [2], si NO desea asistir NI ASUMIR la deuda y desea cancelar le proceso.";
            if (confirm(text, 2, ". No compra ni reserva el item.")) {
                database[userDatabaseIndex][7] += String.valueOf(debt);
                System.out.printf("Deuda registrada: %f$. Puede asistir.%n", debt);
                internalFinalNutritFlow(database,nutritionArray);
            }
        }
    }


}
